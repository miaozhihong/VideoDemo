package com.jm.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.ListBean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/11/20 19:47
 * 页面功能:
 */
public class UserOnLineAdapter extends BaseQuickAdapter<ListBean,BaseViewHolder> {
    public UserOnLineAdapter( Context context, @Nullable List<ListBean> data) {
        super(R.layout.is_onlin, data);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        helper.setText(R.id.loginNames,item.getLoginName())
                .setText(R.id.userNames,item.getName())
                .setText(R.id.logs,item.getOldTime());
        //判断用户是否在线
        int isStatus = item.getIsStatus();
        if (isStatus==1){
            helper.setText(R.id.isOnlines,"在线");
        }else {
            helper.setText(R.id.isOnlines,"掉线")
                  .setTextColor(R.id.isOnlines,Color.parseColor("#FFF40419"))
                  .setTextColor(R.id.loginNames,Color.parseColor("#FFF40419"))
                  .setTextColor(R.id.userNames,Color.parseColor("#FFF40419"))
                  .setTextColor(R.id.logs,Color.parseColor("#FFF40419"));
        }
    }
}
