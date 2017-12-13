package com.framework.frameworkdemo.base;

import android.app.Application;

import com.framework.data.greendao.DaoMaster;
import com.framework.data.greendao.DaoSession;

/**
 * Copyright (C), 2017/11/30 91账单
 * Author: chenzhi
 * Email: chenzhi@91zdan.com
 * Description:
 */
public class GreenDaoConfig {

    private static DaoSession mDaoSession;

    public static void init(Application application) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(application, "project.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getSession() {
        return mDaoSession;
    }
}