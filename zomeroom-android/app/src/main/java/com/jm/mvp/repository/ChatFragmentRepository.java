package com.jm.mvp.repository;

import com.jm.bean.UserBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 13:28
 * 页面功能:消息页面
 */
public class ChatFragmentRepository implements IModel {
    @Override
    public void onDestroy() {
    }

    /**
     * 查询姓名
     */
    public Observable<UserBean> getDetialUserList() {
        return HttpUtils.service
                .shopDetialUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
