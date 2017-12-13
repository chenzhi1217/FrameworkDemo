package com.framework.data.exception;

/**
 * Created by 月光和我 on 2017/8/28.
 * 网络连接失败的异常
 */
public class NetConnectException extends ApiException {
    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public NetConnectException(String message) {
        super(message);
    }

    public NetConnectException(Throwable throwable) {
        super(throwable);
    }
}
