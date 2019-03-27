package com.jm.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.TelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/25 12:02
 * 页面功能:打电话选择dialog适配器
 */
public class TalkTelChooseAdapter extends BaseQuickAdapter<TelBean,BaseViewHolder> {

    public TalkTelChooseAdapter(@Nullable List<TelBean> data) {//,
        super(R.layout.dialog_tel, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TelBean item) {
        helper.setText(R.id.tel_name,item.getTelName());
        helper.setText(R.id.tel,item.getTel());
    }
}
