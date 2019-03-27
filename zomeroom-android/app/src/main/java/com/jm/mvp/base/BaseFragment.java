/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jm.mvp.base;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jm.mvp.ui.activity.LoginActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.yktour.network.base.IFragment;
import cn.com.yktour.network.base.IPresenter;

/**
 * Created by sky on 2017/8/7.
 * description: APP BaseFragment用来封装一些公共方法
 * changed: 创建
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment<P> {
    protected final String TAG = this.getClass().getSimpleName();
    protected P mPresenter;
    private Unbinder mUnbinder;

    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //返回 FragmentView
        View rootView = initView(inflater, container, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, rootView);
        setPresenter();
        initData(savedInstanceState);
        return rootView;
    }

    /**
     * 设置Presenter到父类统一管理
     *
     * @param presenter 子类中实现的Presenter
     */
    @Override
    public void setPresenter(P presenter) {

    }

    public void setPresenter() {
        this.mPresenter = obtainPresenter();
        //google最新框架实现注解获得生命周期状态
        if (mPresenter != null) {
            getLifecycle().addObserver((LifecycleObserver) mPresenter);
        }
    }

    /**
     * 页面重建时，重新设置Presenter
     *
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = obtainPresenter();
        }
    }

    /**
     * onDetach，执行各种注销置空操作
     */
    @Override
    public void onDetach() {
        super.onDetach();
        //执行P层注销操作
        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
        this.mPresenter = null;
        //注解解绑
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        this.mUnbinder = null;
    }

    public void showLoading() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).showLoading();
    }

    public void hideLoading() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).hideLoading();
    }

    public void toLogin() {
        if (getActivity() != null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }
}
