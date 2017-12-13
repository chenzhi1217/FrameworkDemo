package com.framework.data.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * @author sunquan
 * @email sunquan.wzdai.com
 * @Description
 */
public class ScreenUtils {
    /**
     * 获取手机密度
     *
     * @return
     */
    public static float getScreenDIP(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().density;
    }
    
    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.heightPixels;
    }
    
    /**
     * 获取包括虚拟按键在内的屏幕高度
     * @param context
     * @return
     */
    public static int getScreenAllHeight(Activity context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(dm);
        return dm.heightPixels;
    }
    
    /**
     * 获取屏幕分辨率
     *
     * @param context 上下分对象
     * @return 包含宽和高的数组
     */
    public static int[] getScreenDisplay(Context context) {
        int result[] = {getScreenWidth(context), getScreenHeight(context)}; 
        return result;
    }
    
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static double getDistance(float X1, float X2, float Y1, float Y2) {
        return Math.sqrt(Math.pow(X1 - X2,2) + Math.pow(Y1 - Y2,2));
    }

    /**
     * 获取底部NavigationBar高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarSize(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
