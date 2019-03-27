package com.liaomi.live;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.constant.AVChatEventType;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCalleeAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.model.AVChatCommonEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatControlEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNotifyOption;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.netease.vcloud.video.effect.VideoEffectFactory;

/**
 * 消息页面
 */
public class MsgActivity extends AppCompatActivity implements View.OnClickListener {
    private String account;
    private CallStateEnum callingState;
    private AVChatData avChatData;
    private int state = 2;
    private AVChatCameraCapturer mCameraCapturer;
    private AVChatSurfaceViewRenderer sur;
    private AVChatSurfaceViewRenderer small_sur;
    private RelativeLayout rl_call;
    private ImageView iv_answer;
    private ImageView iv_hangup;
    private ImageView videochat_hands_free;
    private ImageView videochat_hangup;
    private ImageView videochat_hands_nowheat;
    private View chat_call;
    private View chat_video;
    private Button viewById;
    private boolean microphoneMute = false;//是否静音
    private boolean speakerEnable = false;//{@code true} 打开扬声器，{@code false} 关闭扬声器
    private String a;
    private JKLiveExtendDialog mLiveExtendDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        account = "56fa773c70e01a65b4c25c8b7099383d";
        Intent intent = getIntent();
        a = intent.getStringExtra("a");
        viewById = findViewById(R.id.call);
        viewById.setOnClickListener(this);
        callingState = CallStateEnum.getCallStateEnum(state);
        if (callingState == CallStateEnum.INVALID) {
            finish();
        }
        initView();
        registerObserves(true);

    }

    private void initView() {
        sur = findViewById(R.id.sur);
        small_sur = findViewById(R.id.small_sur);
        small_sur.setZOrderOnTop(true);

        videochat_hands_free = findViewById(R.id.videochat_hands_free);
        videochat_hangup = findViewById(R.id.videochat_hangup);
        videochat_hands_nowheat = findViewById(R.id.videochat_hands_nowheat);

        videochat_hands_free.setImageResource(speakerEnable ? R.drawable.ic_launcher_background : R.drawable.ic_launcher_background);
        videochat_hands_nowheat.setImageResource(microphoneMute ? R.drawable.ic_launcher_background : R.drawable.ic_launcher_background);

//        chat_call = findViewById(R.id.chat_call);
//        chat_video = findViewById(R.id.chat_video);
//        rl_call = findViewById(R.id.rl_call);
//        iv_answer = findViewById(R.id.iv_answer);
//        iv_hangup = findViewById(R.id.iv_hangup);


//        iv_answer.setOnClickListener(this);
//        iv_hangup.setOnClickListener(this);
//        videochat_hands_free.setOnClickListener(this);
//        videochat_hands_nowheat.setOnClickListener(this);
//        videochat_hangup.setOnClickListener(this);
//        chat_call.setVisibility(View.VISIBLE);
//        chat_video.setVisibility(View.GONE);

        if (callingState == CallStateEnum.OUTGOING_VIDEO_CALLING) {
         //   iv_answer.setVisibility(View.GONE);
        } else if (callingState == CallStateEnum.INCOMING_VIDEO_CALLING) {
            iv_answer.setVisibility(View.VISIBLE);
        }
        addOperateButtons();
    }

    protected void addOperateButtons() {
//        if (mOperateButtonsView == null) {
//            mOperateButtonsView = new JKOperateButtonsView(this);
//            mOperateButtonsView.setOperateCallback(this);
//            mOperateButtonsView.setCreater(false);
//            ViewUtils.replaceView(findViewById(R.id.fl_bottom_buttons), mOperateButtonsView);
//        }
    }
    /**
     * 初始化参数
     */
    private void init() {
        VideoEffectFactory.getVCloudEffect().init(MsgActivity.this, true, false);
        mCameraCapturer = AVChatVideoCapturerFactory.createCameraCapturer();
        AVChatManager.getInstance().enableRtc();
        AVChatManager.getInstance().enableVideo();
        AVChatManager.getInstance().setupVideoCapturer(mCameraCapturer);
        AVChatManager.getInstance().startVideoPreview();

        AVChatNotifyOption notifyOption = new AVChatNotifyOption();
        notifyOption.extendMessage = account;
        notifyOption.forceKeepCalling = false;
        if (callingState == CallStateEnum.OUTGOING_VIDEO_CALLING) {
            AVChatManager.getInstance().call2(account, AVChatType.VIDEO, notifyOption, new AVChatCallback<AVChatData>() {
                @Override
                public void onSuccess(AVChatData avChatData) {
                    avChatData = avChatData;
                    Log.e("a", "获取通话ID:" + avChatData.getChatId() +
                            "获取对方帐号:" + avChatData.getAccount() +
                            "获取通话的类型" + avChatData.getChatType() +
                            "获取当前事件发生的时间戳" + avChatData.getTimeTag() +
                            "获取自定义" + avChatData.getExtra()
                    );
                }

                @Override
                public void onFailed(int code) {
                    Log.e("111", "失败" + code);

                }

                @Override
                public void onException(Throwable exception) {
                    Log.e("222", "失败" + exception.getMessage());
                }
            });
        }
    }


    private void registerObserves(boolean register) {
        AVChatManager.getInstance().observeAVChatState(avchatStateObserver, register);
        AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, register);
        AVChatManager.getInstance().observeCalleeAckNotification(callAckObserver, register);
        AVChatManager.getInstance().observeControlNotification(callControlObserver, register);
       // AVChatManager.getInstance().observeOnlineAckNotification(onlineAckObserver, register);
        //放到所有UI的基类里面注册，所有的UI实现onKickOut接口
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }

    // 通话过程状态监听
    private SimpleAVChatStateObserver avchatStateObserver = new SimpleAVChatStateObserver() {
        @Override
        public void onLowStorageSpaceWarning(long availableSize) {
            if (state == AVChatType.VIDEO.getValue()) {

            }
        }

        @Override
        public void onUserJoined(String account) {
            super.onUserJoined(account);
            small_sur.setVisibility(View.VISIBLE);
            initLargeSurfaceView(account);

        }

        @Override
        public void onCallEstablished() {
          //  chat_call.setVisibility(View.GONE);
         //   chat_video.setVisibility(View.VISIBLE);
            // 接通以后，自己是小屏幕显示图像，对方是大屏幕显示图像
            small_sur.setVisibility(View.VISIBLE);
            initSmallSur(a);
            if (mLiveExtendDialog != null) {
                mLiveExtendDialog.setIsCreater(true);
            }
        }

        @Override
        public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {

            return true;
        }

        @Override
        public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
            return true;
        }

    };

    // 通话过程中，收到对方挂断电话
    Observer<AVChatCommonEvent> callHangupObserver = new Observer<AVChatCommonEvent>() {
        @Override
        public void onEvent(AVChatCommonEvent avChatHangUpInfo) {
//            avChatData = avChatController.getAvChatData();
//            if (avChatData != null && avChatData.getChatId() == avChatHangUpInfo.getChatId()) {
//                hangUpByOther(AVChatExitCode.HANGUP);
//                cancelCallingNotifier();
//                // 如果是incoming call主叫方挂断，那么通知栏有通知
//                if (mIsInComingCall && !isCallEstablished) {
//                    activeMissCallNotifier();
//                }
//            }

        }
    };

    // 呼叫时，被叫方的响应（接听、拒绝、忙）
    Observer<AVChatCalleeAckEvent> callAckObserver = new Observer<AVChatCalleeAckEvent>() {
        @Override
        public void onEvent(AVChatCalleeAckEvent ackInfo) {
//            AVChatData info = avChatController.getAvChatData();
//            if (info != null && info.getChatId() == ackInfo.getChatId()) {
//                if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_BUSY) {
//                    hangUpByOther(AVChatExitCode.PEER_BUSY);
//                } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_REJECT) {
//                    hangUpByOther(AVChatExitCode.REJECT);
//                } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_AGREE) {
//                    AVChatSoundPlayer.instance().stop();
//                    avChatController.isCallEstablish.set(true);
//                }
//            }
        }
    };

//    Observer<Integer> timeoutObserver = new Observer<Integer>() {
//        @Override
//        public void onEvent(Integer integer) {
//            manualHangUp(AVChatExitCode.CANCEL);
//            // 来电超时，自己未接听
//            if (mIsInComingCall) {
//                activeMissCallNotifier();
//            }
//            finish();
//        }
//    };

    // 监听音视频模式切换通知, 对方音视频开关通知
    Observer<AVChatControlEvent> callControlObserver = new Observer<AVChatControlEvent>() {
        @Override
        public void onEvent(AVChatControlEvent netCallControlNotification) {
            handleCallControl(netCallControlNotification);
        }
    };
    // 处理音视频切换请求和对方音视频开关通知
    private void handleCallControl(AVChatControlEvent notification) {
        if (AVChatManager.getInstance().getCurrentChatId() != notification.getChatId()) {
            return;
        }
        switch (notification.getControlCommand()) {
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO:
                //incomingAudioToVideo();
                Toast.makeText(MsgActivity.this,"incomingAudioToVideo", Toast.LENGTH_SHORT).show();
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_AGREE:
                // 对方同意切成视频啦
               // state = AVChatType.VIDEO.getValue();
                //avChatVideoUI.onAudioToVideoAgree(notification.getAccount());
                Toast.makeText(MsgActivity.this,"对方同意切成视频啦", Toast.LENGTH_SHORT).show();
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_REJECT:
               // rejectAudioToVideo();
                Toast.makeText(MsgActivity.this, R.string.avchat_switch_video_reject, Toast.LENGTH_SHORT).show();
                break;
            case AVChatControlCommand.SWITCH_VIDEO_TO_AUDIO:
                //onVideoToAudio();
                Toast.makeText(MsgActivity.this,"onVideoToAudio", Toast.LENGTH_SHORT).show();
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_OFF:
                // 收到对方关闭画面通知
//                if (state == AVChatType.VIDEO.getValue()) {
//                    avChatVideoUI.peerVideoOff();
//                }
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_ON:
                // 收到对方打开画面通知
//                if (state == AVChatType.VIDEO.getValue()) {
//                    avChatVideoUI.peerVideoOn();
//                }
                break;
            default:
                Toast.makeText(this, "对方发来指令值：" + notification.getControlCommand(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


//    /**
//     * 注册/注销同时在线的其他端对主叫方的响应
//     */
//    Observer<AVChatOnlineAckEvent> onlineAckObserver = new Observer<AVChatOnlineAckEvent>() {
//        @Override
//        public void onEvent(AVChatOnlineAckEvent ackInfo) {
//            if (state == AVChatType.AUDIO.getValue()) {
//                avChatData = avChatController.getAvChatData();
//            } else {
//                avChatData = avChatVideoUI.getAvChatData();
//            }
//            if (avChatData != null && avChatData.getChatId() == ackInfo.getChatId()) {
//                AVChatSoundPlayer.instance().stop();
//
//                String client = null;
//                switch (ackInfo.getClientType()) {
//                    case ClientType.Web:
//                        client = "Web";
//                        break;
//                    case ClientType.Windows:
//                        client = "Windows";
//                        break;
//                    case ClientType.Android:
//                        client = "Android";
//                        break;
//                    case ClientType.iOS:
//                        client = "iOS";
//                        break;
//                    case ClientType.MAC:
//                        client = "Mac";
//                        break;
//                    default:
//                        break;
//                }
//                if (client != null) {
//                    String option = ackInfo.getEvent() == AVChatEventType.CALLEE_ONLINE_CLIENT_ACK_AGREE ? "接听！" : "拒绝！";
//                    Toast.makeText(AVChatActivity.this, "通话已在" + client + "端被" + option, Toast.LENGTH_SHORT).show();
//                }
//                finish();
//            }
//        }
//    };

    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {
        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
//                finish();
            }
        }
    };

    private void initSmallSur(String account) {
        // 设置画布，加入到自己的布局中，用于呈现视频图像
        // account 要显示视频的用户帐号
        if (a.equals(account)) {
            AVChatManager.getInstance().setupLocalVideoRender(null, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
            AVChatManager.getInstance().setupLocalVideoRender(small_sur, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        } else {
            AVChatManager.getInstance().setupRemoteVideoRender(account, small_sur, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        }
    }



    // 大图像surface view 初始化
    public void initLargeSurfaceView(String account) {
        // 设置画布，加入到自己的布局中，用于呈现视频图像
        // account 要显示视频的用户帐号
        if (a.equals(account)) {
            AVChatManager.getInstance().setupLocalVideoRender(sur, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        } else {
            AVChatManager.getInstance().setupRemoteVideoRender(account, sur, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AVChatManager.getInstance().disableRtc();
        AVChatManager.getInstance().disableVideo();
        AVChatManager.getInstance().stopVideoPreview();
        registerObserves(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_answer:
                AVChatManager.getInstance().accept2(avChatData.getChatId(), new AVChatCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("a","接通成功");
                      //  chat_call.setVisibility(View.GONE);
                    //    chat_video.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
                break;
            case R.id.call:
                init();
                break;
            case R.id.videochat_hangup:
                long chatId = 0;
                if (avChatData != null) {
                    chatId = avChatData.getChatId();
                }
                AVChatManager.getInstance().hangUp2(chatId, new AVChatCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("a","取消或挂断成功");
                        finish();
                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
                break;

            case R.id.videochat_hands_free:
                speakerEnable = !speakerEnable;
                AVChatManager.getInstance().setSpeaker(speakerEnable);
                videochat_hands_free.setImageResource(speakerEnable ? R.drawable.ic_launcher_background : R.drawable.ic_launcher_background);
                break;
            case R.id.videochat_hands_nowheat:
                microphoneMute = !microphoneMute;
                AVChatManager.getInstance().setMicrophoneMute(microphoneMute);
                videochat_hands_nowheat.setImageResource(microphoneMute ? R.drawable.ic_launcher_background : R.drawable.ic_launcher_background);
                break;
        }
    }

    /**
     * dailog
     * @return
     */
    private JKLiveExtendDialog getLiveExtendDialog() {
        if (mLiveExtendDialog == null) {
            mLiveExtendDialog = new JKLiveExtendDialog(this);
            mLiveExtendDialog.setIsCreater(true);
            mLiveExtendDialog.setLiveExtendCallback(new JKLiveExtendDialog.LiveExtendCallback() {

                @Override
                public void onClickMsg() {

                }

                @Override
                public void onClickMic(boolean isEnable) {
//                    LogUtils.e(JK_TAG + "isEnable Mic :" + isEnable);
//                    getPushSDK().enableMic(isEnable);
                }

                @Override
                public void onClickAddFriend() {
//                    getLiveBusiness().requestFollowInRoom(getAnotherUserId());
                }

                @Override
                public void onClickCharge() {
//                    getLiveRechargeDialog().show();
                }

                @Override
                public void onClickSwitchCamera() {
//                    getPushSDK().switchCamera();
                }

                @Override
                public void onClickReport() {
//                    getLiveReportDialog().show();
                }
            });
        }
        return mLiveExtendDialog;
    }

}
