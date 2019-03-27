package com.jm.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.CommonBean;
import com.jm.helper.HttpUtils;
import com.jm.mvp.base.BaseActivity;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.yktour.network.ApiException;
import cn.com.yktour.network.base.IPresenter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pc 张立男
 * @Description PasswordActivity 修改密码页面
 * @date 2018/2/26 15:13
 * o(＞﹏＜)o
 */
public class PasswordActivity extends BaseActivity {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_pwd_account)
    TextView mTvPwdAccount;
    @BindView(R.id.et_pwd_pwd_old)
    EditText mEtPwdPwdOld;
    @BindView(R.id.et_pwd_pwd_new)
    EditText mEtPwdPwdNew;
    @BindView(R.id.et_pwd_pwd_new_second)
    EditText mEtPwdPwdNewSecond;
    @BindView(R.id.tv_pwd_confirm)
    TextView mTvPwdConfirm;
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTvTitle.setText("修改密码");
        mTvPwdAccount.setText((String) SPUtils.get("loginName", ""));
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @OnClick({R.id.iv_title_back, R.id.tv_pwd_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_pwd_confirm:
                String pwdOld = CommonUtils.getEditText(mEtPwdPwdOld);
                String pwdNew = CommonUtils.getEditText(mEtPwdPwdNew);
                String pwdNewSecond = CommonUtils.getEditText(mEtPwdPwdNewSecond);

                if (TextUtils.isEmpty(pwdOld)) {
                    ToastUtils.ToastCenter("请输入原密码");
                    return;
                }
                if (TextUtils.isEmpty(pwdNew)) {
                    ToastUtils.ToastCenter("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(pwdNewSecond)) {
                    ToastUtils.ToastCenter("请输入确认密码");
                    return;
                }
                if (!pwdNew.equals(pwdNewSecond)) {
                    ToastUtils.ToastCenter("两次密码不一致，请重新输入");
                    return;
                }
                changPwd(pwdNew, pwdOld);
                break;
            default:
                break;
        }
    }

    private void changPwd(String pwdOld, String pwdNew) {
        HttpUtils.service
                .changPwd(pwdOld, pwdNew)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        closeClient();
                        SPUtils.put("X-S", "");
                        SPUtils.put("X-I", "");
                        SPUtils.put("isLogin", false);
                        SPUtils.put("loginName", "");
                        SPUtils.put("name", "");
                        SPUtils.put("tel", "");
                        Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                        intent.putExtra(Constants.WHICH, Constants.PWD);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof ApiException) {
                            ToastUtils.ToastCenter(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }
}
