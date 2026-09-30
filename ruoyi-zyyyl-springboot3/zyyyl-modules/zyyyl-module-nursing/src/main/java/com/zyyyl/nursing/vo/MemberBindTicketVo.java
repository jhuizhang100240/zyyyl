package com.zyyyl.nursing.vo;

import lombok.Data;

/**
 * 微信登录未绑定时返回的一次性绑定票据。
 */
@Data
public class MemberBindTicketVo {

    private String bindTicket;
}
