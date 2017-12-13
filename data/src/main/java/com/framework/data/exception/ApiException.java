package com.framework.data.exception;

/**
 * Created by 月光和我 on 2017/8/28.
 */

public class ApiException extends RuntimeException {
    private String displayMessage;
    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public ApiException() {
        this("");
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public ApiException(String detailMessage) {
        this(detailMessage,null);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace,
     * the specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable
     */
    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        setDisplayMessage(detailMessage);
    }

    private void setDisplayMessage(String detailMessage) {
        this.displayMessage = detailMessage;
    }

    private String getDisplayMessage() {
        return displayMessage;
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public ApiException(Throwable throwable) {
        this("", throwable);
    }
}
