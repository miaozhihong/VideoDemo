package com.jm.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jm.R;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.contract.SplashContract;
import com.jm.mvp.presenter.SplashPresenter;
import com.jm.utils.SPUtils;
import com.jm.utils.SwitchingDialog;
import com.jm.utils.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * @author pc 张立男
 * @Description SplashActivity 欢迎页面
 * @date 2018/2/19 9:08
 * o(＞﹏＜)o
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.SplashView {

    @Override
    public int initView(Bundle savedInstanceState) {
        // 全屏设置,隐藏窗口所有装饰(包括顶部任务栏)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPresenter = new SplashPresenter(this);
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImageView iv = (ImageView) findViewById(R.id.iv_splash);
        ScaleAnimation sato0 = new ScaleAnimation(0.5f, 1, 0.5f, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        sato0.setDuration(200);
        iv.startAnimation(sato0);
        //权限申请
        AndPermission.with(this)
                .permission(
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_CALL_LOG,
                        Permission.READ_PHONE_STATE,
                        Permission.RECORD_AUDIO,
                        Permission.CAMERA,
                        Permission.READ_CONTACTS)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // 获取权限后，检测登录信息
                        mPresenter.checkLogin();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtils.ToastCenter("权限获取失败，请开启相关权限");
                    }
                })
                .start();
        //提示用户需要开启权限
        boolean enabled = isEnabled();
        if (!enabled){
            goNLPermission(this);
        }

    }


    @Override
    public SplashPresenter obtainPresenter() {
        return mPresenter;
    }

    @Override
    public boolean httpError(int code, String message, int type) {
        if (code == 50001) {
            // 登录过期跳转登录
            ToastUtils.ToastCenter("登录信息已过期，请退出登录重新登录");
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        } else {
            ToastUtils.ToastCenter("数据同步失败");
            //operateDialog();
        }
        return true;
    }

    @Override
    public void checkSuccess() {
        // 调用添加方法，上传通话记录
        mPresenter.addTel();
    }

    @Override
    public void addTelSuccess() {
        ToastUtils.ToastCenter("数据同步成功");
        int isOperate = (int) SPUtils.get("isOperate", 0);
        if (isOperate==1){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else if (isOperate==2) {
            startActivity(new Intent(SplashActivity.this, InteriorActivity.class));
        }else {
            startActivity(new Intent(SplashActivity.this, OperateActivity.class));
        }
        finish();
    }
    public static void goNLPermission(Context context) {
        try {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /**
     * 监听系统通知，需要用户手动开启权限，那么该进程可以不死
     */
//@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("NewApi")
    public static class MyListenerService extends NotificationListenerService {
        @Override
        public void onNotificationPosted(StatusBarNotification sbn) {
        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) {
        }
    }

    // 判断是否打开了通知监听权限
    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //是否切换到运营
    private void operateDialog() {
        int loginType = (int) SPUtils.get("loginType", 9);
        if (loginType == 0) {
            //销售
            SPUtils.put("isOperate", 1);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else if (loginType == 1) {
            //内务管理
            SPUtils.put("isOperate", 2);
            SPUtils.put("lgintypes", 1);
            startActivity(new Intent(SplashActivity.this, InteriorActivity.class));
            finish();
        } else if (loginType == 2) {
            //市场开发
            SPUtils.put("isOperate", 3);
            SPUtils.put("lgintypes", 1);
            startActivity(new Intent(SplashActivity.this, OperateActivity.class));
            finish();
        } else {
            SwitchingDialog switchingDialog = new SwitchingDialog(this);
            switchingDialog.show();
            switchingDialog.setCancelable(false);
            switchingDialog.setCanceledOnTouchOutside(false);
            switchingDialog.setItemClickListenerInterface(new SwitchingDialog.ItemClickListenerInterface() {
                @Override
                public void doOperateClickItem() {
                    SPUtils.put("isOperate", 1);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void doInteriorClickItem() {
                    SPUtils.put("isOperate", 2);
                    SPUtils.put("lgintypes", 1);
                    startActivity(new Intent(SplashActivity.this, InteriorActivity.class));
                    finish();
                }

                @Override
                public void doMarketClickItem() {
                    SPUtils.put("isOperate", 3);
                    SPUtils.put("lgintypes", 1);
                    startActivity(new Intent(SplashActivity.this, OperateActivity.class));
                    finish();
                }
            });
        }
    }
}
