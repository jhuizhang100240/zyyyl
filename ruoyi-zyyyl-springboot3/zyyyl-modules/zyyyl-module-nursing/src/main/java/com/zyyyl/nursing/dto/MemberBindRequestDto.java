package com.zyyyl.nursing.dto;

import lombok.Data;

/**
 * 首次微信登录绑定原家属账号请求。
 */
@Data
public class MemberBindRequestDto {

    /** 微信登录未绑定时返回的一次性票据 */
    private String bindTicket;

    /** 家属手机号 */
    private String phone;

    /** 微信手机号快速验证临时凭证，可选 */
    private String phoneCode;
}
