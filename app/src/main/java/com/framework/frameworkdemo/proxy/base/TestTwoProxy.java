package com.framework.frameworkdemo.proxy.base;

import android.content.Context;

import com.framework.data.api.TestApiImpl;
import com.framework.data.model.HttpResult;
import com.framework.data.model.TestModel;
import com.framework.data.requester.BaseRequester;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 月光和我 on 2017/8/28.
 */

public class TestTwoProxy extends RefreshProxy<BaseRequester,Object> {

    private final TestApiImpl mTestApiImpl;

    public TestTwoProxy(Context context) {
        super(context);
        mTestApiImpl = new TestApiImpl(context);
    }

    /**
     * 让子类去返回一个Observable对象
     * @param requester
     * @return
     */
    @Override
    protected Observable<Object> getObservable(BaseRequester requester) {
        return null;
    }
}
