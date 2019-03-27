package com.jm.mvp.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.mvp.ui.activity.LoginActivity;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.ActivitybarUtils;
import com.jm.utils.AppManager;
import com.jm.utils.BarTextColorUtils;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.StatusBarUtil;
import com.jm.utils.ToastUtils;
import com.umeng.message.PushAgent;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.yktour.network.base.IActivity;
import cn.com.yktour.network.base.IPresenter;

/**
 * @author sky
 * @date 2017/8/7
 * description: APP BaseActivity用来封装一些公共方法
 * changed: 创建
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity<P> {
    /**
     * 通用的TAG
     */
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    /**
     * presenter代表
     */
    protected P mPresenter;
    protected Dialog pd;
    protected DemoClient mClient;
    protected Gson mGson;
    /**
     * 手动关闭
     */
    protected boolean mClose = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //限制页面竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //状态栏背景透明,状态栏字体变为黑色
        BarTextColorUtils.transparencyBar(this);
        BarTextColorUtils.StatusBarLightMode(this, true);

        PushAgent.getInstance(this).onAppStart();
        ActivitybarUtils.assistActivity(findViewById(android.R.id.content));

        super.onCreate(savedInstanceState);
        //全局Activity堆栈管理
        AppManager.getAppManager().addActivity(this);
        //沉浸式显示
        StatusBarUtil.statuInScreen(this);
        try {
            //从实现类获取布局
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterKnife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            ToastUtils.ToastCenter("页面数据异常");
            e.printStackTrace();
            return;
        }
        setPresenter();
        //初始化数据
        initData(savedInstanceState);
    }

    /**
     * 设置Presenter到父类统一管理
     *
     * @param presenter 子类中实现的Presenter
     */
    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    public void setPresenter() {
        this.mPresenter = obtainPresenter();
        //google最新框架实现注解获得生命周期状态
        if (this.mPresenter != null) {
            getLifecycle().addObserver((LifecycleObserver) mPresenter);
        }
    }

    /**
     * 页面重建时，重新设置Presenter
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = obtainPresenter();
        }
    }

    /**
     * Destroy时，执行各种注销置空操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //执行P层注销操作
        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
        this.mPresenter = null;
        if (pd != null) {
            pd.cancel();
            pd = null;
        }
        //注解解绑
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        AppManager.getAppManager().removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //收回软键盘
        CommonUtils.hideSoft(this);
    }


    /**
     * 展示加载框
     */
    public void showLoading() {
        if (pd == null) {
            pd = LoadingDialogUtils.createLoadingDialog(this, "加载中");
            pd.show();
        } else {
            pd.show();
        }
    }

    /**
     * 展示加载框
     *
     * @param msg 动态msg
     */
    public void showLoading(String msg) {
        pd = LoadingDialogUtils.createLoadingDialog(this, msg);
        pd.show();
    }

    /**
     * 隐藏加载框
     */
    public void hideLoading() {
        LoadingDialogUtils.closeDialog(pd);
    }

    /**
     * 全局跳转登陆页面
     */
    public void toLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public class DemoClient extends WebSocketClient {


        public DemoClient(URI serverUri) {
            super(serverUri);
        }

        public DemoClient(URI serverUri, Draft protocolDraft) {
            super(serverUri, protocolDraft);
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Map<String, String> map = new HashMap<>();
            map.put("loginName", (String) SPUtils.get("loginName", ""));
            map.put("type", "open");
            send(mGson.toJson(map));
            Log.e("xx", "开始连接");
        }

        @Override
        public void onMessage(final String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    ToastUtils.ToastCenter(message);
//                    showNotify(message);
                }
            });
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            if (!mClose) {
                try {
                    mClient = new DemoClient(new URI(Constants.WEB_SOCKET));
                    CommonUtils.checkNotNull(mClient, "连接时，client为空").connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(Exception ex) {
            if (!mClose) {
                try {
                    mClient = new DemoClient(new URI(Constants.WEB_SOCKET));
                    CommonUtils.checkNotNull(mClient, "连接时，client为空").connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    final String CHANNEL_ID = "channel_id_1";
    final String CHANNEL_NAME = "channel_name_1";

    public void showNotify(String str) {

        NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = null;


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this);
        } else {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            mManager.createNotificationChannel(notificationChannel);
            builder = new Notification.Builder(this, CHANNEL_ID);
        }

        Notification notification = builder.setContentTitle("通知信息")
                .setContentText(str)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        mManager.notify(1, notification);
    }

    public void closeClient() {
        mClose = true;
        Map<String, String> map = new HashMap<>();
        map.put("loginName", (String) SPUtils.get("loginName", ""));
        map.put("type", "close");
        if (mClient != null) {
            CommonUtils.checkNotNull(mClient, "client已经为空").send(mGson.toJson(map));
            CommonUtils.checkNotNull(mClient, "断开时，client为空").close();
            Log.e("xx", "client关闭");
        } else {
            Log.e("xx", "client为空");
        }
    }

}
