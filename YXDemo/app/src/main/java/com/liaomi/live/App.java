package com.liaomi.live;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

import java.io.IOException;

import static com.netease.nimlib.sdk.media.player.AudioPlayer.TAG;

/**
 * @author :created by mzh
 * time :2019年3月9日16:52:59
 * 描述：app
 */
public class App extends Application {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
       // registerAVChatIncomingCallObserver(true);
    }


//    /**
//     * 接收到视频进行回调
//     */
//    private void registerAVChatIncomingCallObserver(boolean register) {
//        AVChatManager.getInstance().observeIncomingCall(new Observer<AVChatData>() {
//            @Override
//            public void onEvent(AVChatData data) {
//                String extra = data.getExtra();
//                if (PhoneCallStateObserver.getInstance().getPhoneCallState() != PhoneCallStateObserver.PhoneCallStateEnum.IDLE
//                        || AVChatProfile.getInstance().isAVChatting()
//                        || AVChatManager.getInstance().getCurrentChatId() != 0) {
//                    AVChatManager.getInstance().sendControlCommand(data.getChatId(), AVChatControlCommand.BUSY, null);
//                    return;
//                }
//                // 有网络来电打开AVChatActivity
//                AVChatProfile.getInstance().setAVChatting(true);
//                //AVChatActivity.launch(DemoCache.getContext(), data, AVChatActivity.FROM_BROADCASTRECEIVER);
//                startActivity(new Intent(getApplicationContext(),Main2Activity.class));
//            }
//        }, register);
//    }
//

    private void init() {
        NIMClient.init(this, loginInfo(), options());
        if (NIMUtil.isMainProcess(this)) {
        }
    }

    private SDKOptions options() {
        SDKOptions options = new SDKOptions();
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = WelcomeActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.ic_launcher_background;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        String sdkPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
        options.sdkStorageRootPath = sdkPath;
        options.preloadAttach = true;
        options.thumbnailSize = 200;
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String s, String s1, SessionTypeEnum sessionTypeEnum) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
                return null;
            }
//            @Override
//            public int getDefaultIconResId() {
//                return R.drawable.avatar_def;
//            }
//
//            @Override
//            public Bitmap getTeamIcon(String tid) {
//                return null;
//            }
//
//            @Override
//            public Bitmap getAvatarForMessageNotifier(String account) {
//                return null;
//            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        return null;
    }

    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + "hellos";
        }

        return storageRootPath;
    }
}
