package com.zyyyl.nursing.ai;

/**
 * Dify SSE 调用失败。
 */
public class DifySseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DifySseException(String message) {
        super(message);
    }

    public DifySseException(String message, Throwable cause) {
        super(message, cause);
    }
}
