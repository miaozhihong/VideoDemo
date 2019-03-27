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
import com.jm.mvp.contract.ContactsContract;
import com.jm.mvp.contract.DetailContract;
import com.jm.mvp.repository.ContactsRepository;
import com.jm.mvp.repository.DetailRepository;
import com.jm.utils.ConnectedUtils;
import com.jm.utils.DateChangesUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 11:19
 * 页面功能:联系人p层
 */
public class ContactsPresenter extends BasePresenter<ContactsRepository> {

    private ContactsContract.View mView;
    private Gson mGson;
    private final ContactsRepository mModel;

    public ContactsPresenter(ContactsContract.View view) {
        mView = view;
        mModel = new ContactsRepository();
        mGson = new Gson();
    }
    /**
     * 获取用户数据
     */
    public void getUserData(UserBean bean) {
        mModel.getDetialUserList()
                .subscribe(new ProgressSubscriber<UserBean>(
                        new SubscriberOnNextListener<UserBean>() {
                            @Override
                            public void onNext(UserBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                List<UserBean.DataBean> data = bean.getData();
                                if (data == null) {
                                    //处理数据为空时逻辑
                                    mView.showEmpty();
                                    return;
                                }
                                mView.showUserData(data);
                            }
                        }, mView, this
                ));
    }
}

