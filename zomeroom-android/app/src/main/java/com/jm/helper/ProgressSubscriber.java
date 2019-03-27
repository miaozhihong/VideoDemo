package com.jm.helper;

import android.text.TextUtils;

import com.jm.mvp.base.BasePresenter;
import com.jm.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.com.yktour.network.ApiException;
import cn.com.yktour.network.base.IProgress;
import cn.com.yktour.network.subscribers.SubscriberOnNextListener;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author sky
 * @date 2017/11/19
 * description:用于在Http请求开始时，自动显示一个ProgressDialog
 * changed:创建
 */
public class ProgressSubscriber<T> implements Observer<T> {

    private final boolean canShow;//是否显示进度条
    private SubscriberOnNextListener<T> mSubscriberOnNextListener;

    private IProgress mIProgress;
    private BasePresenter mPresenter;
    private int mType;

    /**
     * @param mSubscriberOnNextListener
     * @param canShow  是否显示进度条
     */
    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, IProgress mIProgress, BasePresenter mPresenter, boolean canShow) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.canShow = canShow;
        this.mIProgress = mIProgress;
        this.mPresenter = mPresenter;
        OkHttp3Utils.isUseCatch = false;
    }

    /**
     * @param mSubscriberOnNextListener
     */
    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, IProgress mIProgress, BasePresenter mPresenter) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.canShow = true;
        this.mIProgress = mIProgress;
        this.mPresenter = mPresenter;
        OkHttp3Utils.isUseCatch = false;
    }

    /**
     * @param mSubscriberOnNextListener
     * @param mPresenter
     * @param canShow                   是否显示进度条
     * @param isUseCatch                是否使用缓存
     */
    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, IProgress mIProgress, BasePresenter mPresenter, boolean canShow, boolean isUseCatch) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.canShow = canShow;
        this.mPresenter = mPresenter;
        OkHttp3Utils.isUseCatch = isUseCatch;
        this.mIProgress = mIProgress;
    }
    /**
     * @param mSubscriberOnNextListener
     * @param mPresenter
     * @param canShow                    是否显示进度条
     * @param isUseCatch                是否使用缓存
     * @param type                       单个页面用来区分不同请求的 onError
     */
    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, IProgress mIProgress, BasePresenter mPresenter, boolean canShow , boolean isUseCatch , int type) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.canShow = canShow;
        this.mPresenter = mPresenter;
        OkHttp3Utils.isUseCatch = isUseCatch;
        this.mIProgress = mIProgress;
        this.mType = type;
    }

    private void showProgressDialog() {
        if (mIProgress != null && canShow) {
            mIProgress.showLoading();
        }
    }

    private void dismissProgressDialog() {
        if (mIProgress != null && canShow) {
            mIProgress.hideLoading();
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    private Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
        mPresenter.addDispose(d);
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        boolean isToast = true;
        int code = 0;
        if (e instanceof SocketTimeoutException) {
            ToastUtils.ToastCenter("请求超时，请检查您的网络状态");
            isToast = false;
        } else if (e instanceof ConnectException) {
            ToastUtils.ToastCenter("无法连接服务器，请检查您的网络状态");
            isToast = false;
        } else if (e instanceof HttpException) {
            //HTTP错误
            code = ((HttpException) e).code();
            doNetErr(code);
            e.printStackTrace();
            isToast = false;
        } else if (e instanceof UnknownHostException) {  //无网络
            ToastUtils.ToastCenter("网络异常，请检查您的网络状态");
            isToast = false;
            e.printStackTrace();
        } else if (e instanceof ApiException) {
            code = ((ApiException) e).code;
            e.printStackTrace();
        } else {
            isToast = false;
            ToastUtils.ToastCenter("服务器访问异常，请稍后重试");
            e.printStackTrace();
        }
        //Code为 50001时，跳转登录页面
//        if (code == 50001) {
//            mIProgress.toLogin();
//        }
        dismissProgressDialog();
        //将error信息回调到首页
        boolean isDispose = mIProgress.httpError(code, e.getMessage(), mType);
        if (!isToast) {
            return;
        }
        if (!isDispose &&!TextUtils.isEmpty(e.getMessage())) {
            ToastUtils.ToastCenter(e.getMessage());
        }
    }

    /**
     * 分析网络异常
     *
     * @param code
     */
    private void doNetErr(int code) {
        switch (code) {
            case 404:
                ToastUtils.ToastCenter("服务器访问异常，请稍后重试");
                break;
            default:
                ToastUtils.ToastCenter("网络异常，请检查您的网络状态");
                break;
        }

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }
}