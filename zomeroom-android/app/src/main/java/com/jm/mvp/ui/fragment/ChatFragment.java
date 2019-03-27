package com.jm.mvp.ui.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.ModelDataDTOBean;
import com.jm.mvp.base.BaseFragment;
import com.jm.mvp.base.ChatNoReadMsg;
import com.jm.mvp.contract.ChatFragmentContract;
import com.jm.mvp.presenter.ChatFragmentPresenter;
import com.jm.mvp.ui.activity.ChatDetailsActivity;
import com.jm.mvp.ui.adapter.ChatFragmentAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.DateUtils;
import com.jm.utils.ToastUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * 聊天fragment
 */
public class ChatFragment extends BaseFragment<ChatFragmentPresenter> implements ChatFragmentContract.View {
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_talk)
    PullLoadMoreRecyclerView mSsfTel;
    private ChatFragmentPresenter mPresenter;
    private ChatFragmentAdapter mChatFragmentAdapter;
    private Dialog loadingDialog;
    ArrayList<String> mList = new ArrayList<>();
    private EMConversation conversation;
    ArrayList<ChatNoReadMsg> mMsgs = new ArrayList<>();

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new ChatFragmentPresenter(this);

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getContacts();
        mMsgs.clear();
        mIvTitleBack.setVisibility(View.GONE);
        mTvTitle.setText("消息");
        mSsfTel.setLinearLayout();
        mSsfTel.setFooterViewText("正在加载");

        mChatFragmentAdapter = new ChatFragmentAdapter(mMsgs);
        mSsfTel.setAdapter(mChatFragmentAdapter);
        mChatFragmentAdapter.setListener(new ChatFragmentAdapter.ClickListener() {
            @Override
            public void messageTop() {
                ToastUtils.ToastCenter("正在开发中...");
            }

            @Override
            public void deletTalk() {
                ToastUtils.ToastCenter("正在开发中...");
            }

            @Override
            public void clickItem(String chatName) {
                //指定会话消息未读数清零
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatName);
                conversation.markAllMessagesAsRead();
                Intent intent = new Intent(getContext(), ChatDetailsActivity.class);
                intent.putExtra(Constants.CHAT_ID, chatName);
                startActivity(intent);
            }
        });
        mSsfTel.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mMsgs.clear();
                mChatFragmentAdapter.notifyDataSetChanged();
                getContacts();
                mSsfTel.setPullLoadMoreCompleted();
            }

            @Override
            public void onLoadMore() {
                mSsfTel.setPullLoadMoreCompleted();
            }
        });
    }

    /**
     * 获取未读消息数量
     */
    private void getEMdata() {
        Map<String, Integer> mMap = new HashMap<>();
        for (int i = 0; i < mList.size(); i++) {
            conversation = EMClient.getInstance().chatManager().getConversation(mList.get(i));
            if (null != conversation) {
                mMap.put(mList.get(i), conversation.getUnreadMsgCount());
            }
        }

        Iterator iter = mMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            int val = mMap.get(key);
            mMsgs.add(new ChatNoReadMsg(key, "", "", "", val + ""));
            mChatFragmentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public ChatFragmentPresenter obtainPresenter() {
        return mPresenter;
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showNotMore() {

    }


    @Override
    public void refresh() {

    }

    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }

    /**
     * 获取好友
     */
    private void getContacts() {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "获取中...");
        mList.clear();
        new Thread(new Runnable() {
            private List<String> usernames;

            @Override
            public void run() {
                EMContactManager emContactManager = EMClient.getInstance().contactManager();
                try {
                    usernames = emContactManager.getAllContactsFromServer();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mList.addAll(usernames);
                            LoadingDialogUtils.closeDialog(loadingDialog);
                            getEMdata();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取聊天记录
     */
    private void getHistoryData(String chatName) {
        mMsgs.clear();
        List<EMMessage> messages = conversation.getAllMessages();
        if (messages.size() > 0) {
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).getFrom().equals(chatName)) {
                    String body = messages.get(i).getBody().toString();
                    String substring = body.substring(5, body.length() - 1);
                    mMsgs.add(new ChatNoReadMsg("", substring, DateUtils.timeStampToDate(messages.get(i).getMsgTime()), "", ""));
                } else {
                    String body = messages.get(i).getBody().toString();
                    String substring = body.substring(5, body.length() - 1);
                    mMsgs.add(new ChatNoReadMsg("", substring, DateUtils.timeStampToDate(messages.get(i).getMsgTime()), "", ""));
                }
                mChatFragmentAdapter.notifyDataSetChanged();
            }

        } else {
            ToastUtils.ToastCenter("暂无聊天记录");
        }
    }

    /**
     * 加载数据
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden == false) {
            mMsgs.clear();
            mChatFragmentAdapter.notifyDataSetChanged();
            getContacts();
        }
        super.onHiddenChanged(hidden);
    }

}
