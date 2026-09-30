package com.zyyyl.nursing.vo;

import lombok.Data;

/**
 * 家属端登录响应，字段与遗留小程序保持一致。
 */
@Data
public class MemberLoginVo {

    /** 登录令牌 */
    private String token;

    /** 昵称 */
    private String nickName;

    /** 脱敏手机号 */
    private String phone;

    private Long memberId;
}
