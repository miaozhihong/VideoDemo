package com.jm.mvp.presenter;

import com.jm.base.Constants;
import com.jm.bean.SubwayBean;
import com.jm.helper.ProgressSubscriber;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.SubwayContract;
import com.jm.mvp.repository.SubwayRepository;
import com.jm.utils.ConnectedUtils;

import java.util.List;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;

/**
 * @author pc 张立男
 * @Description SubwayPresenter 地铁搜索
 * @date 2018/5/12 17:51
 * o(＞﹏＜)o
 */
public class SubwayPresenter extends BasePresenter<SubwayRepository> {

    private SubwayContract.View mView;
    private List<SubwayBean.DataBean> mList;

    public SubwayPresenter(SubwayContract.View view) {
        mView = view;
        mModel = new SubwayRepository();
    }

    /**
     * 访问数据前的网络判断，不需要可以删除
     */
    public void netAndData(String name) {
        if (ConnectedUtils.isNetworkAvailable()) {
            getData(name);
        } else {
            mView.showNetError();
        }
    }

    /**
     * 获取数据
     */
    public void getData(String name) {

        mModel.searchSubway(name)
                .subscribe(new ProgressSubscriber<SubwayBean>(
                        new SubscriberOnNextListener<SubwayBean>() {
                            @Override
                            public void onNext(SubwayBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                mList = bean.getData();
                                if (mList == null || mList.isEmpty()) {
                                    mView.showEmpty();
                                }
                                mView.showData(mList);
                            }
                        }, mView, this
                ));
    }

    public String getSubway(int position) {
        return mList.get(position).getStationName();
    }

    public int getSubwayId(int position) {
        return mList.get(position).getId();
    }
}