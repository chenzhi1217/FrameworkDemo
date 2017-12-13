package com.framework.frameworkdemo.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 描述 所有ViewHolder的基类。
 * 创建人 kelin
 * 创建时间 2017/5/30  下午9:23
 * 版本 v 1.0.0
 */
public abstract class BaseHolder<T,B extends ViewDataBinding> extends RecyclerView.ViewHolder{


    private B mBinding;

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public BaseHolder(ViewGroup parent,int resId) {
        super(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),resId,parent,false).getRoot());
        mBinding = DataBindingUtil.getBinding(this.itemView);
    }

    public void bindHolder(T data){
        bindData(data);
        mBinding.executePendingBindings();
    }

    protected abstract void bindData(T data);


    public void onViewRecycled() {}
}
