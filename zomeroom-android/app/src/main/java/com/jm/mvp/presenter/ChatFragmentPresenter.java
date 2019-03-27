package com.jm.mvp.presenter;

import com.google.gson.Gson;
import com.jm.base.Constants;
import com.jm.bean.UserBean;
import com.jm.helper.ProgressSubscriber;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.ChatFragmentContract;
import com.jm.mvp.contract.ContactsContract;
import com.jm.mvp.repository.ChatFragmentRepository;
import com.jm.mvp.repository.ContactsRepository;

import java.util.List;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 13:27
 * 页面功能:消息页面
 */
public class ChatFragmentPresenter  extends BasePresenter<ChatFragmentRepository> {

    private ChatFragmentContract.View mView;
    private Gson mGson;
    private final ChatFragmentRepository mModel;

    public ChatFragmentPresenter(ChatFragmentContract.View view) {
        mView = view;
        mModel = new ChatFragmentRepository();
        mGson = new Gson();
    }
}
