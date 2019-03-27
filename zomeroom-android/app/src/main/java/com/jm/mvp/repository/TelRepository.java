package com.jm.mvp.repository;

import com.jm.bean.CommonBean;
import com.jm.bean.ShopBean;
import com.jm.bean.TelListBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author pc 张立男
 * @Description TelRepository 电话咨询
 * @date 2018/5/31 9:53
 * o(＞﹏＜)o
 */
public class TelRepository implements IModel {

    @Override
    public void onDestroy() {

    }
    /**
     * 添加黑名单
     *
     * @param json
     * @return
     */
    public Observable<CommonBean> addBlackList(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service.addBlack(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
    /**
     * 电话咨询列表
     */
    public Observable<TelListBean> getTelList(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .getTelList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 拨打电话
     */
    public Observable<CommonBean> call(String tel) {
        return HttpUtils.service
                .callTel(tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 转进店
     */
    public Observable<CommonBean> telToShop(String shopId) {
        return HttpUtils.service
                .telToShop(shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 添加手机号
     */
    public Observable<CommonBean> addTel(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .addSingleTel(body)
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
}