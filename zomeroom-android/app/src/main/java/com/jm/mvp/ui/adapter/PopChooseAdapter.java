package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jm.R;

import java.util.List;

/**
 * @author pc 张立男
 * @Description PopChooseAdapter 通用的筛选框
 * @date 2018/5/4 15:19
 * o(＞﹏＜)o
 */
public class PopChooseAdapter extends BaseRvAdapter<String, PopChooseAdapter.PopChooseHolder> {

    public PopChooseAdapter(List<String> list, Context context) {
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
    protected void convert(PopChooseHolder holder, String s, int position) {
        holder.mTextView.setText(s);
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
