package com.jm.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author zhaoshuang
 * @Description
 * @date 2017/8/22 16:42
 */

public class DetailScrollYNewUtil {
    public static void getScrollY(RelativeLayout layout_category_topbar, TextView black_title, ImageView iv_black_share, ImageView iv_white_share, ImageView iv_black_back, ImageView iv_white_back,int scrollY,View topBar_shadle_forview){
        if (scrollY >= 0 && scrollY <= 255) {
            //设置标题栏透明度0~255
            float v = (float) (scrollY % 256 * 0.004);
//            Log.e("dmdrs", "scrollY:" + scrollY + " v：" + v);
            layout_category_topbar.setAlpha(v);
            black_title.setAlpha(v);
            iv_white_share.setAlpha(255 - scrollY);
            iv_black_share.setAlpha(scrollY);
            iv_black_back.setAlpha(scrollY);
            iv_white_back.setAlpha(255 - scrollY);
            topBar_shadle_forview.setAlpha(v);
        } else if (scrollY > 255) {
            //滑动距离大于255就设置为不透明
            layout_category_topbar.setAlpha(1);
            black_title.setAlpha(1);
            iv_black_share.setAlpha(255);
            iv_white_share.setAlpha(0);
            iv_black_back.setAlpha(255);
            iv_white_back.setAlpha(0);
            topBar_shadle_forview.setAlpha(1);
        }
    }
}
