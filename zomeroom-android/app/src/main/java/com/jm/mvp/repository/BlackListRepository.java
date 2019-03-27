package com.jm.mvp.repository;

import com.jm.bean.BlackListBean;
import com.jm.bean.CommonBean;
import com.jm.helper.HttpUtils;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author pc 张立男
 * @Description BlackListRepository 黑名单
 * @date 2018/5/9 17:19
 * o(＞﹏＜)o
 */

public class BlackListRepository implements IModel {
    @Override
    public void onDestroy() {

    }

    /**
     * 查询黑名单列表 或 单一数据
     *
     * @param json
     * @return
     */
    public Observable<BlackListBean> getBlackList(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service.getBlackList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
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
     * 删除黑名单
     *
     * @param id
     * @return
     */
    public Observable<CommonBean> delBlackTel(String id) {
        return HttpUtils.service.delBlackTel(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
