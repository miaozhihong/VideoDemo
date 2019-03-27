package com.jm.mvp.repository;

import com.jm.bean.CommonBean;
import com.jm.bean.JmModelDataBean;
import com.jm.bean.ShopBean;
import com.jm.bean.TelListBean;
import com.jm.bean.UserBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 作者 Created by $miaozhihong on 2019/1/10 10:31
 * 页面功能:
 */
public class DetailRepository implements IModel {
    @Override
    public void onDestroy() {
    }
    /**
     * 内务管理数据展示
     */
    public Observable<JmModelDataBean> getJmList(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .getJmModelList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 入库
     */
    public Observable<JmModelDataBean> getStorageJmList(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .getStorageJmModelList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 入库
     */
    public Observable getWare(String id) {
        return HttpUtils.service
                .getWarehou(id,1+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 筛选商店列表
     */
    public Observable<ShopBean> getShopList() {
        return HttpUtils.service
                .shopList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 查询分店
     */
    public Observable<ShopBean> getDetialShopList() {
        return HttpUtils.service
                .shopDetialShopList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 进行调拨
     */
    public Observable getShopDoDetail(String id,String shoId) {
        return HttpUtils.service
                .shopDoDetail(id,shoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 查询调拨
     */
    public Observable getShowReqsutDetail() {
        return HttpUtils.service
                .shopRequstDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 同意调拨
     */
    public Observable getShowAreggReqsutDetail(String id) {
        return HttpUtils.service
                .shopRequstAreggDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 不同意调拨
     */
    public Observable getShowNotAreggReqsutDetail(String id) {
        return HttpUtils.service
                .shopRequstNotAreggDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
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
