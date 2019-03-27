package com.jm.mvp.repository;

import com.jm.bean.CommonBean;
import com.jm.bean.RemarkBean;
import com.jm.bean.TelDetailBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class TelDetailRepository implements IModel {

    @Override
    public void onDestroy() {

    }

    /**
     * 获取电话详情数据
     */
    public Observable<TelDetailBean> getTelDetail(String id) {
        return HttpUtils.service
                .getTelDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 更新电话详情数据
     */
    public Observable<CommonBean> updateTelDetail(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .updateTelDetail(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 添加备注
     */
    public Observable<CommonBean> addRemark(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .addRemark(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 查询备注
     */
    public Observable<RemarkBean> getRemarkList(String id) {
        return HttpUtils.service
                .remarkList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

}