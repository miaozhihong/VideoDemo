package com.jm.mvp.presenter;

import com.google.gson.Gson;
import com.jm.base.Constants;
import com.jm.bean.BlackListBean;
import com.jm.bean.CommonBean;
import com.jm.helper.ProgressSubscriber;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.BlackListContract;
import com.jm.mvp.repository.BlackListRepository;
import com.jm.utils.ConnectedUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;

/**
 * @author pc 张立男
 * @Description BlackListPresenter 黑名单
 * @date 2018/5/9 17:38
 * o(＞﹏＜)o
 */

public class BlackListPresenter extends BasePresenter<BlackListRepository> {
    private BlackListContract.View mView;
    private Gson mGson;
    private int mCurrentPage = 1;
    private List<BlackListBean.DataBean.ListBean> mLocalList;
    private int mTotalPage = 1;

    public BlackListPresenter(BlackListContract.View view) {
        mView = view;
        mModel = new BlackListRepository();
        mGson = new Gson();
        mLocalList = new ArrayList<>();
    }

    /**
     * 检测网络并联网
     */
    public void netAndData(String tel, int type) {
        if (ConnectedUtils.isNetworkAvailable()) {
            if (type == Constants.DOWN_REFRESH) {
                mCurrentPage = 1;
            } else {
                if (mCurrentPage > mTotalPage) {
                    mView.showNotMore();
                    return;
                }
            }
            Map<String, String> map = new HashMap<>();
            map.put("tel", tel);
            map.put("pageSize", "10");
            map.put("pageNo", "1");
            String json = mGson.toJson(map);
            getBlackList(json, type);
        } else {
            mView.showNetError();
        }
    }

    private void getBlackList(String json, final int type) {
        mModel.getBlackList(json).subscribe(new ProgressSubscriber<BlackListBean>(
                new SubscriberOnNextListener<BlackListBean>() {
                    @Override
                    public void onNext(BlackListBean bean) {
                        int flag = bean.getFlag();

                        if (flag != Constants.NET_SUCCESS) {
                            //处理请求失败的相关逻辑
                            mView.showNetError();
                            return;
                        }

                        BlackListBean.DataBean data = bean.getData();
                        if (data == null) {
                            //处理数据为空时逻辑
                            mView.showEmpty();
                            return;
                        }
                        mTotalPage = data.getTotalPages();
                        List<BlackListBean.DataBean.ListBean> list = data.getList();
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
                        mView.display(mLocalList);
                        mCurrentPage++;
                    }
                }, mView, this
        ));
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
                            mView.refresh();
                        }
                    }, mView, this
            ));
        } else {
            mView.showNetError();
        }
    }

    public void delBlackTel(String id) {
        if (ConnectedUtils.isNetworkAvailable()) {
            mModel.delBlackTel(id).subscribe(new ProgressSubscriber<CommonBean>(
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
        } else {
            mView.showNetError();
        }
    }

    public String getId(int position) {
        return mLocalList.get(position).getId();
    }
}
