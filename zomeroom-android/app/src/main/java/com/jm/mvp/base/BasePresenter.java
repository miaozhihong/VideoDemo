/**
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.jm.mvp.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.List;

import cn.com.yktour.network.base.IModel;
import cn.com.yktour.network.base.IPresenter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by sky on 2017/11/7.
 * description: APP 基类 Presenter
 * changed: 创建
 */
public class BasePresenter<M extends IModel> implements IPresenter, LifecycleObserver {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;
    protected M mModel;
    protected List<IModel> mModels;

    public BasePresenter() {
        mModels = new ArrayList<>();
        //onStart();
    }

    /**
     * 应对多个Model情况，数组循环Destroy
     * @param model
     */
    public void addModel(IModel model) {
        mModels.add(model);
    }

    /**
     * 数据层继续封装
     * @param model 公共的数据仓库
     */
    public BasePresenter(M model) {
        if (model == null) {
            return;
        }
        this.mModel = model;
        //onStart();
    }

    /**
     * 废弃
     */
    @Override
    public void onStart() {
    }

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    @Override
    public void onDestroy() {
        unDispose();//解除订阅
        if (mModel != null){
            mModel.onDestroy();
            this.mModel = null;
        }
        if (mModels != null) {
            for (int i = 0; i < mModels.size(); i++) {
                IModel model = mModels.get(i);
                if (model != null) {
                    model.onDestroy();
                    model = null;
                }
            }
        }
        this.mCompositeDisposable = null;
    }

    /**
     * 注解生命周期，同步通知
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
            mCompositeDisposable = null;
        }
    }
}
