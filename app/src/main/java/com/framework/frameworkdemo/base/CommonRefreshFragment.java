package com.framework.frameworkdemo.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.framework.data.exception.ApiException;
import com.framework.data.requester.BaseRequester;
import com.framework.data.util.ToastUtil;
import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.adapter.BaseRecyclerViewAdapter;
import com.framework.frameworkdemo.holder.BaseHolder;
import com.framework.frameworkdemo.proxy.base.BasicProxy;

import java.util.List;

/**
 * Created by chenzhi on 2017/11/19.
 */

public abstract class CommonRefreshFragment<REQ extends BaseRequester,DATA,ITEM,BIND extends ViewDataBinding> extends BasicFragment<REQ,DATA,BIND> {

    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mRecyclerViewManager;
    protected BaseRecyclerViewAdapter<ITEM> mRecyclerViewAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.common_recycler_view_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //设置下拉刷新
        mSwipeRefresh = getView(R.id.sf_common_swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNeedWeb() && !mBasicProxy.isLoading()) {
                    refreshData();
                }
            }
        });
        //设置下拉刷新的样式
        mSwipeRefresh.setColorSchemeResources(getSwipeColor());
        mBasicProxy.setRefreshLayout(mSwipeRefresh);

        //设置RecyclerView
        mRecyclerView = getView(R.id.rv_common_recycler_view);
        mRecyclerView.setLayoutManager(onCreateLayoutManager());
        mRecyclerViewAdapter = onCreateRecyclerViewAdapter();

        //设置RecyclerView上拉加载的监听
        if (pageEnable()) {
            mRecyclerViewAdapter.setLoadMoreView(getLoadMoreLoadingLayoutId(), getLoadMoreRetryLayoutId(), getLoadMoreNoDataLayoutId(), 1, getRequestId().getPageSize(), new BaseRecyclerViewAdapter.LoadMoreCallback() {
                @Override
                public void onLoadMore() {
                    if (!mBasicProxy.isLoading()) {
                        loadMoreData();
                    }
                }
            });
        }

        //设置Adapter中的ItemType
        mRecyclerViewAdapter.setViewTypeMapper(onCreateItemTypeMapper());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    /**
     * 让子类根据每个条目的数据模型返回当前条目的ItemType
     * @return
     */
    protected abstract BaseRecyclerViewAdapter.ViewTypeMapper<ITEM> onCreateItemTypeMapper();

    /**
     * 上拉加载更多数据的具体实现（如果你的页面支持分页加载）
     */
    protected void loadMoreData() {
        if (pageEnable() && !mBasicProxy.isLoading()) {
            mBasicProxy.request(BasicProxy.ProxyType.LOAD_MORE,getRequestId());
        }
    }

    /**
     * 刷新当前页面的数据
     */
    @Override
    protected void refreshData() {
        if (pageEnable()) {
            getRequestId().reSetPage();
            mRecyclerViewAdapter.reSetLoadMoreState();
        }
        super.refreshData();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void loadData() {
        if (pageEnable() && !mBasicProxy.isLoading()) {
            getRequestId().reSetPage();
            mRecyclerViewAdapter.reSetLoadMoreState();
        }
        super.loadData();
    }

    /**
     * 上拉加载没有数据底部的布局
     * @return
     */
    private int getLoadMoreNoDataLayoutId() {
        return R.layout.layout_no_more_data;
    }

    /**
     * 上拉加载失败底部的布局
     * @return
     */
    private int getLoadMoreRetryLayoutId() {
        return R.layout.layout_load_more_failed;
    }

    /**
     * 上拉加载中的底部布局
     * @return
     */
    private int getLoadMoreLoadingLayoutId() {
        return R.layout.layout_load_more_loading;
    }


    /**
     * 创建RecyclerView的适配器
     * @return
     */
    private BaseRecyclerViewAdapter<ITEM> onCreateRecyclerViewAdapter() {
        return new BaseRecyclerViewAdapter<ITEM>(mRecyclerView) {
            @Override
            protected BaseHolder<? extends ITEM, ? extends ViewDataBinding> onCreateItemHolder(ViewGroup parent, int viewType) {
                return CommonRefreshFragment.this.onCreateViewHolder(parent,viewType);
            }
        };
    }

    /**
     * 让子类创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseHolder<? extends ITEM,? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType);


    /**
     * 设置RecyclerView的manager
     * @return
     */
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        int recyclerColumn = getRecyclerColumn();
        if (recyclerColumn == 1) {
            mRecyclerViewManager = new LinearLayoutManager(getContext());
            return mRecyclerViewManager;
        } else {
            GridLayoutManager gm = new GridLayoutManager(getContext(), recyclerColumn);
            gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mRecyclerViewAdapter.isLoadMoreItem(position)) {
                        return getRecyclerColumn();
                    } else {
                        return CommonRefreshFragment.this.getItemColumnSize(mRecyclerViewAdapter.getObject(position), position);
                    }
                }
            });
            mRecyclerViewManager = gm;
        }
        return mRecyclerViewManager;
    }

    /**
     * 当前条目所占的列数
     * @param object
     * @param position
     * @return
     */
    private int getItemColumnSize(ITEM object, int position) {
        return getRecyclerColumn();
    }

    /**
     * 获取RecyclerView的列数，默认是1列
     * @return
     */
    protected int getRecyclerColumn() {
        return 1;
    }

    /**
     * 设置下拉刷新的样式，子类可以更改
     * @return
     */
    protected  @ColorRes
    int getSwipeColor() {
        return R.color.line_color;
    }

    /**
     * 设置分页功能是否可用
     * @return
     */
    private boolean pageEnable() {
        return true;
    }

    @Override
    protected BasicProxy.ProxyCallBack<REQ, DATA> createProxyCallBack() {
        return new RefreshProxyCallBack();
    }

    /**
     * 这里重新创建此类是为了处理上拉加载的结果回掉
     */
    class RefreshProxyCallBack extends BasicFragmentProxyCallBack{

        /**
         * 网络请求成功的回掉
         * @param requester 请求体对象
         * @param type      请求类型
         * @param data      后端返回的数据模型
         */
        @Override
        public void onLoadBack(REQ requester, BasicProxy.ProxyType type, DATA data) {
            if (pageEnable()) {
                if (pageEnable()) {
                    getRequestId().pgDown();
                }
                if (type == BasicProxy.ProxyType.LOAD_MORE) {
                    requestInterceptTouch(false);
                    onLoadMoreSuccess(requester, data);
                } else {
                    super.onLoadBack(requester,type,data);
                }
            }
        }


        /**
         * 网络请求失败的回掉
         * @param requester
         * @param type
         * @param e         抛出的异常
         */
        @Override
        public void onError(REQ requester, BasicProxy.ProxyType type, ApiException e) {

            if (type == BasicProxy.ProxyType.LOAD_MORE) {
                requestInterceptTouch(false);
                checkUnLogin(e,getActivity());
                onLoadMoreError(requester, e);
            } else {
                super.onError(requester, type, e);
            }
        }
    }

    /**
     * 上拉加载失败的回掉
     * @param requester
     * @param e
     */
    private void onLoadMoreError(REQ requester, ApiException e) {
        if (e != null) {
            ToastUtil.showShortToast(getContext(),e.getMessage());
        }
        mRecyclerViewAdapter.setLoadMoreFailed();
    }

    /**
     * 上拉加载成功的回掉
     * @param requester
     * @param data
     */
    private void onLoadMoreSuccess(REQ requester, DATA data) {
        List<ITEM> loadMoreData = converDataToList(data);
        if (loadMoreData != null && loadMoreData.size() > 0) {
            mRecyclerViewAdapter.getDataList().addAll(loadMoreData);
        }
        checkIsLastPage(loadMoreData);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 首次加载数据成功
     * @param requester
     * @param type
     * @param data
     */
    @Override
    protected void onLoadSuccess(REQ requester, BasicProxy.ProxyType type, DATA data) {
        super.onLoadSuccess(requester, type, data);
        setInitData(data);
    }

    /**
     * 首次加载数据失败
     * @param requester
     * @param type
     * @param e
     */
    @Override
    protected void onLoadError(REQ requester, BasicProxy.ProxyType type, ApiException e) {
        super.onLoadError(requester, type, e);
    }

    /**
     * 下拉刷新成功
     * @param requester
     * @param type
     * @param data
     */
    @Override
    protected void onRefreshSuccess(REQ requester, BasicProxy.ProxyType type, DATA data) {
        setInitData(data);
    }

    /**
     * 下拉刷新失败
     * @param requester
     * @param type
     * @param e
     */
    @Override
    protected void onRefreshError(REQ requester, BasicProxy.ProxyType type, ApiException e) {
        super.onRefreshError(requester, type, e);
    }

    /**
     * 将网络加载的数据设置到Adapter中去
     * @param data
     */
    protected void setInitData(DATA data) {
        List<ITEM> items = converDataToList(data);
        mRecyclerViewAdapter.setItems(items);
        //判断是否为最后一页
        checkIsLastPage(items);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 检测当前是否为最后一页
     * @param items
     */
    private void checkIsLastPage(List<ITEM> items) {
        if (pageEnable()) {
            if (items == null && items.size() == 0) {
                mRecyclerViewAdapter.setNoMoreData();
            } else {
                mRecyclerViewAdapter.setLoadMoreFinished();
            }
        }
    }

    /**
     * 将网络返回的数据转提取到List集合中去
     * @param data
     */
    protected abstract List<ITEM> converDataToList(DATA data);

}
