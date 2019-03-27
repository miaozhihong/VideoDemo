package com.jm.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jm.R;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.yktour.network.base.IPresenter;

/**
 * 环信登录页面
 */
public class ChatLoginActivity extends BaseActivity {
    @BindView(R.id.iv_mine)
    ImageView mIvMine;
    @BindView(R.id.tv_mine_account)
    TextView mTvMineAccount;
    @BindView(R.id.ed_userName)
    EditText mEdUserName;
    @BindView(R.id.ed_psw)
    EditText mEdPsw;
    @BindView(R.id.tv_mine_login)
    TextView mTvMineLogin;
    private Dialog loadingDialog;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_chat_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mEdUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<1){
                    mTvMineAccount.setText("请登录");
                }else {
                    mTvMineAccount.setText(s.toString());
                }

            }
        });
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @OnClick({R.id.iv_mine, R.id.tv_mine_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine:
                break;
            case R.id.tv_mine_login:
                String loginName = mEdUserName.getText().toString().trim();
                String loginPsw = mEdPsw.getText().toString().trim();
                if (TextUtils.isEmpty(loginName) ||loginName.equals("")){
                    ToastUtils.ToastCenter("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(loginPsw) ||loginPsw.equals("")){
                    ToastUtils.ToastCenter("请输入密码");
                    return;
                }
                initChatLogin(loginName,loginPsw);
                break;
            default:
                break;
        }
    }

    /**
     *
     * 环信账号登录
     * @param loginName
     * @param loginPsw
     */
    private void initChatLogin(String loginName,String loginPsw) {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(this, "正在登录");
        EMClient.getInstance().login(loginName,loginPsw, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatLoginActivity.this, "登录聊天服务器成功", Toast.LENGTH_SHORT).show();
                        SPUtils.put("isLoginSuccess",true);
                        LoadingDialogUtils.closeDialog(loadingDialog);
                        Intent intent = new Intent(ChatLoginActivity.this, ChatActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialogUtils.closeDialog(loadingDialog);
                        if (code==204){
                            ToastUtils.ToastCenter("用户不存在");
                        }else if (code==202){
                            ToastUtils.ToastCenter("用户名或密码错误");
                        }else if (code==408){
                            ToastUtils.ToastCenter("请求超时");
                        }else {
                            Toast.makeText(ChatLoginActivity.this, code+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
