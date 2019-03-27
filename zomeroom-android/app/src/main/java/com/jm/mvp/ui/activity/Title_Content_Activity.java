package com.jm.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.DaoMaster;
import com.jm.bean.DaoSession;
import com.jm.bean.ListBean;
import com.jm.bean.OnLineBean;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.mvp.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.yktour.network.base.IPresenter;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Title_Content_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title;
    private TextView time;
    private TextView text;
    private String contents;
    private String titles;
    private String times;
    private String sender_letters;
    private String addressee_letters;
    private TextView sender_letter;
    private TextView addressee_letter;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    sender_letter.setText("发件人："+(String) msg.obj);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title__content_);
        initView();
        initDaoData();
    }

    private void initDaoData() {
        Intent intent = getIntent();
         contents =  intent.getStringExtra("content");
         titles =  intent.getStringExtra("titles");
         times =  intent.getStringExtra("time");
         sender_letters =  intent.getStringExtra("sender_letter");
         addressee_letters =  intent.getStringExtra("addressee_letter");

        if (!TextUtils.isEmpty(sender_letters)){
            if (sender_letters.length()>2){
                sender_letter.setText("发件人："+sender_letters);
            }else {
                initDatas();
            }
        }

        if (null!=title){
            title.setText("标题："+titles);
            time.setText("时间："+times);
            text.setText("内容："+contents);
            addressee_letter.setText("收件人："+addressee_letters);
        }

    }


    public void initView() {

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        time = (TextView) findViewById(R.id.time);
        text = (TextView) findViewById(R.id.text);
        sender_letter = (TextView) findViewById(R.id.sender_letter);
        addressee_letter = (TextView) findViewById(R.id.addressee_letter);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
    private void initDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行网络请求获取服务器数据
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                final Request request = new Request.Builder().url(Constants.upFilePath+Constants.byShopName+sender_letters).build();
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
                        try {
                            String string = response.body().string();
                            JSONObject jsonObject=new JSONObject(string);
                            String shopName = jsonObject.getString("shopName");
                            Message obtain = Message.obtain();
                            obtain.what=1;
                            obtain.obj=shopName;
                            handler.sendMessage(obtain);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}
