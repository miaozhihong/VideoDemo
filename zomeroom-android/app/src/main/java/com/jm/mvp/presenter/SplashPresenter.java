package com.jm.mvp.presenter;

import com.google.gson.Gson;
import com.jm.bean.CallInfo;
import com.jm.bean.CommonBean;
import com.jm.bean.UpTelAddBean;
import com.jm.helper.HttpUtils;
import com.jm.helper.ProgressSubscriber;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.SplashContract;
import com.jm.mvp.service.CallInfoService;
import com.jm.utils.CommonUtils;

import java.util.List;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author pc 张立男
 * @Description SplashPresenter 引导页面
 * @date 2018/6/23 15:34
 * o(＞﹏＜)o
 */

public class SplashPresenter extends BasePresenter {
    public SplashContract.SplashView mView;

    public SplashPresenter(SplashContract.SplashView view) {
        mView = view;
    }

    /**
     * 检测是否登录
     */
    public void checkLogin() {
        HttpUtils.service
                .checkLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<CommonBean>(new SubscriberOnNextListener<CommonBean>() {
                    @Override
                    public void onNext(CommonBean bean) {
                        mView.checkSuccess();
                    }
                }, mView, this));
    }

    /**
     * 上传通话记录
     */
    public void addTel() {
        //  获取通讯录
        List<CallInfo> infos = CallInfoService.getCallInfos(SampleApplicationContext.context);
        List<UpTelAddBean> upTelAddBeanList = CommonUtils.toUpAddTelList(infos);
        String json = new Gson().toJson(upTelAddBeanList);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        HttpUtils.service
                .addTel(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<CommonBean>(new SubscriberOnNextListener<CommonBean>() {
                    @Override
                    public void onNext(CommonBean bean) {
                        mView.addTelSuccess();
                    }
                }, mView, this));
    }
}
