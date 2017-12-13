package com.framework.data.exception;

/**
 * Created by 月光和我 on 2017/8/28.
 * 操作失误的异常
 */

public class OperationException extends ApiException {
    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public OperationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public OperationException(Throwable throwable) {
        super(throwable);
    }
}
