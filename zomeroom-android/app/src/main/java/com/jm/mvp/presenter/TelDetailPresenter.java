package com.jm.mvp.presenter;

import com.google.gson.Gson;
import com.jm.base.Constants;
import com.jm.bean.CommonBean;
import com.jm.bean.RemarkBean;
import com.jm.bean.TelDetailBean;
import com.jm.bean.UpTelDetailBean;
import com.jm.helper.ProgressSubscriber;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.TelDetailContract;
import com.jm.mvp.repository.TelDetailRepository;
import com.jm.utils.ConnectedUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;

/**
 * @author pc 张立男
 * @Description TelDetailPresenter 电话详情
 * @date 2018/5/11 16:13
 * o(＞﹏＜)o
 */
public class TelDetailPresenter extends BasePresenter<TelDetailRepository> {

    private TelDetailContract.View mView;
    private Gson mGson;

    public TelDetailPresenter(TelDetailContract.View view) {
        mView = view;
        mModel = new TelDetailRepository();
        mGson = new Gson();
    }

    /**
     * 访问数据前的网络判断，不需要可以删除
     */
    public void netAndData(String id) {
        if (ConnectedUtils.isNetworkAvailable()) {
            getData(id);
            getRemarkList(id);
        } else {
            mView.showNetError();
        }
    }

    /**
     * 获取数据
     */
    public void getData(String id) {
        mModel.getTelDetail(id)
                .subscribe(new ProgressSubscriber<TelDetailBean>(
                        new SubscriberOnNextListener<TelDetailBean>() {
                            @Override
                            public void onNext(TelDetailBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                TelDetailBean.DataBean data = bean.getData();
                                mView.showData(data);
                            }
                        }, mView, this
                ));
    }

    /**
     * 获取数据
     */
    public void updateData(UpTelDetailBean bean) {
        String json = mGson.toJson(bean);
        mModel.updateTelDetail(json)
                .subscribe(new ProgressSubscriber<CommonBean>(
                        new SubscriberOnNextListener<CommonBean>() {
                            @Override
                            public void onNext(CommonBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                mView.refresh();
                            }
                        }, mView, this
                ));
    }

    /**
     * 添加备注
     */
    public void addRemark(String id, String remark) {
        Map<String, String> map = new HashMap<>();
        map.put("cId", id);
        map.put("content", remark);
        String json = mGson.toJson(map);
        mModel.addRemark(json)
                .subscribe(new ProgressSubscriber<CommonBean>(
                        new SubscriberOnNextListener<CommonBean>() {
                            @Override
                            public void onNext(CommonBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                mView.refreshRemark();
                            }
                        }, mView, this
                ));
    }

    /**
     * 备注列表
     */
    public void getRemarkList(String id) {
        mModel.getRemarkList(id)
                .subscribe(new ProgressSubscriber<RemarkBean>(
                        new SubscriberOnNextListener<RemarkBean>() {
                            @Override
                            public void onNext(RemarkBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                List<RemarkBean.DataBean> data = bean.getData();
                                mView.showRemarkList(data);
                            }
                        }, mView, this
                ));
    }

}