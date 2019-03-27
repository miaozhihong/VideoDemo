package com.jm.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.helper.OkHttp3Utils;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.widget.ZoomImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.com.yktour.network.base.IPresenter;
import okhttp3.Call;

/**
 * 图片缩放界面
 */
public class ImageTouchActivity extends BaseActivity {


    private ImageView iv_title_back;
    private ZoomImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_touch);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView = (ZoomImageView) findViewById(R.id.image_view);
        OkHttp3Utils.setIamge(Constants.HOST + image,imageView,getApplicationContext());
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
}
