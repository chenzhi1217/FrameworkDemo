package com.framework.frameworkdemo.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framework.frameworkdemo.holder.BaseHolder;
import com.framework.frameworkdemo.holder.LoadMoreViewHolder;

import java.util.List;

/**
 * 描述 所有RecyclerView的适配器的基类。
 * 创建人 kelin
 * 创建时间 2017/5/30  下午9:23
 * 版本 v 1.0.0
 */
public abstract class BaseRecyclerViewAdapter<D> extends RecyclerView.Adapter<BaseHolder<D,? extends ViewDataBinding>> {

    private List<D> items;
    private ViewTypeMapper<D> mapper;
    private OnItemEventListener<D> onItemEventListener;
    private LoadMoreManager mLoadMoreManager;
    private LoadMoreCallback mLoadMoreCallback;
    private LoadMoreRetryClickListener mLoadMoreRetryClickListener;
    private boolean mLoadMoreEnable = true;
    private ListenerInterceptor mListenerInterceptor;

    public BaseRecyclerViewAdapter(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (checkLoadMoreAvailable() && layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
                    if (mLoadMoreManager.isInTheLoadMore() || mLoadMoreManager.isNoMoreState())
                        return;
                    int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                    int targetPosition = items.size() - mLoadMoreManager.getLoadMoreOffset();
                    if (targetPosition == 0 || lastVisibleItemPosition >= targetPosition) {
                        startLoadMore();

                    }
                }
            }
        });
    }

    public BaseRecyclerViewAdapter(List<D> items) {
        this.items = items;
    }

    /**
     * 给Adapter设置适配器设置数据
     *
     * @param items
     */
    public void setItems(List<D> items) {
        this.items = items;
    }

    public List<D> getDataList() {
        return items;
    }

    public void setViewTypeMapper(ViewTypeMapper<D> mapper) {
        this.mapper = mapper;
    }

    public void setOnItemEventListener(OnItemEventListener<D> onItemEventListener) {
        this.onItemEventListener = onItemEventListener;
    }

    /**
     * 设置加载更多时显示的布局。
     *
     * @param loadMoreLayoutId 加载更多时显示的布局的资源ID。
     * @param retryLayoutId    加载更多失败时显示的布局。
     * @param callback         加载更多的回调。
     */
    public void setLoadMoreView(@LayoutRes int loadMoreLayoutId, @LayoutRes int retryLayoutId, int pageSize, @NonNull LoadMoreCallback callback) {
        setLoadMoreView(loadMoreLayoutId, retryLayoutId, 0, pageSize, callback);
    }

    /**
     * 设置加载更多时显示的布局。
     *
     * @param loadMoreLayoutId   加载更多时显示的布局的资源ID。
     * @param retryLayoutId      加载更多失败时显示的布局。
     * @param noMoreDataLayoutId 没有更多数据时显示的布局。
     * @param callback           加载更多的回调。
     */
    public void setLoadMoreView(@LayoutRes int loadMoreLayoutId, @LayoutRes int retryLayoutId, @LayoutRes int noMoreDataLayoutId, int pageSize, @NonNull LoadMoreCallback callback) {
        setLoadMoreView(loadMoreLayoutId, retryLayoutId, noMoreDataLayoutId, 0, pageSize, callback);
    }

    /**
     * 设置加载更多时显示的布局。
     *
     * @param loadMoreLayoutId   加载更多时显示的布局的资源ID。
     * @param retryLayoutId      加载更多失败时显示的布局。
     * @param noMoreDataLayoutId 没有更多数据时显示的布局。
     * @param offset             加载更多触发位置的偏移值。偏移范围只能是1-10之间的数值。正常情况下是loadMoreLayout显示的时候就开始触发，
     *                           但如果设置了该值，例如：2，那么就是在loadMoreLayout之前的两个位置的时候开始触发。
     * @param callback           加载更多的回调。
     */
    public void setLoadMoreView(@LayoutRes int loadMoreLayoutId, @LayoutRes int retryLayoutId, @LayoutRes int noMoreDataLayoutId, @Size(min = 1, max = 10) int offset, int pageSize, @NonNull LoadMoreCallback callback) {
        setLoadMoreView(new LoadMoreManager(loadMoreLayoutId, retryLayoutId, noMoreDataLayoutId, offset, pageSize), callback);
    }

    public void setListenerInterceptor(ListenerInterceptor interceptor) {
        mListenerInterceptor = interceptor;
    }

    /**
     * 设置加载更多时显示的布局。
     *
     * @param layoutInfo LoadMore布局信息对象。
     * @param callback   加载更多的回调。
     */
    public void setLoadMoreView(@NonNull LoadMoreManager layoutInfo, @NonNull LoadMoreCallback callback) {
        mLoadMoreManager = layoutInfo;
        mLoadMoreCallback = callback;
    }

    public boolean isLoadMoreItem(int position) {
        return checkLoadMoreAvailable() && !mLoadMoreManager.noCurStateLayoutId() && position == getItemCount() - 1;
    }

    /**
     * 开始加载更多。
     */
    private void startLoadMore() {
        if (mLoadMoreCallback != null) {
            mLoadMoreManager.setInTheLoadMore(true);
            mLoadMoreCallback.onLoadMore();
        }
    }

    /**
     * 当加载更多完成后要调用此方法，否则不会触发下一次LoadMore事件。
     */
    public void setLoadMoreFinished() {
        mLoadMoreManager.setInTheLoadMore(false);
//        notifyItemRangeInserted(lastRangeStar, getItemCount() - lastRangeStar);
//        Log.i("MultiTypeAdapter", "加载完成");
    }

    /**
     * 当加载更多失败后要调用此方法，否则没有办法点击重试加载更多。
     */
    public void setLoadMoreFailed() {
        if (checkLoadMoreAvailable()) {
            int position = getItemCount() - 1;
            mLoadMoreManager.setRetryState();
            notifyItemChanged(position);
//            LogUtil.i("MultiTypeAdapter   加载完成");
        }
    }

    /**
     * 如果你的页面已经没有更多数据可以加载了的话，应当调用此方法。调用了此方法后就不会再触发LoadMore事件，否则还会触发。
     */
    public void setNoMoreData() {
        if (checkLoadMoreAvailable()) {
            int position = getItemCount() - 1;
            mLoadMoreManager.setNoMoreState();
            notifyItemChanged(position);
        }
    }

    public void reSetLoadMoreState() {
        if (checkLoadMoreAvailable()) {
            int position = getItemCount() - 1;
            mLoadMoreManager.reSetState();
//            if (position >= 0) {
//                notifyItemChanged(position);
//            }
        }
    }

    private boolean checkLoadMoreAvailable() {
        return mLoadMoreEnable && mLoadMoreManager != null && items != null && items.size() >= mLoadMoreManager.mPageSize;
    }

    public LoadMoreManager getLoadMoreManager() {
        return mLoadMoreManager;
    }

    @Override
    public BaseHolder<D,? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View clickView;
        if (checkLoadMoreAvailable() && viewType == mLoadMoreManager.getCurStateLayoutId()) {
            View loadMoreView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            BaseHolder loadMoreViewHolder = new LoadMoreViewHolder(loadMoreView);
            if (mLoadMoreManager.isRetryState()) {
                if (mLoadMoreRetryClickListener == null) {
                    mLoadMoreRetryClickListener = new LoadMoreRetryClickListener();
                }
                loadMoreView.setOnClickListener(mLoadMoreRetryClickListener);
            }
            return loadMoreViewHolder;
        }
        final BaseHolder<? extends D,? extends ViewDataBinding> holder = onCreateItemHolder(parent, viewType);
        return (BaseHolder<D,? extends ViewDataBinding>) holder;
    }

    protected abstract BaseHolder<? extends D,? extends ViewDataBinding> onCreateItemHolder(ViewGroup parent, int viewType);
    
    @Override
    public void onBindViewHolder(BaseHolder<D,? extends ViewDataBinding> holder, int position) {
        if (isLoadMoreItem(position)) return;
        holder.bindHolder(items.get(position));
    }
    
    @Override
    public void onViewRecycled(BaseHolder<D,? extends ViewDataBinding> holder) {
        if (holder != null) {
            holder.onViewRecycled();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreItem(position)) {
            return mLoadMoreManager.getCurStateLayoutId();//根据上拉加载的不同时段显示不同的类型
        } else {
            return mapper == null ? 0 : mapper.onMapViewType(items.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size() + (mLoadMoreManager != null && !mLoadMoreManager.noCurStateLayoutId() && items.size() >= mLoadMoreManager.mPageSize && mLoadMoreEnable ? 1 : 0);
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        mLoadMoreEnable = loadMoreEnable;
    }

    public D getObject(int position) {
        return items == null || items.isEmpty() || position >= items.size() ? null : items.get(position);
    }

    /**
     * 用来将数据模型映射到具体的ItemType的类。
     *
     * @param <D> 数据模型。
     */
    public interface ViewTypeMapper<D> {
        /**
         * 根据当前的数据模型区分映射成对应的ItemViewType。
         *
         * @param d        当前的数据模型。
         * @param position 当前的Adapter中的位置。
         * @return 返回对应的ViewType，推荐使用 {@link LayoutRes} 布局资源ID做为Type。
         */
        @LayoutRes
        int onMapViewType(D d, int position);
    }

    public interface OnItemEventListener<D> {
        
        /**
         * 当条目或指定的条目中的用来取代条目的控件被点击后调用。
         *
         * @param d        当前条目的数据模型。
         * @param position 当前被点击的位置。
         */
        void onItemClick(D d, int position);
        
        /**
         *
         * @param d        当前条目的数据模型。
         * @param position 当前被点击的位置。
         * @param child    当前被点击的子View。
         */
        void onItemChildClick(D d, int position, View child);
    }

    /**
     * 加载更多的回调对象。
     */
    public abstract static class LoadMoreCallback {

        /**
         * 加载更多时的回调。
         */
        public abstract void onLoadMore();
    }

    /**
     * 描述 用来描述加载更多的布局信息。
     * 创建人 kelin
     * 创建时间 2017/5/3  下午2:39
     * 版本 v 1.0.0
     */
    private static class LoadMoreManager {

        private static final int STATE_FAILED = 0X0000_00F0;
        private static final int STATE_NO_MORE = 0X0000_00F1;
        private static final int STATE_LOAD = 0X0000_00F2;
        private final int mLoadMoreOffset;
        private final int mPageSize;

        private int mCurState = STATE_LOAD;
        /**
         * 加载更多时显示的布局文件ID。
         */
        private int mLoadMoreLayoutId;
        /**
         * 加载更多失败时显示的布局文件ID。
         */
        private int mRetryLayoutId;
        /**
         * 没有更多数据时显示的布局文件ID。
         */
        private int mNoMoreDataLayoutId;
        /**
         * 是否正在加载更多，通过此变量做判断，防止LoadMore重复触发。
         */
        private boolean mIsInTheLoadMore;

        /**
         * 构建一个加载更多的布局信息对象。
         *
         * @param loadMoreLayoutId   加载更多时显示的布局的资源ID。
         * @param retryLayoutId      加载更多失败时显示的布局。
         * @param noMoreDataLayoutId 没有更多数据时显示的布局。
         * @param offset             加载更多触发位置的偏移值。偏移范围只能是1-10之间的数值。正常情况下是loadMoreLayout显示的时候就开始触发，
         *                           但如果设置了该值，例如：2，那么就是在loadMoreLayout之前的两个位置的时候开始触发。
         */
        LoadMoreManager(@LayoutRes int loadMoreLayoutId, @LayoutRes int retryLayoutId, @LayoutRes int noMoreDataLayoutId, @Size(min = 1, max = 10) int offset, int pageSize) {
            mLoadMoreLayoutId = loadMoreLayoutId;
            mRetryLayoutId = retryLayoutId;
            mNoMoreDataLayoutId = noMoreDataLayoutId;
            mLoadMoreOffset = offset < 0 ? 0 : offset > 10 ? 10 : offset;
            mPageSize = pageSize;
        }

        void setInTheLoadMore(boolean isInTheLoadMore) {
            mIsInTheLoadMore = isInTheLoadMore;
        }

        boolean isInTheLoadMore() {
            return mIsInTheLoadMore;
        }

        void setRetryState() {
            mCurState = STATE_FAILED;
        }

        boolean isRetryState() {
            return mCurState == STATE_FAILED;
        }

        void setNoMoreState() {
            setInTheLoadMore(false);
            mCurState = STATE_NO_MORE;
        }

        void reSetState() {
            setInTheLoadMore(false);
            mCurState = STATE_LOAD;
        }

        boolean isNoMoreState() {
            return mCurState == STATE_NO_MORE;
        }

        void setLoadState() {
            mCurState = STATE_LOAD;
        }

        @LayoutRes
        int getCurStateLayoutId() {
            switch (mCurState) {
                case STATE_LOAD:
                    return mLoadMoreLayoutId;
                case STATE_FAILED:
                    return mRetryLayoutId;
                case STATE_NO_MORE:
                    return mNoMoreDataLayoutId;
                default:
                    throw new RuntimeException("the current state is unknown!");
            }
        }

        boolean noCurStateLayoutId() {
            return getCurStateLayoutId() == 0;
        }

        int getLoadMoreOffset() {
            return mLoadMoreOffset;
        }
    }


    private class LoadMoreRetryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mLoadMoreManager.setLoadState();
            notifyItemChanged(getItemCount() - 1);
            startLoadMore();
        }
    }

    public interface ListenerInterceptor{
        /**
         * @param v 要绑定的View。
         * @return 是否拦截绑定，如果拦截则不会对其进行绑定点击事件，否则将会为其绑定点击事件。
         */
        boolean onInterceptor(View v);
    }
}
