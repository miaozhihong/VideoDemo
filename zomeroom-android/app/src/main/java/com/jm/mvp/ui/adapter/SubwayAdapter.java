package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jm.R;
import com.jm.bean.SubwayBean;

import java.util.List;

/**
 * @author pc 张立男
 * @Description SubwayAdapter 地铁搜索的adpater
 * @date 2018/5/8 10:27
 * o(＞﹏＜)o
 */

public class SubwayAdapter extends BaseRvAdapter<SubwayBean.DataBean, SubwayAdapter.SubwayHolder> {

    public SubwayAdapter(List<SubwayBean.DataBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected SubwayHolder getHolder(View view) {
        return new SubwayHolder(view);
    }

    @Override
    protected int getResId() {
        return R.layout.item_subway;
    }

    @Override
    protected void convert(SubwayHolder holder, SubwayBean.DataBean bean, int position) {
        holder.mTvItemSubway.setText(bean.getStationName());
    }

    class SubwayHolder extends RecyclerView.ViewHolder {
        TextView mTvItemSubway;

        public SubwayHolder(View itemView) {
            super(itemView);
            mTvItemSubway = (TextView) itemView.findViewById(R.id.tv_item_subway);
        }
    }
}
