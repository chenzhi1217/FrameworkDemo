package com.framework.data.exception;

/**
 * Created by 月光和我 on 2017/8/28.
 * 未登录的异常
 */

public class UnLoginException extends ApiException {
    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public UnLoginException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public UnLoginException(Throwable throwable) {
        super(throwable);
    }
}
