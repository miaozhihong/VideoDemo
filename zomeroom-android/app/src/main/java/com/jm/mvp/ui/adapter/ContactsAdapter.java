package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.UserBean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 11:04
 * 页面功能:联系人adapter
 */
public class ContactsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ContactsAdapter(@Nullable List<String> data) {
        super(R.layout.item_chat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_subway, item);
        helper.setText(R.id.tv_state,"在线");
        final ImageView imageView = helper.getView(R.id.images);
//        Glide.with(mContext).load(R.mipmap.headimage).asBitmap()
//                .override(1000, 200)
//                .placeholder(R.mipmap.headimage)//等待图
//                .error(R.mipmap.headimage)//加载失败图
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        imageView.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
    }
}
