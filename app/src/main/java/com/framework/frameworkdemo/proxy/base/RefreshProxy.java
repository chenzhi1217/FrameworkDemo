package com.framework.frameworkdemo.proxy.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.framework.data.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by 月光和我 on 2017/7/13.
 * 在网络请求基类的基础上做了刷新的状态的显示
 */
public abstract class RefreshProxy<ID,DATA> extends BasicProxy<ID,DATA> {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;//记录当前是否为正在加载的状态
    public RefreshProxy(Context context) {
        super(context);
    }

    private List<Subscription> mSubscriptions = new ArrayList<>();
    
    /**
     * 将下拉刷新控件传递过来
     * @param refreshLayout
     */
    public void setRefreshLayout(View refreshLayout) {
        if (refreshLayout instanceof SwipeRefreshLayout) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) refreshLayout;
            this.mSwipeRefreshLayout = swipeRefreshLayout;
        }
    }

    /**
     * 设置下拉刷新的状态
     * @param flag      下拉刷新的装哎
     * @param isRefresh 记录当前是否为下拉刷新
     */
    public void setRefreshStatus(final boolean flag, boolean isRefresh) {
        if (mSwipeRefreshLayout == null) return;
        if (mSwipeRefreshLayout.isRefreshing() != flag) {
            mSwipeRefreshLayout.setRefreshing(flag);
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(flag);
                    }
                }
            });
        }
    }

    /**
     * 网络请求的入口
     * @param type
     * @param requester
     */
    @Override
    public void request(ProxyType type, ID requester) {
        if (isLoading()) return;
        //创建观察者对象
        RefreshSubscribe subscribe = new RefreshSubscribe(type, requester);
        bindData(subscribe, requester);
    }

    /**
     * 开始获取网络数据
     * @param basicSubscriber
     * @param requester
     */
    @Override
    protected void bindData(BasicSubscriber basicSubscriber, ID requester) {
        if (mSwipeRefreshLayout != null && basicSubscriber.isRefresh()) {
            setRefreshStatus(true,basicSubscriber.isRefresh());
        }
        mSubscriptions.add(basicSubscriber);
        isLoading = true;
        super.bindData(basicSubscriber, requester);
    }

    /**
     * 取消当前请求
     */
    public void cancel() {
        if (mSubscriptions == null || mSubscriptions.size() > 0) {
            for (Subscription mSubscription : mSubscriptions) {
                if (!mSubscription.isUnsubscribed()) {
                    mSubscription.unsubscribe();
                }
            }
        }
    }

    /**
     * 判断当前是否处于加载中的状态
     * @return
     */
    public boolean isLoading() {
        return isLoading;
    }

    /**
     * 定义一个订阅者，相对于BaseSubscriber只是加入了下拉状态的处理
     */
    class RefreshSubscribe extends BasicSubscriber{
        
        RefreshSubscribe(ProxyType proxyType, ID requester) {
            super(proxyType, requester);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            setRefreshStatus(false, isRefresh());
            isLoading = false;
        }

        @Override
        public void onNext(DATA data) {
            super.onNext(data);
            setRefreshStatus(false, isRefresh());
            isLoading = false;
        }
    }
}
