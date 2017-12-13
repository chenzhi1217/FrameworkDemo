package com.framework.data.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 月光和我 on 2017/8/28.
 * 网络请求的拦截器
 */

public class HeadInterceptor implements Interceptor {
    private Context mContext;
    
    public HeadInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();
        builder.addHeader("imeiCode", "android");

        Response proceed = chain.proceed(request);
        return proceed;
    }
}
