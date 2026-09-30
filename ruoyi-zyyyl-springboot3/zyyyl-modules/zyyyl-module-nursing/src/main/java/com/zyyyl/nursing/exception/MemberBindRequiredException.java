package com.zyyyl.nursing.exception;

/**
 * 微信登录未绑定原家属账号时抛出，由控制层转换为 202 bindRequired。
 */
public class MemberBindRequiredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String bindTicket;

    public MemberBindRequiredException(String bindTicket) {
        super("bindRequired");
        this.bindTicket = bindTicket;
    }

    public String getBindTicket() {
        return bindTicket;
    }
}
