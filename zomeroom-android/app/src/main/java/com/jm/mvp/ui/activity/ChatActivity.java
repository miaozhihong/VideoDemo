package com.jm.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.fragment.ChatFragment;
import com.jm.mvp.ui.fragment.ContactsFragment;
import com.jm.mvp.ui.fragment.MineChatFragment;
import com.jm.utils.AppManager;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;

import java.net.URI;

import butterknife.BindView;
import cn.com.yktour.network.base.IPresenter;

/**
 * 即时通讯界面
 */
public class ChatActivity extends BaseActivity {
    private static final int READ_PHONE_STATE = 1;
    @BindView(R.id.rg_main)
    RadioGroup mRgMain;
    @BindView(R.id.rab_chat)
    RadioButton mRrabChat;
    @BindView(R.id.rab_contacts)
    RadioButton mRabContacts;
    @BindView(R.id.rab_mine_chat)
    RadioButton mRabMineChat;
    private MineChatFragment mMineWhatFragment;
    private ChatFragment mChatFragment;
    private ContactsFragment mContactsFragment;

    @Override
    public int initView(Bundle savedInstanceState) {
        FragmentManager trx = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mChatFragment = (ChatFragment) trx.findFragmentByTag("rab_chat");
            mContactsFragment = (ContactsFragment) trx.findFragmentByTag("rab_contacts");
            mMineWhatFragment = (MineChatFragment) trx.findFragmentByTag("rab_mine_what");
        }
        return R.layout.activity_chat;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setOnListener();
        setTelListener();
        if (savedInstanceState != null) {
            return;
        }
        mRrabChat.setChecked(true);
        mGson = new Gson();
        try {
            mClient = new BaseActivity.DemoClient(new URI(Constants.WEB_SOCKET));
            ToastUtils.ToastCenter("开始链接");
            CommonUtils.checkNotNull(mClient, "连接时，client为空").connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 退出登录或者 修改密码重新链接
        try {
            mClient = new BaseActivity.DemoClient(new URI(Constants.WEB_SOCKET));
            CommonUtils.checkNotNull(mClient, "连接时，client为空").connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置通话记录的监听
     */
    private void setTelListener() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置点击事件
     */
    private void setOnListener() {
        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                hideAllFragment(trx);
                switch (checkedId) {
                    case R.id.rab_chat:
                        if (mChatFragment == null) {
                            mChatFragment = ChatFragment.newInstance();
                        }
                        if (!mChatFragment.isAdded()) {
                            trx.add(R.id.fl_main, mChatFragment, "rab_chat");
                        }
                        trx.show(mChatFragment);
                        break;
                    case R.id.rab_contacts:
                        if (mContactsFragment == null) {
                            mContactsFragment = ContactsFragment.newInstance();
                        }
                        if (!mContactsFragment.isAdded()) {
                            trx.add(R.id.fl_main, mContactsFragment, "rab_contacts");
                        }
                        trx.show(mContactsFragment);
                        break;
                    case R.id.rab_mine_chat:
                        if (mMineWhatFragment == null) {
                            mMineWhatFragment = MineChatFragment.newInstance();
                        }
                        if (!mMineWhatFragment.isAdded()) {
                            trx.add(R.id.fl_main, mMineWhatFragment, "rab_mine_what");
                        }
                        trx.show(mMineWhatFragment);
                        break;
                    default:
                        break;
                }
                trx.commitAllowingStateLoss();
            }
        });
    }

    /**
     * 隐藏所有界面
     * @param fragmentTransaction 管理
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fragmentTransaction != null) {
            if (mChatFragment!= null) {
                fragmentTransaction.hide(mChatFragment);
            }
            if (mContactsFragment!= null) {
                fragmentTransaction.hide(mContactsFragment);
            }
            if (mMineWhatFragment != null) {
                fragmentTransaction.hide(mMineWhatFragment);
            }
        }
    }

    /**
     * 返回键 再按一次退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            int isOperate = (int) SPUtils.get("isOperate", 0);
            if (isOperate==1){
                //销售
                startActivity(new Intent(ChatActivity.this,MainActivity.class));
            }else if (isOperate==2){
                //内务
                startActivity(new Intent(ChatActivity.this,InteriorActivity.class));
            }else if (isOperate==3){
                //开发
                startActivity(new Intent(ChatActivity.this,OperateActivity.class));
            }else {
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
