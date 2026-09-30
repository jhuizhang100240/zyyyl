package com.zyyyl.nursing.service.impl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.domain.FamilyMember;
import com.zyyyl.nursing.mapper.FamilyMemberMapper;
import com.zyyyl.nursing.service.IFamilyMemberService;

@Service
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, FamilyMember>
        implements IFamilyMemberService {

    private static final List<String> DEFAULT_NICKNAME_PREFIX = List.of(
            "生活更美好", "大桔大利", "日富一日", "好柿开花", "柿柿如意",
            "一椰暴富", "大柚所为", "杨梅吐气", "天生荔枝");

    @Override
    public FamilyMember selectByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) {
            return null;
        }
        return mapper.selectByOpenId(openId);
    }

    @Override
    public FamilyMember selectByPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return null;
        }
        return mapper.selectByPhone(phone);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FamilyMember saveOrUpdateByWechat(String openId, String phone, String nickName) {
        FamilyMember byOpenId = mapper.selectByOpenId(openId);
        if (byOpenId != null) {
            boolean changed = false;
            if (StringUtils.isNotEmpty(phone) && !phone.equals(byOpenId.getPhone())) {
                byOpenId.setPhone(phone);
                changed = true;
            }
            if (StringUtils.isNotEmpty(nickName) && !nickName.equals(byOpenId.getName())) {
                byOpenId.setName(nickName);
                changed = true;
            }
            if (changed) {
                updateById(byOpenId);
            }
            return byOpenId;
        }

        FamilyMember byPhone = mapper.selectByPhone(phone);
        if (byPhone != null) {
            byPhone.setOpenId(openId);
            if (StringUtils.isNotEmpty(nickName)) {
                byPhone.setName(nickName);
            }
            updateById(byPhone);
            return byPhone;
        }

        FamilyMember member = new FamilyMember();
        member.setOpenId(openId);
        member.setPhone(phone);
        member.setName(StringUtils.isNotEmpty(nickName) ? nickName : buildDefaultNickname(phone));
        save(member);
        return member;
    }

    private String buildDefaultNickname(String phone) {
        String suffix = StringUtils.isEmpty(phone) || phone.length() < 4
                ? "0000"
                : phone.substring(phone.length() - 4);
        String prefix = DEFAULT_NICKNAME_PREFIX.get(ThreadLocalRandom.current().nextInt(DEFAULT_NICKNAME_PREFIX.size()));
        return prefix + suffix;
    }
}
