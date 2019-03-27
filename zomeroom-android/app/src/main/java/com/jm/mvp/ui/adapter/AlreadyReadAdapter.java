package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.Titles;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/11/9 10:50
 * 页面功能:
 */

public class AlreadyReadAdapter extends BaseQuickAdapter<Titles, BaseViewHolder> {
    private Context context;
    public AlreadyReadAdapter(Context context, @Nullable List<Titles> data) {
        super(R.layout.activity_notread, data);
        context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Titles item) {
            helper.setText(R.id.text, item.getPushtype())
                    .setText(R.id.time,item.getTime());
    }
}
