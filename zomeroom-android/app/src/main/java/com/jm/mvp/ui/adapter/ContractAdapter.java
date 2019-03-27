package com.jm.mvp.ui.adapter;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.base.Constants;

import java.util.ArrayList;

/**
 * 作者 Created by $miaozhihong on 2019/1/24 15:16
 * 页面功能:合同适配器
 */
public class ContractAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Context context1;
    int types=0;

    public ContractAdapter(Context context, ArrayList<String> mList, int type) {
        super(R.layout.layout_contract, mList);
        context1 = context;
        types=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (types==1){
            Glide.with(mContext).load(Constants.HOST+item).into((ImageView)helper.getView(R.id.iv_imagView));
        }else {
            Glide.with(mContext).load(item).into((ImageView)helper.getView(R.id.iv_imagView));
        }
    }
}
