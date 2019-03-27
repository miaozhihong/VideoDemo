package com.jm.mvp.repository;

import com.jm.bean.CommonBean;
import com.jm.bean.LoginBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @author pc 张立男
 * @Description LoginRepository 登录
 * @date 2018/5/29 10:33
 * o(＞﹏＜)o
 */

public class LoginRepository implements IModel {
    @Override
    public void onDestroy() {

    }

    /**
     * 登录
     */
    public Observable<Response<LoginBean>> login(String username, String password) {
        return HttpUtils.service
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

}
