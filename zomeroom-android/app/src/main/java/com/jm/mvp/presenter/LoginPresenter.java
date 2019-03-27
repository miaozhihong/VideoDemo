package com.jm.mvp.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jm.bean.CallInfo;
import com.jm.bean.CommonBean;
import com.jm.bean.LoginBean;
import com.jm.bean.UpTelAddBean;
import com.jm.helper.HttpUtils;
import com.jm.helper.ProgressSubscriber;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.LoginContract;
import com.jm.mvp.repository.LoginRepository;
import com.jm.mvp.service.CallInfoService;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;

import java.util.List;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * @author pc 张立男
 * @Description LoginPresenter 登录
 * @date 2018/5/29 10:35
 * o(＞﹏＜)o
 */

public class LoginPresenter extends BasePresenter<LoginRepository> {
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mModel = new LoginRepository();
    }

    /**
     * 登录
     */
    public void login(String username, String password) {
        mModel.login(username, password)
                .subscribe(new ProgressSubscriber<Response<LoginBean>>(
                        new SubscriberOnNextListener<Response<LoginBean>>() {
                            @Override
                            public void onNext(Response<LoginBean> response) {
                                String XI = response.headers().get("X-I");
                                String XS = response.headers().get("X-S");
                                SPUtils.put("X-S", XS);
                                SPUtils.put("X-I", XI);
                                SPUtils.put("isLogin", true);
                                SPUtils.put("loginName", response.body().getData().getLoginName());
                                SPUtils.put("name", response.body().getData().getName());
                                SPUtils.put("loginType", response.body().getData().getType());
                                SPUtils.put("tel", response.body().getData().getTel().replace(" ", ""));
                                mView.loginSuccess();
                            }
                        }, mView, this
                ));
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
