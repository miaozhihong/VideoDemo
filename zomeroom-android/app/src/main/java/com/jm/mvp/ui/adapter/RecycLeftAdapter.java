package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.TelListBean;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者 Created by Administrator on 2018/8/31 15:28
 * 页面功能:
 */
public class RecycLeftAdapter extends BaseRvAdapter<TelListBean.DataBean.ListBean, TelAdapter.TelHolder> {
    private List<TelListBean.DataBean.ListBean> mList;
    private int mType;
    public static int color;
    public RecycLeftAdapter(List<TelListBean.DataBean.ListBean> list, Context context) {
        super(list, context);
    }

    public RecycLeftAdapter(List<TelListBean.DataBean.ListBean> infos, Context context, int type) {
        super(infos, context);
        mType = type;
    }

    @Override
    protected TelAdapter.TelHolder getHolder(View view) {
        return new TelAdapter.TelHolder(view);
    }

    @Override
    protected int getResId() {
        return R.layout.item_tel;
    }

    @Override
    protected void convert(TelAdapter.TelHolder holder, final TelListBean.DataBean.ListBean bean, final int position) {
        holder.mTvItemTelTel.setText(bean.getTel().replace(" ", ""));
        holder.mTvItemTelTime.setText(bean.getCreateTimeStr());
        int call = bean.getIsCall();
        // 是否拨打电话（0-否 1-是）
        if (call == 0) {
            color=1;
            holder.mTvItemTelTel.setTextColor(mContext.getResources().getColor(R.color.text_color_F05049));
            holder.mIvItemTelIsCall.setVisibility(View.INVISIBLE);
        } else {
            holder.mTvItemTelTel.setTextColor(mContext.getResources().getColor(R.color.text_color_333));
            holder.mIvItemTelIsCall.setVisibility(View.VISIBLE);
        }
        holder.mTvItemTelCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.call(bean.getTel().replace(" ", ""),bean.getId());
                    SPUtils.put("phone",bean.getTel());
                    SPUtils.put("shopId",bean.getShopId());
                }
            }
        });
        holder.mTvItemTelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.addBlack(bean.getTel().replace(" ", ""));
                }
            }
        });
        holder.mTvItemTelShop.setText(bean.getShopName());
        // 顾客意向(0- 没有值，1-跟进，2-pass,3-成交,4-与销售无关
        holder.mTvItemTelIntention.setText(CommonUtils.getIntention(bean.getClientIntention()));
        //进店看房 隐藏转进店选项
        if (mType == Constants.SEE) {
            holder.mTvItemTelCome.setVisibility(View.GONE);
        } else {
            holder.mTvItemTelCome.setVisibility(View.VISIBLE);
            // 是否转进店 0 否 1 是
            if (bean.getIsEnterShop() == 0) {
                holder.mTvItemTelCome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.come(bean.getId());
                        }
                    }
                });
                holder.mTvItemTelCome.setBackgroundColor(mContext.getResources().getColor(R.color.bg_fe051b));
            } else {
                holder.mTvItemTelCome.setBackgroundColor(mContext.getResources().getColor(R.color.login_bg));
                holder.mTvItemTelCome.setOnClickListener(null);
            }
        }
        holder.mIvItemTelDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickItem(bean.getId());
                }
            }
        });
    }

    static class TelHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_tel_time)
        TextView mTvItemTelTime;
        @BindView(R.id.tv_item_tel_shop)
        TextView mTvItemTelShop;
        @BindView(R.id.tv_item_tel_tel)
        TextView mTvItemTelTel;
        @BindView(R.id.tv_item_tel_intention)
        TextView mTvItemTelIntention;
        @BindView(R.id.tv_item_tel_call)
        TextView mTvItemTelCall;
        @BindView(R.id.tv_item_tel_come)
        TextView mTvItemTelCome;
        @BindView(R.id.tv_item_tel_add)
        TextView mTvItemTelAdd;
        @BindView(R.id.ll_item_tel)
        LinearLayout mLlItemTel;
        @BindView(R.id.iv_item_tel_detail)
        ImageView mIvItemTelDetail;
        @BindView(R.id.iv_item_tel_isCall)
        ImageView mIvItemTelIsCall;

        TelHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setListener(TelAdapter.ClickListener listener) {
        mListener = listener;
    }

    private TelAdapter.ClickListener mListener;

    public interface ClickListener {
        void call(String tel,int id);

        void come(int shopId);

        void clickItem(int id);

        void addBlack(String tel);
    }
}
