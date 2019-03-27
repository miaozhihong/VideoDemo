package com.jm.mvp.repository;

import com.jm.bean.CommonBean;
import com.jm.bean.SeeListBean;
import com.jm.bean.ShopBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author pc 张立男
 * @Description SeeRepository 进店看房列表
 * @date 2018/5/14 22:37
 * o(＞﹏＜)o
 */
public class SeeRepository implements IModel {

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
     * 进店看房列表
     */
    public Observable<SeeListBean> getSeeList(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .getSeeList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 拨打电话
     */
    public Observable<CommonBean> call(String tel) {
        return HttpUtils.service
                .enterCallTel(tel)
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
     * 取消转进店
     *
     * @param id
     * @return
     */
    public Observable<CommonBean> cancelToShop(String id) {
        return HttpUtils.service
                .cancelToShop(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}