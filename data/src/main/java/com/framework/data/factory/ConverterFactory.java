package com.framework.data.factory;

import com.framework.data.converter.ResponseConverter;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by 月光和我 on 2017/8/28.
 */

public final class ConverterFactory extends Converter.Factory {

    public static ConverterFactory create() {
        return create(new Gson());
    }

    public static ConverterFactory create(Gson gson) {
        return new ConverterFactory(gson);
    }
    
    private Gson gson;

    private ConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null !");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ResponseConverter<>(gson,adapter,type);
    }
    
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
