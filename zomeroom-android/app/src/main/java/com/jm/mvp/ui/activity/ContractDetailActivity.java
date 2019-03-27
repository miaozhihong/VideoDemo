package com.jm.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.adapter.ContractDetailAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.yktour.network.base.IPresenter;

/**
 * 轮播
 */
public class ContractDetailActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;
    private ArrayList<String> listPicture = new ArrayList<>();
    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_contract_detail;

    }
    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(Constants.ARRAY);
        listPicture.clear();
        listPicture.addAll(stringArrayListExtra);
        banner.setImages(listPicture).setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .isAutoPlay(false)
                .setIndicatorGravity(BannerConfig.CENTER)//设置指示器位置
                .start();
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(Constants.HOST+path).into(imageView);
        }
    }

}
