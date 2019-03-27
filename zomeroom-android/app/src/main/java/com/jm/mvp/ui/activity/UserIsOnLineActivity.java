package com.jm.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.ListBean;
import com.jm.bean.OnLineBean;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.adapter.NotReadAdapter;
import com.jm.mvp.ui.adapter.UserOnLineAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.yktour.network.base.IPresenter;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者 Created by $miaozhihong on 2018/11/23 14:50
 * 页面功能:用户在线状态页面
 */
public class UserIsOnLineActivity extends BaseActivity {
    private ImageView back;
    private RecyclerView mReclerView;
    private Dialog loadingDialog;
    private ArrayList<ListBean> mList=new ArrayList<>();
    UserOnLineAdapter mUserOnLineAdapter;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ArrayList<ListBean>obj = (ArrayList<ListBean>) msg.obj;
                    mList.addAll(obj);
                    mUserOnLineAdapter.notifyDataSetChanged();
                    LoadingDialogUtils.closeDialog(loadingDialog);
                break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_is_on_line);
        loadingDialog = LoadingDialogUtils.createLoadingDialog(this, "加载中...");
        loadingDialog.show();
        initDatas();
        initViews();
    }

    public void initViews(){
        back = (ImageView) findViewById(R.id.back);
        TextView title = (TextView) findViewById(R.id.title);
        title.getPaint().setFakeBoldText(true);//加粗
        mReclerView = findViewById(R.id.mReclerView);
        mUserOnLineAdapter=new UserOnLineAdapter(this,mList);
        mReclerView.setLayoutManager(new LinearLayoutManager(UserIsOnLineActivity.this));
        mReclerView.setAdapter(mUserOnLineAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    private void initDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行网络请求获取服务器数据
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                final Request request = new Request.Builder().url(Constants.upFilePath+Constants.getOnlineTiem).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        Log.e("onFailure",e.toString());
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        if (200 != response.code()) {
                            return;
                        }
                        OnLineBean onLineBean = new Gson().fromJson(response.body().string(), OnLineBean.class);
                        List<ListBean> list = onLineBean.getList();
                        Message obtain = Message.obtain();
                        obtain.what=1;
                        obtain.obj=list;
                        handler.sendMessage(obtain);
                    }
                });
            }
        }).start();
    }
}
