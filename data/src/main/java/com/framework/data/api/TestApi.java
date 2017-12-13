package com.framework.data.api;

import com.framework.data.model.HttpResult;
import com.framework.data.model.TestModel;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 月光和我 on 2017/8/28.
 */


public interface TestApi {
    @GET("kpAPI.do?m=home&kpmethod=exec&" + "s=api-getYmPageHome")
    Observable<HttpResult<TestModel>> getTestData();
}
