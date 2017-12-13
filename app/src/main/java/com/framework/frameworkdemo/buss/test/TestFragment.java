package com.framework.frameworkdemo.buss.test;

import com.framework.data.requester.BaseRequester;
import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.base.NoNetFragment;
import com.framework.frameworkdemo.databinding.FragmentTestBinding;

/**
 * Copyright (C), 2017/11/30 91账单
 * Author: chenzhi
 * Email: chenzhi@91zdan.com
 * Description:
 */
public class TestFragment extends NoNetFragment<BaseRequester,Object,FragmentTestBinding> {
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_test;
    }
}
