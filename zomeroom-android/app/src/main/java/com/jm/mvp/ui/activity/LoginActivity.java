package com.jm.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.contract.LoginContract;
import com.jm.mvp.presenter.LoginPresenter;
import com.jm.utils.AppManager;
import com.jm.utils.SPUtils;
import com.jm.utils.SwitchingDialog;
import com.jm.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author pc 张立男
 * @Description LoginActivity 登录页面
 * @date 2018/2/19 9:12
 * o(＞﹏＜)o
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.et_login_account)
    EditText mEtLoginAccount;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    private LoginPresenter mPresenter;
    private int mWhich;
    String account;

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        account = mEtLoginAccount.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        if (TextUtils.isEmpty(account)) {
            ToastUtils.ToastCenter("帐号不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.ToastCenter("密码不能为空");
            return;
        }
        mPresenter.login(account, pwd);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        mPresenter = new LoginPresenter(this);
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mWhich = getIntent().getIntExtra(Constants.WHICH, -1);
    }

    @Override
    public LoginPresenter obtainPresenter() {
        return mPresenter;
    }

    @Override
    public void loginSuccess() {
        SPUtils.put("userName", account);
        sendToken();
        mClose = false;
        mPresenter.addTel();
    }

    private void sendToken() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String userName = (String) SPUtils.get("userName", "");
        final String mToken = (String) SPUtils.get("token", "");
        if (null != userName) {
            final Request request = new Request.Builder().url(Constants.upFilePath + Constants.sendToken + SPUtils.get("userName", "") + "/" + mToken).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.e("onFailure", e.toString());
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(LoginActivity.this, "登陆成功：" + mToken, Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "登陆成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (mWhich == Constants.PWD) {
            // 修改密码 重新登录 后退直接关闭 app
            AppManager manager = AppManager.getAppManager();
            manager.AppExit();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } else {
            finish();
        }
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void addTelSuccess() {
        ToastUtils.ToastCenter("数据同步成功");
        operateDialog();

    }

    @Override
    public boolean httpError(int code, String message, int type) {
        ToastUtils.ToastCenter(message);
        return true;
    }

    //是否切换到运营
    private void operateDialog() {
        int loginType = (int) SPUtils.get("loginType", 9);
        if (loginType == 0) {
            //销售
            SPUtils.put("isOperate", 1);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else if (loginType == 1) {
            //内务管理
            SPUtils.put("isOperate", 2);
            SPUtils.put("lgintypes", 1);
            startActivity(new Intent(LoginActivity.this, InteriorActivity.class));
            finish();
        } else if (loginType == 2) {
            //市场开发
            SPUtils.put("isOperate", 3);
            SPUtils.put("lgintypes", 1);
            startActivity(new Intent(LoginActivity.this, OperateActivity.class));
            finish();
        } else if (loginType == 4) {
            //销售&&内务
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this).setTitle("系统提示")
                    .setMessage("请选择登录系统")
                    .setNegativeButton("销售", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SPUtils.put("isOperate", 1);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }).setPositiveButton("内务", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SPUtils.put("isOperate", 2);
                            SPUtils.put("lgintypes", 1);
                            startActivity(new Intent(LoginActivity.this, InteriorActivity.class));
                            finish();
                        }
                    });
            alertDialog.setIcon(R.mipmap.logoyimei).create().show();
        } else {
            SwitchingDialog switchingDialog = new SwitchingDialog(this);
            switchingDialog.show();
            switchingDialog.setCancelable(false);
            switchingDialog.setCanceledOnTouchOutside(false);
            switchingDialog.setItemClickListenerInterface(new SwitchingDialog.ItemClickListenerInterface() {
                @Override
                public void doOperateClickItem() {
                    SPUtils.put("isOperate", 1);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void doInteriorClickItem() {
                    SPUtils.put("isOperate", 2);
                    SPUtils.put("lgintypes", 1);
                    startActivity(new Intent(LoginActivity.this, InteriorActivity.class));
                    finish();
                }

                @Override
                public void doMarketClickItem() {
                    SPUtils.put("isOperate", 3);
                    SPUtils.put("lgintypes", 1);
                    startActivity(new Intent(LoginActivity.this, OperateActivity.class));
                    finish();
                }
            });
        }
    }
}
