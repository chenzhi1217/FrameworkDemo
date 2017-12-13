package com.framework.frameworkdemo.base;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by 月光和我 on 2017/7/14.
 */
public class ViewHelper {
    private View mRootView;
    private SparseArray<View> mViewArray = new SparseArray<>();

    public ViewHelper(View rootView) {
        this.mRootView = rootView;
    }

    public <V extends View> V getView(@IdRes int idRes) {
        View view = mViewArray.get(idRes);
        if (view == null) {
            view = mRootView.findViewById(idRes);
            mViewArray.put(idRes, view);
        } 
        return (V) view;
    }
}
