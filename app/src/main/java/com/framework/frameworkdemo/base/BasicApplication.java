package com.framework.frameworkdemo.base;

import android.app.Application;
import android.content.Context;

import com.framework.data.util.LogUtils;

/**
 * Created by chenzhi on 2017/11/28.
 */

public class BasicApplication extends Application {
    private static Context appliContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        appliContext = this;
        //初始化日志
        LogUtils.initViseLog();
        ProjectConfig.init(this);
        
        //初始化GreenDao
        GreenDaoConfig.init(this);
    }
}
