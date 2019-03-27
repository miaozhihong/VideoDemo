package com.jm.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.TelBean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/12 15:16
 * 页面功能:调拨选择dialog适配器
 */
public class DetailChooseAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public DetailChooseAdapter(@Nullable List<String> data) {//,
        super(R.layout.dialog_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tel_name,item);
    }
}
