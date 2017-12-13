package com.framework.frameworkdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.framework.frameworkdemo.base.MainActivity;

/**
 * Created by 月光和我 on 2017/8/15.
 */

public class MyService extends Service {

    public static final String TAG = "MyService";
    private MyBind mBind = new MyBind();

    @Override
    public void onCreate() {
        Notification notification = new Notification(R.mipmap.ic_launcher, "测试服务", System.currentTimeMillis());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent.getActivity(this, 0, intent, 0);
        startForeground(0, notification);
        Log.i(TAG,"service_onCreate");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"service_onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"service_onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }
    
    
    class MyBind extends Binder{
        public void startDowLoad() {
            Log.i(TAG,"服务里开启下载了。。。");
        }
    }
}
