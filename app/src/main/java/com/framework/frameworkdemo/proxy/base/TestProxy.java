package com.framework.frameworkdemo.proxy.base;

import android.content.Context;

import com.framework.data.api.TestApiImpl;
import com.framework.data.model.HttpResult;
import com.framework.data.model.TestModel;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 月光和我 on 2017/8/28.
 */

public class TestProxy extends RefreshProxy<TestRequest,TestModel> {

    private final TestApiImpl mTestApiImpl;

    public TestProxy(Context context) {
        super(context);
        mTestApiImpl = new TestApiImpl(context);
    }

    /**
     * 让子类去返回一个Observable对象
     * @param requester
     * @return
     */
    @Override
    protected Observable<TestModel> getObservable(TestRequest requester) {
        return mTestApiImpl.getTestData().map(new Func1<HttpResult<TestModel>, TestModel>() {
            @Override
            public TestModel call(HttpResult<TestModel> testModelHttpResult) {
                return testModelHttpResult.getData();
            }
        });
    }
}
