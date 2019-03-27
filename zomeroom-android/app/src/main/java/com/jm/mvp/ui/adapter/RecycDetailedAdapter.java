package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.R;
import com.jm.bean.JmModelDataBean;
import com.jm.bean.TalkJmRoomOriginBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 内务管理适配器
 */
public class RecycDetailedAdapter extends BaseRvAdapter<JmModelDataBean.DataBean.ListBean, RecycDetailedAdapter.TelHolder> {
    private List<TalkJmRoomOriginBean.DataBean.ListBean> mList;
    public static int color;
    private final int type1;

    public RecycDetailedAdapter(List<JmModelDataBean.DataBean.ListBean> list, Context context,int type) {
        super(list, context);
        type1 = type;
    }

    @Override
    protected RecycDetailedAdapter.TelHolder getHolder(View view) {
        return new RecycDetailedAdapter.TelHolder(view);
    }

    @Override
    protected int getResId() {
        return R.layout.item_detailed;
    }
    @Override
    protected void convert(RecycDetailedAdapter.TelHolder holder, final JmModelDataBean.DataBean.ListBean bean, final int position) {
        holder.mTvItemTime.setText(bean.getUploadTime()+"");
        holder.mTvItemShop.setText(bean.getShopName());
        holder.mTvItemName.setText(bean.getUserName());
        holder.mTvItemArea.setText(bean.getArea());
        if (type1==1){
            holder.mTvItemWarehou.setText("取消入库");
        }else {
            holder.mTvItemWarehou.setText("入库");
        }
        /**
         *item点击事件
         */
        holder.mIvItemDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickItem(bean.getId());
                }
            }
        });

        holder.mTvItemAllocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.allocation(position);
                }
            }
        });
        holder.mTvItemWarehou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.warehou(bean.getId());
                }
            }
        });

        holder.mTvItemDetailedCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.cancle();
                }
            }
        });
    }

    static class TelHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_time)
        TextView mTvItemTime;
        @BindView(R.id.tv_item_shop)
        TextView mTvItemShop;
        @BindView(R.id.tv_item_name)
        TextView mTvItemName;
        @BindView(R.id.tv_item_area)
        TextView mTvItemArea;
        @BindView(R.id.iv_item_detail)
        ImageView mIvItemDetail;
        @BindView(R.id.ll_item_tel)
        LinearLayout mLlItemTel;
        @BindView(R.id.tv_item_allocation)
        TextView mTvItemAllocation;
        @BindView(R.id.tv_item_detailed_cancle)
        TextView mTvItemDetailedCancle;
        @BindView(R.id.tv_item_warehou)
        TextView mTvItemWarehou;

        TelHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setListener(RecycDetailedAdapter.ClickListener listener) {
        mListener = listener;
    }

    private RecycDetailedAdapter.ClickListener mListener;

    public interface ClickListener {
        void allocation(int id);//调拨
        void warehou(int id);//入库
        void clickItem(int id);
        void cancle();
    }
}

