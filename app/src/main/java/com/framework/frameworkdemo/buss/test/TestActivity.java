package com.framework.frameworkdemo.buss.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.base.BasicActivity;

/**
 * Copyright (C), 2017/11/30 91账单
 * Author: chenzhi
 * Email: chenzhi@91zdan.com
 * Description:
 */
public class TestActivity extends BasicActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("测试页面");
        startFragment(R.id.common_activity_fl,TestFragment.class,"TestFragment");
    }
}
