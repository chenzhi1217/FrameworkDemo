package com.framework.frameworkdemo.base;

import android.content.Context;

/**
 * Copyright (C), 2017/11/29 91账单
 * Author: chenzhi
 * Email: chenzhi@91zdan.com
 * Description:
 */
public class BasicPresenter<IV extends IView, IM extends IModel> {
    protected IV mView;
    protected IM mModel;
    private Context mContext;

    public BasicPresenter(Context context, IV view, IM model) {
        this.mContext = context;
        this.mView = view;
        this.mModel = model;
    }
}
