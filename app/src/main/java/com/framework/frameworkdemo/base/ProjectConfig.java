package com.framework.frameworkdemo.base;

import android.app.Application;
import android.util.Log;

import com.framework.frameworkdemo.BuildConfig;

/**
 * Created by chenzhi on 2017/11/29.
 */

public class ProjectConfig {
    private static int BUILD_TYPE = BuildConfig.APP_BUILD_TYPE;


    public static void init(Application context) {
        seeLog(context);
    }


    public static void seeLog(Application context) {
        Log.i("System_Config", "BuildType  " + BuildConfig.BUILD_TYPE);
        Log.i("System_Config", "FlavoType  " + BuildConfig.FLAVOR);
        Log.i("System_Config", "VerisionName  " + BuildConfig.VERSION_NAME);
        Log.i("System_Config", "VerisionCode  " + BuildConfig.VERSION_CODE);
    }
}
