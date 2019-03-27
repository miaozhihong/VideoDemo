package com.jm.mvp.repository;

import com.jm.bean.JmModelDataBean;
import com.jm.bean.ShopBean;
import com.jm.bean.UserBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 11:20
 * 页面功能:联系人
 */
public class ContactsRepository implements IModel {
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

