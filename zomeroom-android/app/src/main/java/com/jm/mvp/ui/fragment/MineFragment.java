package com.jm.mvp.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.jm.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.yktour.network.base.IPresenter;

/**
 * @author pc 张立男
 * @Description MineFragment 我的页面
 * @date 2018/2/19 20:51
 * o(＞﹏＜)o
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.iv_mine)
    ImageView mIvMine;
    @BindView(R.id.tv_mine_account)
    TextView mTvMineAccount;
    @BindView(R.id.tv_mine_pwd)
    TextView mTvMinePwd;
    @BindView(R.id.tv_mine_black)
    TextView mTvMineBlack;
    @BindView(R.id.tv_mine_update)
    TextView mTvMineUpdate;
    @BindView(R.id.tv_mine_exit)
    TextView mTvMineExit;
    @BindView(R.id.tv_online)
    TextView tvOnLine;
    @BindView(R.id.tv_mine_talk)
    TextView tvMineTalk;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        int lgintypes = (int) SPUtils.get("lgintypes", 0);
        if (lgintypes == 1) {
            mTvMineBlack.setVisibility(View.GONE);
        } else {
            mTvMineBlack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTvMineAccount.setText((String) SPUtils.get("name", ""));
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.iv_mine, R.id.tv_mine_talk, R.id.tv_online, R.id.tv_mine_pwd, R.id.tv_mine_black, R.id.tv_mine_update, R.id.tv_mine_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine:
                break;
            case R.id.tv_mine_pwd:
                startActivity(new Intent(getActivity(), PasswordActivity.class));
                break;
            case R.id.tv_mine_black:
                startActivity(new Intent(getActivity(), BlackListActivity.class));
                break;
            case R.id.tv_mine_update:
                Toast.makeText(getContext(), "当前已是最新版", Toast.LENGTH_SHORT).show();
                break;
                case R.id.tv_mine_talk:
                    Boolean isLoginSuccess = (Boolean) SPUtils.get("isLoginSuccess", false);
                    if (isLoginSuccess){
                        startActivity(new Intent(getActivity(), ChatActivity.class));
                    }else {
                        startActivity(new Intent(getActivity(), ChatLoginActivity.class));
                    }

                break;
            case R.id.tv_mine_exit:
                int wx = (int) SPUtils.get("WX", 0);
                AlertDialogUtils.customAlertDialog(getActivity(), "提示", "确定要退出？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((BaseActivity) getActivity()).closeClient();
                        SPUtils.put("X-S", "");
                        SPUtils.put("lgintypes", 0);
                        SPUtils.put("X-I", "");
                        SPUtils.put("isLogin", false);
                        SPUtils.put("loginName", "");
                        SPUtils.put("name", "");
                        SPUtils.put("tel", "");
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra(Constants.WHICH, Constants.PWD);
                        startActivity(intent);
                        SPUtils.put("WX", 0);
                    }
                });

                break;
            case R.id.tv_online:
                startActivity(new Intent(getContext(), UserIsOnLineActivity.class));
                break;
            default:
                break;
        }
    }
}
