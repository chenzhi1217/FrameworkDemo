package com.framework.frameworkdemo.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.buss.mainfragment.OneFragment;
import com.framework.frameworkdemo.buss.mainfragment.ThreeFragment;
import com.framework.frameworkdemo.buss.mainfragment.TwoFragment;
import com.framework.frameworkdemo.buss.factory.MainFragmentFactory;
import com.framework.data.util.CommonUtils;
import com.framework.frameworkdemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;

    private List<Fragment> mFragments = new ArrayList<>();
    private int mIndex = 0;
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initView();
        initFragment();
    }
    
    /**
     * 创建Fragment实例
     */
    private void initFragment() {
        OneFragment oneFragment = (OneFragment) MainFragmentFactory.getInstanceByIndex(R.id.main_radio_button_one);
        TwoFragment twoFragment = (TwoFragment) MainFragmentFactory.getInstanceByIndex(R.id.main_radio_button_two);
        ThreeFragment threeFragment = (ThreeFragment) MainFragmentFactory.getInstanceByIndex(R.id.main_radio_button_three);
        mFragments.add(oneFragment);mFragments.add(twoFragment);mFragments.add(threeFragment);
        //默认显示第一个Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.main_framelayout, oneFragment).show(oneFragment).commit();
    }
    
    /**
     * 初始化控件
     */
    private void initView() {
        Toolbar toolbar = mMainBinding.mainToolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//取消左上角的返回键
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏ToolBar默认的标题
        mRadioGroup = mMainBinding.mainRadioGroup;
        mRadioGroup.setOnCheckedChangeListener(listener);//注册RadioGroup的选中监听
        //设置RadioButton图片的大小
        CommonUtils.setRadioButtonSize(this,R.drawable.lable_home_selector, (RadioButton) findViewById(R.id.main_radio_button_one), 22);
        CommonUtils.setRadioButtonSize(this,R.drawable.lable_my_selector, (RadioButton) findViewById(R.id.main_radio_button_three), 22);
        CommonUtils.setRadioButtonSize(this,R.drawable.lable_news_selector, (RadioButton) findViewById(R.id.main_radio_button_two), 22);
    }
    
    //测试了下
    
    /**
     * RadioGroup选中的监听
     */
    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.main_radio_button_one:
                    setSelectedFragment(0);
                    break;
                case R.id.main_radio_button_two:
                    setSelectedFragment(1);
                    break;
                case R.id.main_radio_button_three:
                    setSelectedFragment(2);
                    break;
            }
        }
    };

    /**
     * Fragment的切换方法
     * @param index
     */
    private void setSelectedFragment(int index) {
        if (index == mIndex) {
            return;
        }
        Fragment currFragment = mFragments.get(index);
        //开启一个事物
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //隐藏上一个Fragment
        ft.hide(mFragments.get(mIndex));
        //显示当前Fragment
        if (!mFragments.get(index).isAdded()) {
            ft.add(R.id.main_framelayout, currFragment).show(currFragment);
        } else {
            ft.show(currFragment);
        }
        //提交事务
        ft.commit();
        mIndex = index;
    }
}
