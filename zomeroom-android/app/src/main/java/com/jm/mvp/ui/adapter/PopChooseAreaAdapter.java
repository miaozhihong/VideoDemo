package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jm.R;
import com.jm.bean.AreaBean;
import com.jm.mvp.ui.adapter.BaseRvAdapter;
import com.jm.mvp.ui.adapter.PopChooseAdapter;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/17 17:06
 * 页面功能:位置popwindow右侧reclerview adapter
 */
public class PopChooseAreaAdapter extends BaseRvAdapter<AreaBean.DataBean, PopChooseAreaAdapter.PopChooseHolder> {

        public PopChooseAreaAdapter(List<AreaBean.DataBean> list, Context context) {
            super(list, context);
        }

        @Override
        protected PopChooseHolder getHolder(View view) {
            return new PopChooseHolder(view);
        }

        @Override
        protected int getResId() {
            return R.layout.item_pop_choose;
        }

    @Override
    protected void convert(PopChooseHolder holder, AreaBean.DataBean dataBean, int position) {
        holder.mTextView.setText(dataBean.getName());
        if (position == mSelect) {
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.text_color_1CC453));
        } else {
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.text_color_111));
        }
    }


        class PopChooseHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public PopChooseHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_item_pop_choose);
            }
        }
}
