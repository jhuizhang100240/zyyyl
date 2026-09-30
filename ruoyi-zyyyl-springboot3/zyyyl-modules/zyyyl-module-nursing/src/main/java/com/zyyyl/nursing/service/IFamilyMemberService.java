package com.zyyyl.nursing.service;

import com.mybatisflex.core.service.IService;
import com.zyyyl.nursing.domain.FamilyMember;

public interface IFamilyMemberService extends IService<FamilyMember> {

    FamilyMember selectByOpenId(String openId);

    FamilyMember selectByPhone(String phone);

    /**
     * 登录时保存或更新家属账号，返回最新记录。
     *
     * @param openId   微信 openid
     * @param phone    手机号
     * @param nickName 昵称，可空
     * @return 家属账号
     */
    FamilyMember saveOrUpdateByWechat(String openId, String phone, String nickName);
}
