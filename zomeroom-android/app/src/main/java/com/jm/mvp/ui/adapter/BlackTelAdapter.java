package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.R;
import com.jm.bean.BlackListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author pc 张立男
 * @Description BlackTelAdapter 黑名单的adapter
 * @date 2018/2/23 19:08
 * o(＞﹏＜)o
 */

public class BlackTelAdapter extends BaseRvAdapter<BlackListBean.DataBean.ListBean, BlackTelAdapter.BlackHolder> {

    public BlackTelAdapter(List<BlackListBean.DataBean.ListBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected BlackHolder getHolder(View view) {
        return new BlackHolder(view);
    }

    @Override
    protected int getResId() {
        return R.layout.item_black;
    }

    @Override
    protected void convert(BlackHolder holder, BlackListBean.DataBean.ListBean bean, final int position) {
        holder.mTvItemBlackTel.setText(bean.getTel().replace(" ", ""));
        holder.mTvItemBlackReason.setText(bean.getRemark());
        holder.mIvItemBlackDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.del(position);
                }
            }
        });
    }

    @Override
    public BlackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_black, parent, false);
        return new BlackHolder(view);
    }

    class BlackHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_black_tel)
        TextView mTvItemBlackTel;
        @BindView(R.id.tv_item_black_reason)
        TextView mTvItemBlackReason;
        @BindView(R.id.iv_item_black_del)
        ImageView mIvItemBlackDel;
        @BindView(R.id.rl_item_black)
        RelativeLayout mRlItemBlack;

        BlackHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setListener(DelListener listener) {
        mListener = listener;
    }

    private DelListener mListener;

    public interface DelListener {
        void del(int position);
    }
}
