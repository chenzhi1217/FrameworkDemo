package com.framework.frameworkdemo.holder;

import android.view.View;
import android.view.ViewGroup;

import com.framework.frameworkdemo.databinding.TestItemBinding;

/**
 * Created by chenzhi on 2017/11/25.
 */

public class testHolder extends BaseHolder<Object,TestItemBinding> {
    public testHolder(View itemView) {
        super(itemView);
    }

    public testHolder(ViewGroup parent, int resId) {
        super(parent, resId);
    }

    @Override
    protected void bindData(Object data) {

    }
}
