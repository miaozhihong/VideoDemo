package com.jm.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.IsVoiceBean;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.fragment.DetailedFragment;
import com.jm.mvp.ui.fragment.MineFragment;
import com.jm.mvp.ui.fragment.StatisticsFragment;
import com.jm.mvp.ui.fragment.TalkFirstFragment;
import com.jm.mvp.ui.fragment.TalkFragment;
import com.jm.mvp.ui.fragment.WarehousFragment;
import com.jm.utils.AppManager;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.com.yktour.network.base.IPresenter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 内务管理系统
 */
public class InteriorActivity extends BaseActivity {
    private static final int READ_PHONE_STATE = 1;
    @BindView(R.id.rg_main)
    RadioGroup mRgMain;
    @BindView(R.id.rab_mine)
    RadioButton mRabMine;
    @BindView(R.id.rab_detailed)
    RadioButton mRabDetailed;

    /**
     * 用于判断两次点击间隔时间
     */
    private long exitTime = 0;
    private MineFragment mMineFragment;
    private TelephonyManager mTm;
    private static String[] CALLS_STATE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    private DetailedFragment mDetailedFragment;
    private WarehousFragment mWarehousFragment;

    @Override
    public int initView(Bundle savedInstanceState) {
        FragmentManager trx = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mDetailedFragment = (DetailedFragment) trx.findFragmentByTag("rab_detailed");
            mWarehousFragment = (WarehousFragment) trx.findFragmentByTag("rab_warehou");
            mMineFragment = (MineFragment) trx.findFragmentByTag("rab_mine");
        }
        return R.layout.activity_interior;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setOnListener();
        setTelListener();
        if (savedInstanceState != null) {
            return;
        }
        mRabDetailed.setChecked(true);
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
                    case R.id.rab_detailed:
                        if (mDetailedFragment == null) {
                            mDetailedFragment = DetailedFragment.newInstance();
                        }
                        if (!mDetailedFragment.isAdded()) {
                            trx.add(R.id.fl_main, mDetailedFragment, "rab_detailed");
                        }
                        trx.show(mDetailedFragment);
                        break;
                        case R.id.rab_warehou:
                        if (mWarehousFragment == null) {
                            mWarehousFragment = WarehousFragment.newInstance();
                        }
                        if (!mWarehousFragment.isAdded()) {
                            trx.add(R.id.fl_main, mWarehousFragment, "rab_warehou");
                        }
                        trx.show(mWarehousFragment);
                        break;
                    case R.id.rab_mine:
                        if (mMineFragment == null) {
                            mMineFragment = MineFragment.newInstance();
                        }
                        if (!mMineFragment.isAdded()) {
                            trx.add(R.id.fl_main, mMineFragment, "rab_mine");
                        }
                        trx.show(mMineFragment);
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
     *
     * @param fragmentTransaction 管理
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fragmentTransaction != null) {
            if (mDetailedFragment!= null) {
                fragmentTransaction.hide(mDetailedFragment);
            }
            if (mWarehousFragment!= null) {
                fragmentTransaction.hide(mWarehousFragment);
            }
            if (mMineFragment != null) {
                fragmentTransaction.hide(mMineFragment);
            }
        }
    }

    /**
     * 返回键 再按一次退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                ToastUtils.ToastCenter(getString(R.string.exit_app));
                exitTime = System.currentTimeMillis();
            } else {
                closeClient();
                AppManager.getAppManager().AppExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
