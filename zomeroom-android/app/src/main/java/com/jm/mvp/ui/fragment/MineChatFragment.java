package com.jm.mvp.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.base.BaseFragment;
import com.jm.mvp.ui.activity.BlackListActivity;
import com.jm.mvp.ui.activity.ChatActivity;
import com.jm.mvp.ui.activity.ChatLoginActivity;
import com.jm.mvp.ui.activity.LoginActivity;
import com.jm.mvp.ui.activity.PasswordActivity;
import com.jm.mvp.ui.activity.UserIsOnLineActivity;
import com.jm.utils.AlertDialogUtils;
import com.jm.utils.AppManager;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.yktour.network.base.IPresenter;

/**
 * A simple {@link Fragment} subclass.
 * 即时通讯我的fragment
 */
public class MineChatFragment extends BaseFragment {
    @BindView(R.id.iv_mine)
    ImageView mIvMine;
    @BindView(R.id.tv_mine_account)
    TextView mTvMineAccount;
    @BindView(R.id.tv_mine_pwd)
    TextView mTvMinePwd;
    @BindView(R.id.tv_mine_material)
    TextView mTvMineMaterial;
    @BindView(R.id.tv_mine_update)
    TextView mTvMineUpdate;
    @BindView(R.id.tv_mine_exit)
    TextView mTvMineExit;

    public static MineChatFragment newInstance() {
        MineChatFragment fragment = new MineChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine_what, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }


    @OnClick({R.id.iv_mine, R.id.tv_mine_pwd, R.id.tv_mine_material, R.id.tv_mine_update, R.id.tv_mine_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine:
                break;
            case R.id.tv_mine_pwd:
                Toast.makeText(getContext(), "正在开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_material:
                Toast.makeText(getContext(), "正在开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_update:
                Toast.makeText(getContext(), "当前已是最新版", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_exit:
                /**
                 * 退出登录
                 */
                EMClient.getInstance().logout(true, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        SPUtils.put("isLoginSuccess",false);
                        Intent intent = new Intent(getActivity(), ChatLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        ToastUtils.ToastCenter(status);
                    }

                    @Override
                    public void onError(int code, String message) {
                        ToastUtils.ToastCenter(message);
                    }
                });
                break;
            default:
                break;
        }
    }
}
