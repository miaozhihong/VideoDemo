package com.jm.mvp.presenter;

import com.google.gson.Gson;
import com.jm.base.Constants;
import com.jm.bean.CommonBean;
import com.jm.bean.ShopBean;
import com.jm.bean.TelListBean;
import com.jm.bean.UpTelAddBean;
import com.jm.bean.UpTelSearchBean;
import com.jm.helper.ProgressSubscriber;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.TelContract;
import com.jm.mvp.repository.TelRepository;
import com.jm.utils.ConnectedUtils;
import com.jm.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;

/**
 * @author pc 张立男
 * @Description TelPresenter 电话咨询列表
 * @date 2018/5/11 14:24
 * o(＞﹏＜)o
 */
public class TelPresenter extends BasePresenter<TelRepository> {

    private TelContract.View mView;
    private int mCurrentPage = 1;
    private List<TelListBean.DataBean.ListBean> mLocalList;
    private int mTotalPage = 1;
    private Gson mGson;

    public TelPresenter(TelContract.View view) {
        mView = view;
        mModel = new TelRepository();
        mGson = new Gson();
        mLocalList = new ArrayList<>();
    }

    /**
     * 访问数据前的网络判断，不需要可以删除
     */
    public void netAndData(UpTelSearchBean bean, int type) {
        if (ConnectedUtils.isNetworkAvailable()) {
            if (type == Constants.DOWN_REFRESH) {
                mCurrentPage = 1;
            } else {
                if (mCurrentPage > mTotalPage) {
                    mView.showNotMore();
                    return;
                }
            }
            bean.setPageNo(String.valueOf(mCurrentPage));
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
        mModel.getTelList(json)
                .subscribe(new ProgressSubscriber<TelListBean>(
                        new SubscriberOnNextListener<TelListBean>() {
                            @Override
                            public void onNext(TelListBean bean) {
                                int flag = bean.getFlag();

                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }

                                TelListBean.DataBean data = bean.getData();
                                if (data == null) {
                                    //处理数据为空时逻辑
                                    mView.showEmpty();
                                    return;
                                }
                                mTotalPage = data.getTotalPages();
                                List<TelListBean.DataBean.ListBean> list = data.getList();
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
     * 拨打电话
     *
     * @param tel
     */
    public void call(String tel) {
        mModel.call(tel)
                .subscribe(new ProgressSubscriber<CommonBean>(new SubscriberOnNextListener<CommonBean>() {
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
                }, mView, this, false));
    }

    /**
     * 转进店
     *
     * @param shopId
     */
    public void telToShop(String shopId) {
        mModel.telToShop(shopId)
                .subscribe(new ProgressSubscriber<CommonBean>(new SubscriberOnNextListener<CommonBean>() {
                    @Override
                    public void onNext(CommonBean bean) {
                        int flag = bean.getFlag();

                        if (flag != Constants.NET_SUCCESS) {
                            //处理请求失败的相关逻辑
                            mView.showToShopError(bean.getMessage());
                            return;
                        }else {
                            SPUtils.put("tel","");
                        }

                        mView.refresh();
                    }
                }, mView, this, false));
    }

    /**
     * 添加手机号 时间
     *
     * @param upTelAddBeanList
     */
    public void addTel(List<UpTelAddBean> upTelAddBeanList) {
        String json = mGson.toJson(upTelAddBeanList);
        mModel.addTel(json)
                .subscribe(new ProgressSubscriber<CommonBean>(new SubscriberOnNextListener<CommonBean>() {
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
                }, mView, this, false));
    }

    /**
     * 筛选商店列表
     */
    public void getShopList() {
        mModel.getShopList()
                .subscribe(new ProgressSubscriber<ShopBean>(new SubscriberOnNextListener<ShopBean>() {
                    @Override
                    public void onNext(ShopBean bean) {
                        int flag = bean.getFlag();

                        if (flag != Constants.NET_SUCCESS) {
                            //处理请求失败的相关逻辑
                            mView.showNetError();
                            return;
                        }
                        List<ShopBean.DataBean> data = bean.getData();
                        if (data!=null&&!data.isEmpty()){
                        mView.showShopList(data);
                        }
                    }
                }, mView, this, false));
    }
    public void addBlackTel(String tel, String remark) {
        if (ConnectedUtils.isNetworkAvailable()) {
            Map<String, String> map = new HashMap<>();
            map.put("tel", tel);
            map.put("remark", remark);
            String json = mGson.toJson(map);
            mModel.addBlackList(json).subscribe(new ProgressSubscriber<CommonBean>(
                    new SubscriberOnNextListener<CommonBean>() {
                        @Override
                        public void onNext(CommonBean bean) {
                            int flag = bean.getFlag();
                            if (flag != Constants.NET_SUCCESS) {
                                //处理请求失败的相关逻辑
                                mView.showNetError();
                                return;
                            }
                            mView.showMessage("添加黑名单成功");
                        }
                    }, mView, this
            ));
        } else {
            mView.showNetError();
        }
    }
}