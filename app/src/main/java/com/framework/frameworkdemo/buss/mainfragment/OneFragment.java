package com.framework.frameworkdemo.buss.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.data.exception.ApiException;
import com.framework.data.model.TestModel;
import com.framework.frameworkdemo.R;
import com.framework.frameworkdemo.base.BasicFragment;
import com.framework.frameworkdemo.buss.test.TestActivity;
import com.framework.frameworkdemo.databinding.FragmentOneBinding;
import com.framework.frameworkdemo.proxy.base.BasicProxy;
import com.framework.frameworkdemo.proxy.base.RefreshProxy;
import com.framework.frameworkdemo.proxy.base.TestProxy;
import com.framework.frameworkdemo.proxy.base.TestRequest;

/**
 * Created by 月光和我 on 2017/8/22.
 */

public class OneFragment extends BasicFragment<TestRequest, TestModel,FragmentOneBinding> {

    TestRequest testRequest = new TestRequest();

    /**
     * 具体代理的创建由子类去实现
     *
     * @return
     */
    @Override
    protected RefreshProxy<TestRequest, TestModel> createProxy() {
        return new TestProxy(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 页面内容的布局ID
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_one;
    }

    /**
     * 让子类返回请求体
     *0
     * @return
     */
    @Override
    protected TestRequest getRequestId() {
        return testRequest;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.menu_class, menu);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv = (ImageView) getView().findViewById(R.id.fragment_one_iv);

        /*RotateAnimation animation = new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5F);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        iv.startAnimation(animation);*/

        /*Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(300);
        iv.setAnimation(animation);
        animation.start();*/
        /*AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        iv.startAnimation(alphaAnimation);*/

        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 100f, 0f, 0f);
        translateAnimation.setDuration(200);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
//        iv.startAnimation(translateAnimation);
    }

    
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            //Toast.makeText(getContext(),"搜索",Toast.LENGTH_SHORT).show();
            //这里我来测试网络框架
//            testHttp();
            startActivity(new Intent(getContext(), TestActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void testHttp() {

        TestProxy testProxy = new TestProxy(getContext());

        testProxy.setProxyCallBack(new BasicProxy.ProxyCallBack<TestRequest, TestModel>() {
            @Override
            public void onLoadBack(TestRequest requester, BasicProxy.ProxyType type, TestModel testModel) {
                Toast.makeText(getContext(), "成功了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(TestRequest requester, BasicProxy.ProxyType type, ApiException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        testProxy.request(BasicProxy.ProxyType.REFRESH, testRequest);
    }
}
