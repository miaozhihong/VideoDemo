//package com.jm.utils;
//
//import android.content.Context;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.jm.R;
//import com.jm.helper.SampleApplicationContext;
//import com.youth.banner.loader.ImageLoader;
//
//
///**
// * @author pc 张立男
// * @Description GlideImageLoaderUtils banner的ImageLoader
// * @date 2017/12/5 15:16
// * o(＞﹏＜)o
// */
//
//public class GlideImageLoaderUtils extends ImageLoader {
//    @Override
//    public void displayImage(Context context, Object path, ImageView imageView) {
//        Glide.with(SampleApplicationContext.context)
//                .load((String) path)
//                .apply(new RequestOptions()
//                        .placeholder(R.mipmap.ic_launcher)
//                        .error(R.mipmap.ic_launcher))
//                .into(imageView);
//    }
//}
