package com.jm.mvp.repository;

import com.jm.bean.CommonBean;
import com.jm.bean.JmModelDataListBean;
import com.jm.bean.RemarkBean;
import com.jm.bean.TelDetailBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DetailsRepository implements IModel {
    @Override
    public void onDestroy() {

    }

    /**
     * 获取详情数据
     */
    public Observable<JmModelDataListBean> getListDataDetail(String id) {
        return HttpUtils.service
                .getDataListDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 更新详情数据
     */
    public Observable<CommonBean> updateDetail(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .updateDetail(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
