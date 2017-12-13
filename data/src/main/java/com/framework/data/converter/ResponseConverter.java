package com.framework.data.converter;

import com.framework.data.exception.NetConnectException;
import com.framework.data.exception.OperationException;
import com.framework.data.exception.UnLoginException;
import com.framework.data.model.HttpResult;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by 月光和我 on 2017/8/28.
 */

public class ResponseConverter<T> implements Converter<ResponseBody,T>{
    
    private Gson gson;
    private TypeAdapter<?> adapter;
    private Type type;

    public ResponseConverter(Gson gson, TypeAdapter<?>  adapter, Type type) {
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        T data = gson.fromJson(response, type);
        if (data instanceof HttpResult) {
            HttpResult result = (HttpResult) data;
            int status = result.getStatus();
            if (status == 0) {//抛出操作失误的异常
                throw new OperationException(result.getError());
            } else if (status == 9) {//抛出未登录的异常
                throw new UnLoginException(result.getError());
            } else if (status != 1) {//抛出网络连接失败的异常
                throw new NetConnectException(result.getError());
            }
        }
        return data;
    }
}
