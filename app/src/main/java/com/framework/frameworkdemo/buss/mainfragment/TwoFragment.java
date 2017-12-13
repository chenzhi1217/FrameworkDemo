package com.framework.frameworkdemo.buss.mainfragment;

import android.databinding.ViewDataBinding;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;

import com.framework.data.gen.Student;
import com.framework.data.greendao.DaoSession;
import com.framework.data.model.TestModel;
import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.adapter.BaseRecyclerViewAdapter;
import com.framework.frameworkdemo.base.CommonRefreshFragment;
import com.framework.frameworkdemo.base.GreenDaoConfig;
import com.framework.frameworkdemo.databinding.CommonRecyclerViewLayoutBinding;
import com.framework.frameworkdemo.holder.BaseHolder;
import com.framework.frameworkdemo.holder.testHolder;
import com.framework.frameworkdemo.proxy.base.RefreshProxy;
import com.framework.frameworkdemo.proxy.base.TestProxy;
import com.framework.frameworkdemo.proxy.base.TestRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 月光和我 on 2017/8/22.
 */
public class TwoFragment extends CommonRefreshFragment<TestRequest,TestModel,Object,CommonRecyclerViewLayoutBinding> {
        public static final int TYPE_ITEM = R.layout.test_item;
    @Override
    protected BaseRecyclerViewAdapter.ViewTypeMapper<Object> onCreateItemTypeMapper() {
                    return new BaseRecyclerViewAdapter.ViewTypeMapper<Object>() {
                        @Override
                        public int onMapViewType(Object o, int position) {
                            return TYPE_ITEM;
            }
        };
    }

    @Override
    protected BaseHolder<?, ? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new testHolder(parent,viewType);
    }

    @Override
    protected List<Object> converDataToList(TestModel o) {
//        greenDaoDemo();
//        queryUser();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 15; i ++) {
            list.add(i + "");
        }
        return list;
    }

    private void greenDaoDemo() {
        DaoSession daoSession = GreenDaoConfig.getSession();
//        UserModelDao userModelDao = daoSession.getUserModelDao();
        Student userModel = new Student(18351, "陈智", "18226189030");
//        userModelDao.insert(userModel);

        daoSession.insert(userModel);
    }

    private void queryUser() {
        DaoSession daoSession = GreenDaoConfig.getSession();
        List<?> list = daoSession.getDao(Student.class).queryBuilder().list();
    }

    protected RefreshProxy<TestRequest, TestModel> createProxy() {
        return new TestProxy(getContext());
    }

    @Override
    protected TestRequest getRequestId() {
        return new TestRequest();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
