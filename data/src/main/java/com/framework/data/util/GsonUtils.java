package com.framework.data.util;

import android.util.JsonToken;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzhi on 2017/11/28.
 */

public class GsonUtils {
    private Gson mGson = null;
    private GsonUtils() {
        mGson = new Gson();
    }
    private static GsonUtils mInstance = null;
    public GsonUtils getInstance() {
        if (mInstance == null) {
            synchronized (GsonUtils.class) {
                if (mInstance == null) {
                    mInstance = new GsonUtils();
                }
            }
        }
        return mInstance;
    }

    public String object2Json(Object obj) {
        String json = "";
        try {
            json = mGson.toJson(obj);
        } catch (Exception e) {
        }
        return json;
    }

    public <T> T json2Object(String json, Class<T> clazz) {
        T obj = null;
        try {
            obj = mGson.fromJson(json, clazz);
        } catch (Exception e) {

        }
        return obj;
    }

    public <T> List<T> json2List(String json) {
        List<T> list = null;
        try {
            list = mGson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

    public <T> Map<String, T> json2Map(String json) {
        Map<String, T> map = null;
        try {
            map = mGson.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return map;
    }

    public JsonObject json2JsonObject(String json) {
        JsonObject object = null;
        try {
            object = new JsonParser().parse(json).getAsJsonObject();
        } catch (Exception e) {
        }
        return object;
    }

    public JsonObject map2JsonObject(Map<?, ?> map) {
        return json2JsonObject(object2Json(map));
    }

}
