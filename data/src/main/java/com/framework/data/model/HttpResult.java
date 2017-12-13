package com.framework.data.model;

import java.io.Serializable;

/**
 * Created by 月光和我 on 2017/8/28.
 */

public class HttpResult<T> implements Serializable {
    /**
     * 错误信息
     */
    private String error;
    /**
     * 结果编码
     */
    private String code;
    /**
     * 系统状态   9跳转登录
     */
    private int status;
    
    private String webRoot;
    
    private T data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWebRoot() {
        return webRoot;
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
