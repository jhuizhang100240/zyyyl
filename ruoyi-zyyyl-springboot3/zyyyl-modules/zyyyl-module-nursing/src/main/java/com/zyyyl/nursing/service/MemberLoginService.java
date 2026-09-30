package com.zyyyl.nursing.service;

import org.springframework.stereotype.Service;

import com.zyyyl.common.constant.CacheConstants;
import com.zyyyl.common.core.domain.entity.SysUser;
import com.zyyyl.common.core.domain.model.LoginUser;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.SecurityUtils;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.common.utils.uuid.IdUtils;
import com.zyyyl.framework.web.service.TokenService;
import com.zyyyl.nursing.domain.FamilyMember;
import com.zyyyl.nursing.dto.MemberBindRequestDto;
import com.zyyyl.nursing.dto.MemberLoginRequestDto;
import com.zyyyl.nursing.exception.MemberBindRequiredException;
import com.zyyyl.nursing.vo.MemberLoginVo;

/**
 * 家属端登录服务。按方案 A 采用微信授权模型：code 换 openid，phoneCode 换手机号，
 * 首次微信登录返回一次性绑定票据。
 */
@Service
public class MemberLoginService {

    /** 绑定票据有效期（分钟） */
    private static final long BIND_TICKET_EXPIRE_MINUTES = 10L;

    private final IWechatService wechatService;
    private final IFamilyMemberService familyMemberService;
    private final TokenService tokenService;
    private final MemberBindTicketStore bindTicketStore;

    public MemberLoginService(IWechatService wechatService, IFamilyMemberService familyMemberService,
            TokenService tokenService, MemberBindTicketStore bindTicketStore) {
        this.wechatService = wechatService;
        this.familyMemberService = familyMemberService;
        this.tokenService = tokenService;
        this.bindTicketStore = bindTicketStore;
    }

    /**
     * 兼容原小程序的登录入口：code + phoneCode 直接登录，未绑定账号时按手机号自动建号。
     */
    public MemberLoginVo login(MemberLoginRequestDto dto) {
        requireLoginRequest(dto);
        String openid = wechatService.getOpenid(dto.getCode());
        String phone = null;
        if (StringUtils.isNotEmpty(dto.getPhoneCode())) {
            phone = wechatService.getPhone(dto.getPhoneCode());
        }
        FamilyMember member = familyMemberService.selectByOpenId(openid);
        if (member == null && StringUtils.isEmpty(phone)) {
            throw new ServiceException("未绑定原家属账号，请先完成微信绑定");
        }
        member = familyMemberService.saveOrUpdateByWechat(openid, phone, dto.getNickName());
        return buildLoginVo(member);
    }

    /**
     * 微信静默登录：只接受 code。已绑定直接登录，未绑定返回 202 bindRequired。
     */
    public MemberLoginVo wxLogin(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new ServiceException("微信登录凭证不能为空");
        }
        String openid = wechatService.getOpenid(code);
        FamilyMember member = familyMemberService.selectByOpenId(openid);
        if (member == null) {
            String ticket = IdUtils.fastUUID();
            bindTicketStore.save(ticket, openid, BIND_TICKET_EXPIRE_MINUTES);
            throw new MemberBindRequiredException(ticket);
        }
        return buildLoginVo(member);
    }

    /**
     * 首次微信登录绑定原家属账号。
     */
    public MemberLoginVo wxBind(MemberBindRequestDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.getBindTicket())) {
            throw new ServiceException("绑定票据不能为空");
        }
        String openid = bindTicketStore.getOpenId(dto.getBindTicket());
        if (StringUtils.isEmpty(openid)) {
            throw new ServiceException("绑定票据已过期，请重新登录");
        }
        String phone = dto.getPhone();
        if (StringUtils.isNotEmpty(dto.getPhoneCode())) {
            String authorized = wechatService.getPhone(dto.getPhoneCode());
            if (StringUtils.isNotEmpty(phone) && !phone.equals(authorized)) {
                throw new ServiceException("授权手机号与填写手机号不一致");
            }
            phone = authorized;
        }
        if (StringUtils.isEmpty(phone)) {
            throw new ServiceException("手机号不能为空");
        }
        FamilyMember member = familyMemberService.selectByPhone(phone);
        if (member == null) {
            throw new ServiceException("该手机号未注册为家属账号，请确认后重试");
        }
        if (StringUtils.isNotEmpty(member.getOpenId()) && !member.getOpenId().equals(openid)) {
            throw new ServiceException("该家属账号已绑定其他微信");
        }
        member.setOpenId(openid);
        familyMemberService.updateById(member);
        bindTicketStore.remove(dto.getBindTicket());
        return buildLoginVo(member);
    }

    /**
     * 当前登录家属ID，供家属端接口复用。
     */
    public Long currentMemberId() {
        if (!SecurityUtils.isMemberLogin()) {
            throw new ServiceException("请使用家属端账号登录");
        }
        return SecurityUtils.getUserId();
    }

    /**
     * 校验当前请求必须为家属端登录态。给不使用主键的家属端接口复用。
     */
    public void requireMemberLogin() {
        currentMemberId();
    }

    private MemberLoginVo buildLoginVo(FamilyMember member) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(member.getId());
        loginUser.setLoginType("member");
        SysUser user = new SysUser();
        user.setUserId(member.getId());
        user.setUserName("member:" + member.getId());
        user.setNickName(member.getName());
        user.setPhonenumber(member.getPhone());
        loginUser.setUser(user);
        String token = tokenService.createToken(loginUser, CacheConstants.MEMBER_LOGIN_TOKEN_KEY);
        MemberLoginVo vo = new MemberLoginVo();
        vo.setToken(token);
        vo.setNickName(member.getName());
        vo.setMemberId(member.getId());
        vo.setPhone(maskPhone(member.getPhone()));
        return vo;
    }

    private void requireLoginRequest(MemberLoginRequestDto dto) {
        if (dto == null || StringUtils.isEmpty(dto.getCode())) {
            throw new ServiceException("微信登录凭证不能为空");
        }
    }

    private String maskPhone(String phone) {
        if (StringUtils.isEmpty(phone) || phone.length() < 7) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
