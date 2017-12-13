package com.framework.data.util;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by 月光和我 on 2017/9/1.
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showShortToast(Context context, String value) {
        if (mToast == null) {
            mToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(value);
        mToast.show();
    }

    public static void showShortToast(Context context, @StringRes int id) {
        if (mToast == null) {
            mToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(id);
        mToast.show();
    }

    public static void showLongToast(Context context, String value) {
        if (mToast == null) {
            mToast = mToast = Toast.makeText(context,"",Toast.LENGTH_LONG);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(value);
        mToast.show();
    }

    public void showLongToast(Context context, @StringRes int id) {
        if (mToast == null) {
            mToast = mToast = Toast.makeText(context,"",Toast.LENGTH_LONG);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(id);
        mToast.show();
    }
}
