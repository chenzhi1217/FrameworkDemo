package com.framework.data.api;

import android.content.Context;

import com.framework.data.factory.RetrofitFactory;
import com.framework.data.model.HttpResult;
import com.framework.data.model.TestModel;

import rx.Observable;

/**
 * Created by 月光和我 on 2017/8/28.
 */

public class TestApiImpl implements TestApi {
    
    private final TestApi mTestApi;
    
    public TestApiImpl(Context context) {
        mTestApi = RetrofitFactory.create(TestApi.class, context);
    }
    
    @Override
    public Observable<HttpResult<TestModel>> getTestData() {
        return mTestApi.getTestData();
    }
}
