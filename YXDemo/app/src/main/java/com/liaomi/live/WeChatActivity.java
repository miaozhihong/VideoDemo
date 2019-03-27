package com.liaomi.live;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author :created by mzh
 * time :2019年3月11日18:08:39
 * 描述：聊天页面
 */
public class WeChatActivity extends AppCompatActivity {

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        bind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
