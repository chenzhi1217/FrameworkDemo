package com.framework.frameworkdemo.base;

import android.databinding.ViewDataBinding;

import com.framework.data.requester.BaseRequester;
import com.framework.frameworkdemo.proxy.base.RefreshProxy;

/**
 * Created by 月光和我 on 2017/9/2.
 */

public abstract class NoNetFragment<REQ extends BaseRequester,DATA,BIND extends ViewDataBinding> extends BasicFragment<REQ,DATA,BIND> {
    @Override
    protected RefreshProxy<REQ, DATA> createProxy() {
        return null;
    }
    
    @Override
    protected REQ getRequestId() {
        return null;
    }

    @Override
    protected boolean isNeedWeb() {
        return false;
    }
    
    /*
    @Override
    protected boolean isNeedWeb() {
        return false;
    }

    *//**
     * 具体代理的创建由子类去实现
     *
     * @return
     *//*
    @Override
    protected RefreshProxy createProxy() {
        return null;
    }

    *//**
     * 让子类返回请求体
     *
     * @return
     *//*
    @Override
    protected BaseRequester getRequestId() {
        return null;
    }*/
}
