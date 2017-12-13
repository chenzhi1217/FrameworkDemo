package com.framework.frameworkdemo.proxy.base;

import android.content.Context;
import android.util.Log;

import com.framework.data.exception.ApiException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 月光和我 on 2017/7/13.
 * 网络请求的基类
 */
public abstract class BasicProxy<REQ,DATA> {
    
    ProxyCallBack<REQ,DATA> mProxyCallBack;

    public BasicProxy(Context context) {
    }

    /**
     * 定义网络请求的类型
     */
    public enum ProxyType{

        /**
         * 加载数据
         */
        LOAD_DATA,

        /**
         * 上拉加载
         */
        LOAD_MORE,

        /**
         * 下拉刷新
         */
        REFRESH;
    }

    /**
     * 网络请求结果的回掉接口
     * @param <REQ>
     * @param <DATA>
     */
    public interface ProxyCallBack<REQ,DATA>{
        /**
         * 网络请求成功的回掉方法
         * @param requester 请求体对象
         * @param type      请求类型
         * @param data      后端返回的数据模型
         */
        void onLoadBack(REQ requester, ProxyType type, DATA data);

        /**
         * 网络请求失败的回掉方法
         * @param requester
         * @param type
         * @param e          抛出的异常
         */
        void onError(REQ requester, ProxyType type, ApiException e);
    }

    /**
     * 注册一个回掉接口
     * @param callBack
     */
    public void setProxyCallBack(ProxyCallBack<REQ, DATA> callBack) {
        this.mProxyCallBack = callBack;
    }

    /**
     * 网络请求的入口
     * @param type
     * @param requester
     */
    public void request(ProxyType type, REQ requester) {
        //创建一个订阅者
        BasicSubscriber basicSubscriber = new BasicSubscriber(type, requester);
        // TODO 这里要检测网络
        
        bindData(basicSubscriber,requester);
    }

    /**
     * 开启订阅
     * @param basicSubscriber
     * @param requester
     */
    protected void bindData(BasicSubscriber basicSubscriber, REQ requester) {
        getObservable(requester)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(basicSubscriber);
    }

    /**
     * 让子类去返回一个Observable对象
     * @param requester
     * @return
     */
    protected abstract  Observable<DATA> getObservable(REQ requester) ;

    /**
     * 注册一个订阅者
     */
    class BasicSubscriber extends Subscriber<DATA>{
        private final ProxyType proxyType;

        private final REQ requester;

        BasicSubscriber(ProxyType proxyType, REQ requester) {
            this.proxyType = proxyType;
            this.requester = requester;
        }

        public boolean isRefresh() {
            return proxyType == ProxyType.REFRESH;
        }

        @Override
        public void onCompleted() {
            unsubscribe();
        }

        @Override
        public void onError(Throwable throwable) {
            try {
                if (mProxyCallBack != null) {
                    if (throwable instanceof ApiException) {
                        mProxyCallBack.onError(requester, proxyType, (ApiException) throwable);
                    } else {
                        mProxyCallBack.onError(requester, proxyType, new ApiException(throwable.getMessage(), throwable));
                    }
                }
            } catch (Exception e) {
                Log.e("BasicProxy", "error:" + e.getMessage());
                mProxyCallBack.onError(requester, proxyType, new ApiException(e.getMessage()));
            }
            
        }
        
        @Override
        public void onNext(DATA data) {
            try {
                if (mProxyCallBack != null) {
                    mProxyCallBack.onLoadBack(requester, proxyType, data);
                }
            } catch (Exception e) {
                Log.e("BasicProxy", "error:" + e.getMessage());
                mProxyCallBack.onError(requester, proxyType, new ApiException(e.getMessage()));
            }
        }
    }
}
