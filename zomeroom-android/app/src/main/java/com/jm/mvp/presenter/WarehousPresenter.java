package com.jm.mvp.presenter;

import com.google.gson.Gson;
import com.jm.base.Constants;
import com.jm.bean.JmModelDataBean;
import com.jm.bean.ModelDataDTOBean;
import com.jm.bean.ShopBean;
import com.jm.bean.ShowDetailBean;
import com.jm.bean.ShowRequestDetailBean;
import com.jm.bean.UserBean;
import com.jm.helper.ProgressSubscriber;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.DetailContract;
import com.jm.mvp.contract.WarehousContract;
import com.jm.mvp.repository.DetailRepository;
import com.jm.utils.ConnectedUtils;
import com.jm.utils.DateChangesUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;

/**
 * 作者 Created by $miaozhihong on 2019/1/15 11:06
 * 页面功能:入库p
 */
public class WarehousPresenter extends BasePresenter<DetailRepository> {

    private WarehousContract.View mView;
    private int mCurrentPage = 1;
    private List<JmModelDataBean.DataBean.ListBean> mLocalList;
    private int mTotalPage = 1;
    private Gson mGson;
    private final DetailRepository mModel;

    public WarehousPresenter(WarehousContract.View view) {
        mView = view;
        mModel = new DetailRepository();
        mGson = new Gson();
        mLocalList = new ArrayList<>();
    }

    /**
     * d第一次进来加载数据访问数据前的网络判断
     */
    public void netLoadData(ModelDataDTOBean bean, int type) {
        bean.setPageNo(1);
        bean.setPageSize(11);
        String json = mGson.toJson(bean);
        getData(json, type);
    }

    /**
     * 访问数据前的网络判断，不需要可以删除
     */
    public void netAndData(ModelDataDTOBean bean, int type) {
        if (ConnectedUtils.isNetworkAvailable()) {
            if (type == Constants.DOWN_REFRESH) {
                mCurrentPage = 1;
            } else {
                if (mCurrentPage > mTotalPage) {
                    mView.showNotMore();
                    return;
                }
            }
            bean.setPageNo(mCurrentPage);
            String json = mGson.toJson(bean);
            getData(json, type);
        } else {
            mView.showNetError();
        }
    }

    /**
     * 获取数据
     */
    private void getData(String json, final int type) {
        mModel.getStorageJmList(json)
                .subscribe(new ProgressSubscriber<JmModelDataBean>(
                        new SubscriberOnNextListener<JmModelDataBean>() {
                            @Override
                            public void onNext(JmModelDataBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                JmModelDataBean.DataBean data = bean.getData();
                                if (data == null) {
                                    //处理数据为空时逻辑
                                    mView.showEmpty();
                                    return;
                                }
                                mTotalPage = data.getTotalPages();
                                List<JmModelDataBean.DataBean.ListBean> list = data.getList();
                                if (list == null || list.isEmpty()) {
                                    //处理数据为空时逻辑
                                    mView.showEmpty();
                                    return;
                                }
                                //下拉刷新则清空数据集合
                                if (type == Constants.DOWN_REFRESH) {
                                    mLocalList.clear();
                                }
                                //添加集合 回调方法 刷新页面
                                mLocalList.addAll(list);
                                mView.showData(mLocalList);
                                mCurrentPage++;
                            }
                        }, mView, this
                ));
    }
    /**
     * 获取店名数据
     */
    public void getShopData(ShopBean bean) {
        mModel.getDetialShopList()
                .subscribe(new ProgressSubscriber<ShopBean>(
                        new SubscriberOnNextListener<ShopBean>() {
                            @Override
                            public void onNext(ShopBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                List<ShopBean.DataBean> data = bean.getData();
                                if (data == null) {
                                    //处理数据为空时逻辑
                                    mView.showEmpty();
                                    return;
                                }
                                mView.showShopData(data);
                            }
                        }, mView, this
                ));
    }
}

