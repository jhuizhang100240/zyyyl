package com.zyyyl.nursing.dto;

import lombok.Data;

/**
 * 家属端登录 / 微信绑定请求。
 * 字段名与遗留小程序保持一致：code=微信登录凭证，phoneCode=微信手机号授权凭证。
 */
@Data
public class MemberLoginRequestDto {

    /** 微信登录临时凭证 */
    private String code;

    /** 微信手机号快速验证临时凭证 */
    private String phoneCode;

    /** 小程序用户昵称，可空 */
    private String nickName;
}
