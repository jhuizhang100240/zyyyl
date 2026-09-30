package com.zyyyl.nursing.service;

/**
 * 微信小程序服务。
 */
public interface IWechatService {

    /**
     * 用 wx.login 返回的 code 换取 openid
     *
     * @param code 微信登录临时凭证
     * @return openid
     */
    String getOpenid(String code);

    /**
     * 用 getPhoneNumber 返回的 code 换取手机号
     *
     * @param phoneCode 微信手机号授权凭证
     * @return 手机号
     */
    String getPhone(String phoneCode);
}
