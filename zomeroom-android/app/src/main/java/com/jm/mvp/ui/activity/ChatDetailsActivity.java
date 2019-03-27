package com.jm.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.base.Msg;
import com.jm.mvp.ui.adapter.ChatAdapter;
import com.jm.mvp.ui.fragment.ChatFragment;
import com.jm.mvp.ui.widget.EaseConstant;
import com.jm.utils.DateUtils;
import com.jm.utils.EaseAlertDialog;
import com.jm.utils.EaseCommonUtils;
import com.jm.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.commonsdk.debug.E;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.yktour.network.base.IPresenter;

/**
 * 聊天页面
 */
public class ChatDetailsActivity extends BaseActivity implements EMMessageListener {
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_second)
    TextView mTvTitleSecond;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_chatList)
    RecyclerView mRvChatList;
    @BindView(R.id.et_content)
    EditText mEdContent;
    @BindView(R.id.bt_send)
    Button mBtSend;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private String chatName;
    ArrayList<Msg> mMsgs = new ArrayList<>();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ChatAdapter mAdapter;
    private int pagesize=20;
    private EMConversation conversation;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_chat_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mMsgs.clear();
        mTvTitleSecond.setVisibility(View.VISIBLE);
        mTvTitleSecond.setText("删除");
        Intent intent = getIntent();
        chatName = intent.getStringExtra(Constants.CHAT_ID);
        //加载数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvChatList.setLayoutManager(linearLayoutManager);
        mAdapter = new ChatAdapter(mMsgs);
        mRvChatList.setAdapter(mAdapter);

        if (null != chatName && !chatName.equals("")) {
            mTvTitle.setText(chatName);
            getHistoryTalk();
        }
        //初试加载历史记录呈现最新消息
        mRvChatList.scrollToPosition(mAdapter.getItemCount() - 1);
        //设置下滑隐藏软键盘
        mRvChatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < -10) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdContent.getWindowToken(), 0);
                }
            }
        });

        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //pagesize++;
                getHistoryPickTalk();
                refreshlayout.finishRefresh();
            }
        });
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @OnClick({R.id.iv_title_back, R.id.bt_send, R.id.tv_title_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_send:
                sendMessage();
                break;
            case R.id.tv_title_second:
                emptyHistory();
                break;
            default:
                break;
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        String mMessage = mEdContent.getText().toString().trim();
        mEdContent.setText("");
        if (TextUtils.isEmpty(mMessage) || mMessage.equals("")) {
            ToastUtils.ToastCenter("请输入发送内容");
            return;
        }
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(mMessage, chatName);
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        mMsgs.add(new Msg(2, mMessage, df.format(new Date()), ""));
        mAdapter.notifyDataSetChanged();
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.ToastCenter(s);
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage emMessage : list) {
            mMsgs.add(new Msg(1, ((EMTextMessageBody) emMessage.getBody()).getMessage(), df.format(new Date()), ""));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);

    }

    private void getHistoryTalk() {
        /**
         * 获取聊天记录
         */
        conversation = EMClient.getInstance().chatManager().getConversation(chatName);
//获取此会话的所有消息
        if (null== conversation){
            return;
        }
        getHistoryData();
    }
    /**
     * clear the conversation history
     */
    protected void emptyHistory() {
        new EaseAlertDialog(this, null, "是否清空所有聊天记录", null, new EaseAlertDialog.AlertDialogUser() {
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    //删除和某个user会话，如果需要保留聊天记录，传false
                    EMClient.getInstance().chatManager().deleteConversation(chatName, true);
                    mMsgs.clear();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, true).show();
    }

    /**
     * 下拉获取分页数据
     */
    private void getHistoryPickTalk() {
        try {
            if (null==conversation){
                return;
            }
            final List<EMMessage> msgs = conversation.getAllMessages();
            EMClient.getInstance().chatManager().fetchHistoryMessages(chatName, EaseCommonUtils.getConversationType(EaseConstant.CHATTYPE_SINGLE), pagesize, "");
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).getMsgId();
                }
                conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
            }
            getHistoryData();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取聊天记录
     */
    private void getHistoryData() {
        mMsgs.clear();
        List<EMMessage> messages = conversation.getAllMessages();
        if (messages.size() > 0) {
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).getFrom().equals(chatName)) {
                    String body = messages.get(i).getBody().toString();
                    String substring = body.substring(5, body.length() - 1);
                    mMsgs.add(new Msg(1, substring,   DateUtils.timeStampToDate(messages.get(i).getMsgTime()), ""));
                } else {
                    String body = messages.get(i).getBody().toString();
                    String substring = body.substring(5, body.length() - 1);
                    mMsgs.add(new Msg(2, substring,  DateUtils.timeStampToDate(messages.get(i).getMsgTime()), ""));
                }
                mAdapter.notifyDataSetChanged();
            }

        } else {
            ToastUtils.ToastCenter("暂无聊天记录");
        }
    }
}
