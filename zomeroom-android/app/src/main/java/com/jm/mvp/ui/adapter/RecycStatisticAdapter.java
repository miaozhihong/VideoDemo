package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.R;
import com.jm.bean.JmStaticsBean;
import com.jm.bean.TalkJmRoomOriginBean;
import com.jm.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 回访数据item页面
 */
public class RecycStatisticAdapter extends BaseRvAdapter<JmStaticsBean.DataBean, RecycStatisticAdapter.TelHolder> {
    private List<JmStaticsBean.DataBean> mList;
    public RecycStatisticAdapter(List<JmStaticsBean.DataBean> list, Context context) {
        super(list, context);
    }

    public RecycStatisticAdapter(List<JmStaticsBean.DataBean> infos, Context context, int type) {
        super(infos, context);
    }

    @Override
    protected TelHolder getHolder(View view) {
        return new TelHolder(view);
    }

    @Override
    protected int getResId() {
        return R.layout.item_statistic;
    }

    @Override
    protected void convert(TelHolder holder, final JmStaticsBean.DataBean bean, final int position) {
        if (null==bean.getUserName()|| bean.getUserName().equals("")){
            holder.mLinStatistic.setVisibility(View.GONE);
        }else {
            holder.mLinStatistic.setVisibility(View.VISIBLE);
            holder.mName.setText(bean.getUserName());
            holder.mFirstTalk.setText(bean.getFirstInt()+"");
            holder.mFollowInt.setText(bean.getFollowInt()+"");
            holder.mPassInt.setText(bean.getPassInt()+"");
            holder.mSigningInt.setText(bean.getSigningInt()+"");
        }
    }
    static class TelHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.first_talk_int)
        TextView mFirstTalk;
        @BindView(R.id.follow_int)
        TextView mFollowInt;
        @BindView(R.id.pass_int)
        TextView mPassInt;
        @BindView(R.id.signing_int)
        TextView mSigningInt;
        @BindView(R.id.lin_statistic)
        LinearLayout mLinStatistic;

        TelHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
