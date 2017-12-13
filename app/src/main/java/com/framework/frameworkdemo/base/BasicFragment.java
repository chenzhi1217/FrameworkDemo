package com.framework.frameworkdemo.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.data.exception.ApiException;
import com.framework.data.exception.UnLoginException;
import com.framework.data.requester.BaseRequester;
import com.framework.data.util.ToastUtil;
import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.databinding.LayoutCommonLoadingAndRetryLayoutBinding;
import com.framework.frameworkdemo.proxy.base.BasicProxy;
import com.framework.frameworkdemo.proxy.base.RefreshProxy;

/**
 * Created by 月光和我 on 2017/7/14.
 */
public abstract class BasicFragment<REQ extends BaseRequester, DATA,BIND extends ViewDataBinding> extends Fragment implements View.OnClickListener {
    protected RefreshProxy<REQ, DATA> mBasicProxy;//网络请求的Proxy
    private ViewHelper mViewHelper;
    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private ViewGroup mRootView;

    protected LayoutCommonLoadingAndRetryLayoutBinding mParentBinding;
    protected BIND mContentBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (isNeedWeb()) {
            //先创建代理对象
            mBasicProxy = createProxy();
            //注册网络访问结果回调
            mBasicProxy.setProxyCallBack(createProxyCallBack());
        }
    }


    protected BasicProxy.ProxyCallBack<REQ, DATA> createProxyCallBack() {
        return new BasicFragmentProxyCallBack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basic_common_retry_layout://点击重新加载数据
                loadData();
                break;
        }
    }

    /**
     * 网络请求结果的回调
     */
    class BasicFragmentProxyCallBack implements BasicProxy.ProxyCallBack<REQ, DATA> {
        /**
         * 网络请求成功的回掉方法
         *
         * @param requester 请求体对象
         * @param type      请求类型
         * @param data      后端返回的数据模型
         */
        @Override
        public void onLoadBack(REQ requester, BasicProxy.ProxyType type, DATA data) {
            requestInterceptTouch(false);
            switch (type) {
                case LOAD_DATA:
                    //加载成功的方法
                    onLoadSuccess(requester, type, data);
                    break;
                case REFRESH:
                    //刷新成功的方法
                    onRefreshSuccess(requester, type, data);
                    break;
            }
        }

        /**
         * 网络请求失败的回掉方法
         *
         * @param requester
         * @param type
         * @param e         抛出的异常
         */
        @Override
        public void onError(REQ requester, BasicProxy.ProxyType type, ApiException e) {
            //登录检测
            checkUnLogin(e, getActivity()); //TODO 这里一般跳转到登陆界面
            requestInterceptTouch(false);
            switch (type) {
                case LOAD_DATA:
                    //加载失败的方法
                    onLoadError(requester, type, e);
                    break;
                case REFRESH:
                    //刷新失败的方法
                    onRefreshError(requester, type, e);
                    break;
            }
        }
    }

    /**
     * 初始化控件的方法
     *
     * @param idRes 控件的资源id
     * @param <T>   控件的类型
     * @return
     */
    public <T extends View> T getView(@IdRes int idRes) {
        if (mViewHelper != null) {
            return (T) mViewHelper.getView(idRes);
        }
        return (T) mRootView.findViewById(idRes);
    }

    /**
     * 数据刷新失败
     *
     * @param requester
     * @param type
     * @param e
     */
    protected void onRefreshError(REQ requester, BasicProxy.ProxyType type, ApiException e) {
        ToastUtil.showShortToast(getContext(), "刷新失败:" + e.getMessage());
    }

    /**
     * 数据加载失败
     *
     * @param requester
     * @param type
     * @param e
     */
    protected void onLoadError(REQ requester, BasicProxy.ProxyType type, ApiException e) {
        showErrorView();
    }

    /**
     * 做未登录状态的检测
     *
     * @param e
     * @param activity  如果不想销毁当前Activity就传入null
     * @return
     */
    protected void checkUnLogin(ApiException e, Activity activity) {
        if (e instanceof UnLoginException) {
            //TODO 这里要跳转到登陆界面
            ToastUtil.showShortToast(getContext(),e.getMessage());
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 数据刷新成功
     *
     * @param requester
     * @param type
     * @param data
     */
    protected void onRefreshSuccess(REQ requester, BasicProxy.ProxyType type, DATA data) {
    }
    
    /**
     * 数据加载成功
     *
     * @param requester
     * @param type
     * @param data
     */
    protected void onLoadSuccess(REQ requester, BasicProxy.ProxyType type, DATA data) {
        showContentView();
    }
    
    /**
     * 具体代理的创建由子类去实现
     *
     * @return
     */
    protected abstract RefreshProxy<REQ, DATA> createProxy();
    
    /**
     * 默认页面是要获取网络的
     *
     * @return
     */
    protected boolean isNeedWeb() {
        return true;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentBinding = DataBindingUtil.inflate(inflater, getRootLayoutId(), container, false);
        mRootView = (ViewGroup) mParentBinding.getRoot();

        mContentBinding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false);
        mContentView = mContentBinding.getRoot();

        mRootView.addView(mContentView);
        mViewHelper = new ViewHelper(mRootView);

        mLoadingView = mParentBinding.basicCommonLoadingLayout;
        mErrorView = mParentBinding.basicCommonRetryLayout;

        mErrorView.setOnClickListener(this);
        showContentView();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isNeedWeb()) {
            loadData();
        }
    }

    /**
     * 显示文本布局
     */
    protected void showContentView() {
        hideAndShowView(mContentView, mLoadingView, mErrorView);
    }
    
    /**
     * 显示加载失败的布局
     */
    protected void showErrorView() {
        hideAndShowView(mErrorView, mLoadingView, mContentView);
    }
    
    /**
     * 显示加载中的布局
     */
    protected void showLoadingView() {
//        hideAndShowView(mLoadingView, mContentView, mErrorView);
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        mContentView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    /**
     * 设置三块布局的显示和隐藏
     *
     * @param showView
     * @param hideView
     */
    private void hideAndShowView(View showView, View... hideView) {
        if (hideView == null || hideView.length == 0) return;
        showView.setVisibility(View.VISIBLE);
        for (View view : hideView) {
            view.setVisibility(View.GONE);
        }
    }


    /**
     * 页面内容的布局ID
     *
     * @return
     */
    @LayoutRes
    protected abstract int getContentLayoutId();

    /**
     * 返回根布局，如果子类不想用默认的根布局，可以重写此方法
     *
     * @return
     */
    @LayoutRes
    protected int getRootLayoutId() {
        return R.layout.layout_common_loading_and_retry_layout;
    }

    /**
     * 加载数据的方法
     */
    protected void loadData() {
        showLoadingView();
        if (!mBasicProxy.isLoading()) {
            //请求拦截屏幕的触摸事件，加载数据的时候不可点击屏幕
            requestInterceptTouch(true);
            mBasicProxy.request(BasicProxy.ProxyType.LOAD_DATA, getRequestId());
        }
    }

    /**
     * 下拉刷新的方法
     */
    protected void refreshData() {
        if (!mBasicProxy.isLoading()) {
            requestInterceptTouch(true);
            mBasicProxy.request(BasicProxy.ProxyType.REFRESH, getRequestId());
        }
    }

    /**
     * 让子类返回请求体
     *
     * @return
     */
    protected abstract REQ getRequestId();

    /**
     * 拦截页面的触摸事件
     * @param isIntercept
     */
    protected void requestInterceptTouch(boolean isIntercept) {
        if (getActivity() instanceof BasicActivity) {
            ((BasicActivity) getActivity()).requestInterceptTouch(isIntercept);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBasicProxy != null) {
            mBasicProxy.cancel();//取消当前请求
        }
    }

    public boolean onBackPressed() {
        return false;
    }
}
