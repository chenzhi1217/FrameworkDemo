package com.framework.frameworkdemo.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.frameworkdemo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 月光和我 on 2017/8/25.
 */

public class BasicActivity extends AppCompatActivity {
    private Map<String, BasicFragment> mFragments = new HashMap<>();
    private TextView mCenterTitle;
    private Toolbar mToolbar;
    private boolean mIsInterceptTouch;//标记是否拦截页面的触摸事件
    private ImageView mLeftIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootViewLayout());
        //禁止所有Activity横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initToolBar();
        //设置返回键
        setBackEnable(true);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolbar = findViewById(R.id.basic_act_toolbar);
        mCenterTitle = findViewById(R.id.tool_bar_title);
        mLeftIcon = findViewById(R.id.tool_bar_left_icon);
        setSupportActionBar(mToolbar);
    }

    protected Toolbar getToolBar() {
        return mToolbar;
    }

    protected void setToolBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        if (mCenterTitle != null) {
            mCenterTitle.setText(title);
        }
    }

    /**
     * 设置标题左边的icon
     * @param resId
     */
    protected void setLeftIcon(int resId) {
        mLeftIcon.setVisibility(View.VISIBLE);
        mLeftIcon.setImageResource(resId);
    }


    private int getRootViewLayout() {
        return R.layout.common_activity_fragment;
    }

    /**
     * 设置是否显示返回键
     * @param enable
     */
    protected void setBackEnable(boolean enable) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(enable);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mIsInterceptTouch || super.dispatchTouchEvent(ev);
    }

    /**
     * 往Activity中添加Fragment
     *
     * @param frameLayoutId
     * @param fragmentClass
     * @param tag
     */
    protected void startFragment(@IdRes int frameLayoutId, Class<? extends Fragment> fragmentClass, @NonNull String tag) {
        BasicFragment fragment = mFragments.get(tag);
        if (fragment == null) {
            try {
                fragment = (BasicFragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.getClass() != fragmentClass) {
                throw new RuntimeException("the tag in BasicActivity is error !");
            }
        }
        startFragment(frameLayoutId, fragment, tag);
    }

    public void startFragment(@IdRes int frameLayoutId, BasicFragment fragment, @NonNull String tag) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(frameLayoutId, fragment, tag).commit();
            mFragments.put(tag, fragment);
        }
    }

    /**
     * 请求拦截屏幕的点击事件，一般由子类来调用
     * @param isIntercept
     */
    public void requestInterceptTouch(boolean isIntercept) {
        mIsInterceptTouch = isIntercept;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean interceptor = false;
        boolean isBack = item.getItemId() == android.R.id.home;
        if (isBack && mFragments != null && !mFragments.isEmpty()) {
            for (BasicFragment fragment : mFragments.values()) {
                if (fragment.isResumed() && fragment.isVisible()) {
                    if (fragment.onBackPressed()) {
                        interceptor = true;
                    }
                }
            }
        }

        if (!interceptor && isBack) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
