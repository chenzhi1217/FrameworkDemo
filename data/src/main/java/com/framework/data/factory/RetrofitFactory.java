package com.framework.data.factory;

import android.content.Context;

import com.framework.data.interceptor.HeadInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 月光和我 on 2017/8/26.
 */

public class RetrofitFactory {

    public static <T> T create(Class<T> clazz, Context context, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置联网时间
        builder.connectTimeout(16, TimeUnit.SECONDS);
        
        //添加拦截器
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        //默认添加一个拦截器
        builder.addInterceptor(new HeadInterceptor(context.getApplicationContext()));
        
        //设置失败重连
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://www.kaopush.com:8889/")
                .build();
        return retrofit.create(clazz);
    }
}
