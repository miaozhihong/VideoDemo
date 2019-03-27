package com.jm.mvp.repository;

import com.google.gson.Gson;
import com.jm.bean.SubwayBean;
import com.jm.helper.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import cn.com.yktour.network.base.IModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author pc 张立男
 * @Description SubwayRepository 地铁搜索
 * @date 2018/5/12 17:51
 * o(＞﹏＜)o
 */
public class SubwayRepository implements IModel {

    @Override
    public void onDestroy() {

    }

    /**
     * 访问网络获取数据
     */
    public Observable<SubwayBean> searchSubway(String name) {
//        Map<String, String> map = new HashMap<>();
//        map.put("name", name);
//        String json = new Gson().toJson(map);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return HttpUtils.service
                .searchSubway(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}