package com.liaomi.live;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by Administrator on 2017/10/23.
 */

public class JKLiveExtendDialog extends Dialog {

    private LinearLayout llRoot;
    private LinearLayout llMsg;
    private LinearLayout llMic;
    private LinearLayout llAddFriend;
    private TextView tvAddFriend;
    private LinearLayout llCharge;
    private LinearLayout llSwitchCamera;
    private LinearLayout llReport;

    private ImageView ivMic;
    private TextView tvMic;

    private boolean isEnableMic = true;

    public interface LiveExtendCallback {
        void onClickMsg();

        void onClickMic(boolean isEnable);

        void onClickAddFriend();

        void onClickCharge();

        void onClickSwitchCamera();

        void onClickReport();

    }

    private LiveExtendCallback callback;

    public void setIsCreater(boolean isCreater) {
        if (isCreater) {
            setGone(llCharge);
            setGone(llMsg);
        }
    }

    public void setLiveExtendCallback(LiveExtendCallback callback) {
        this.callback = callback;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (callback == null) return;
            switch (v.getId()) {
                case R.id.ll_root:
                    dismiss();
                    break;
                case R.id.ll_msg:
                    callback.onClickMsg();
                    break;
                case R.id.ll_mic:
                    isEnableMic = !isEnableMic;
                    ivMic.setSelected(isEnableMic);
                    tvMic.setText(getMicStr());
                    callback.onClickMic(isEnableMic);
                    break;
                case R.id.ll_add_friend:
                    callback.onClickAddFriend();
                    break;
                case R.id.ll_charge:
                    callback.onClickCharge();
                    break;
                case R.id.ll_switch_camera:
                    callback.onClickSwitchCamera();
                    break;
                case R.id.ll_report:
                    callback.onClickReport();
                    break;
            }
            dismiss();
        }
    };

    public JKLiveExtendDialog(Activity activity) {
        super(activity, R.style.full_screen_dialog);

        setContentView(R.layout.jk_dialog_live_more);

        llRoot = findViewById(R.id.ll_root);
        llMsg = findViewById(R.id.ll_msg);
        llMic = findViewById(R.id.ll_mic);
        llAddFriend = findViewById(R.id.ll_add_friend);
        tvAddFriend = findViewById(R.id.tv_add_friend);
        llCharge = findViewById(R.id.ll_charge);
        llSwitchCamera = (LinearLayout) findViewById(R.id.ll_switch_camera);
        llReport = (LinearLayout) findViewById(R.id.ll_report);

        ivMic = (ImageView) findViewById(R.id.iv_mic);
        tvMic = (TextView) findViewById(R.id.tv_mic);
        ivMic.setSelected(isEnableMic);
        tvMic.setText(getMicStr());

        setListener(llRoot, llMsg, llMic, llAddFriend, llCharge, llSwitchCamera, llReport);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    private void setListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    private String getMicStr() {
        String Mic;
        if (isEnableMic) {
            Mic = "关闭麦克风";
        } else {
            Mic = "打开麦克风";
        }
        return Mic;
    }

    public TextView getTvAddFriend() {
        return tvAddFriend;
    }

    /**
     * view是否处于GONE状态
     *
     * @param view
     * @return
     */
    public static boolean isGone(View view) {
        if (view == null) {
            return false;
        } else {
            return view.getVisibility() == View.GONE;
        }
    }

    /**
     * 设置view为GONE
     *
     * @param view
     * @return true-view处于GONE
     */
    public static boolean setGone(View view) {
        if (view == null) {
            return false;
        }
        if (isGone(view)) {
            return true;
        }
        view.setVisibility(View.GONE);
        return true;
    }
}
