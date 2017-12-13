package com.framework.frameworkdemo.buss.factory;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.buss.mainfragment.OneFragment;
import com.framework.frameworkdemo.buss.mainfragment.ThreeFragment;
import com.framework.frameworkdemo.buss.mainfragment.TwoFragment;

/**
 * Created by 月光和我 on 2017/8/22.
 */

public class MainFragmentFactory {
    public static Fragment getInstanceByIndex(@IdRes int resId) {
        Fragment fragment = null;
        switch (resId) {
            case R.id.main_radio_button_one:
                fragment = new OneFragment();
                break;
            case R.id.main_radio_button_two:
                fragment = new TwoFragment();
                break;
            case R.id.main_radio_button_three:
                fragment = new ThreeFragment();
                break;
        }
        return fragment;
    }
}
