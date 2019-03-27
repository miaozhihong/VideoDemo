package com.liaomi.live;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.user_name)
    public EditText userName;
    @BindView(R.id.user_pasw)
    public EditText userPasw;
    @BindView(R.id.login)
    public Button login;
    @BindView(R.id.regist)
    public Button regist;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.login})
    public void onViewCilcked(View view) {
        switch (view.getId()) {
            case R.id.login:
                getLogin();
                break;
            case R.id.regist:
                getRegister();
                break;
        }
    }


    private void getRegister() {
        NIMClient.getService(AuthService.class).logout();
        Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
    }


    private void getLogin() {
        String name = userName.getText().toString();
        String pasw = userPasw.getText().toString();
        doLogin(name,pasw);
    }


    /**
     * 手动登录
     */
    public void doLogin(final String userName, String userPasw) {
        LoginInfo info = new LoginInfo(userName, userPasw);
        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,MsgActivity.class);
                intent.putExtra("a",userName);
                startActivity(intent);
         //       NIMClient.getService(AuthServiceObserver .class).observeOtherClients(clientsObserver, true);
                //获取用户在线状态
                StatusCode status = NIMClient.getStatus();
   //             initUserWeChatData();
                // 打开单聊界面
         //       NimUIKit.startP2PSession(getApplicationContext(), "17635197479");
// 打开单聊界面，跳转到指定消息位置
    //            NimUIKit.startP2PSession(getApplicationContext, account, anchor);
            }



            @Override
            public void onFailed(int i) {
                Toast.makeText(MainActivity.this, "登录失败"+i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(MainActivity.this, "登录异常"+throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        NIMClient.getService(AuthService.class).login(info).setCallback(callback);
    }


    /**
     * 检测是否多端登录
     */
    Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
        @Override
        public void onEvent(List<OnlineClient> onlineClients) {
            if (onlineClients == null || onlineClients.size() == 0) {
                return;
            }
            OnlineClient client = onlineClients.get(0);
            switch (client.getClientType()) {
                case ClientType.Windows:
                    // PC端
                    break;
                case ClientType.MAC:
                    // MAC端
                    break;
                case ClientType.Web:
                    // Web端
                    break;
                case ClientType.iOS:
                    // IOS端
                    break;
                case ClientType.Android:
                    // Android端
                    initGetOut();
                    break;
                default:
                    break;
            }
        }


    };
    //提下线
    private void initGetOut() {
//        NIMClient.getService(AuthService.class).kickOtherClient(client).setCallback(new RequestCallback<Void>() {
//            @Override
//            public void onSuccess(Void param) {
//                // 踢出其他端成功
//            }
//
//            @Override
//            public void onFailed(int code) {
//                // 踢出其他端失败，返回失败code
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//                // 踢出其他端错误
//            }
//        });
    }


//    /**
//     * 获取用户同步数据状态
//     */
//    private void initUserWeChatData() {
//        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
//            @Override
//            public void onEvent(LoginSyncStatus status) {
//                if (status == LoginSyncStatus.BEGIN_SYNC) {
//                    Log.i("11111", "login sync data begin");
//                } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
//                    Log.i("11111", "login sync data completed");
//                }
//            }
//        }, register);
//    }

//访问本地数据离线记录
//    boolean res = NIMClient.getService(AuthService.class).openLocalCache(account);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
