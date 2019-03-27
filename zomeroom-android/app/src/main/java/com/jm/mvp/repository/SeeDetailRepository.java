package com.jm.mvp.repository;

import com.jm.bean.CommonBean;
import com.jm.bean.SeeDetailBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class SeeDetailRepository implements IModel {

    @Override
    public void onDestroy() {

    }

    /**
     * 获取电话详情数据
     */
    public Observable<SeeDetailBean> getSeeDetail(String id) {
        return HttpUtils.service
                .getSeeDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 更新进店详情数据
     */
    public Observable<CommonBean> updateSeeDetail(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .updateSeeDetail(body)
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
                .addSeeRemark(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());

    }

    /**
     * 查询备注
     */
    public Observable getRemarkList(String id) {
        return HttpUtils.service
                .remarSeekList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}