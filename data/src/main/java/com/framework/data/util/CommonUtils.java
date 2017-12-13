package com.framework.data.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.RadioButton;

/**
 * Created by 月光和我 on 2017/8/25.
 */

public class CommonUtils {

    /**
     * 设置RadioButton图片的大小
     * @param context      上下文
     * @param idRes        图片资源id
     * @param radioButton  radioButton控件
     * @param size         图片的大小  单位dp
     */
    public static void setRadioButtonSize(Context context , @DrawableRes int idRes, RadioButton radioButton, int size) {
        if(radioButton == null) return;
        Drawable drawable = context.getApplicationContext().getResources().getDrawable(idRes);
        drawable.setBounds(0, 0, SizeUtils.dp2px(context, size), SizeUtils.dp2px(context, size));
        radioButton.setCompoundDrawables(null, drawable, null, null);
    }
}
