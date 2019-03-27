package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.SeeListBean;
import com.jm.utils.CommonUtils;
import com.jm.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author pc 张立男
 * @Description TelAdapter 电话咨询的列表adapter 进店看房
 * @date 2018/2/24 19:11
 * o(＞﹏＜)o
 */

public class SeeAdapter extends BaseRvAdapter<SeeListBean.DataBean.ListBean, SeeAdapter.SeeHolder> {
    private List<SeeListBean.DataBean.ListBean> mList;
    private int mType;
    public static int Tag=0;
    public SeeAdapter(List<SeeListBean.DataBean.ListBean> list, Context context) {
        super(list, context);
    }

    public SeeAdapter(List<SeeListBean.DataBean.ListBean> infos, Context context, int type) {
        super(infos, context);
        mType = type;
    }

    @Override
    protected SeeHolder getHolder(View view) {
        return new SeeAdapter.SeeHolder(view);
    }

    @Override
    protected int getResId() {
        return R.layout.item_tel;
    }

    @Override
    protected void convert(SeeHolder holder, final SeeListBean.DataBean.ListBean bean, final int position) {
        holder.mTvItemTelTel.setText(bean.getTel().replace(" ", ""));
        holder.mTvItemTelTime.setText(bean.getCreateTimeStr());
        int call = bean.getIsCall();
        // 是否拨打电话（0-否 1-是）
        if (call == 0) {
            Tag=0;
            holder.mTvItemTelTel.setTextColor(mContext.getResources().getColor(R.color.text_color_F05049));
            holder.mIvItemTelIsCall.setVisibility(View.INVISIBLE);
        } else {
            Tag=1;
            holder.mTvItemTelTel.setTextColor(mContext.getResources().getColor(R.color.text_color_333));
            holder.mIvItemTelIsCall.setVisibility(View.VISIBLE);
        }
        holder.mTvItemTelCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.call(bean.getTel().replace(" ", ""), bean.getId());
                    SPUtils.put("shopId",bean.getShopId());
                    SPUtils.put("phone",bean.getTel());
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
        // 顾客意向(0-跟进，1-pass,2-成交,3-与销售无关
        holder.mTvItemTelIntention.setText(CommonUtils.getIntention(bean.getClientIntention()));
        //进店看房 隐藏转进店选项
        if (mType == Constants.SEE) {
            holder.mTvItemTelCome.setText("取消进店");
        }
        holder.mTvItemTelCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.come(bean.getId());
                }
            }
        });
        holder.mIvItemTelDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickItem(bean.getId());
                }
            }
        });
    }

    static class SeeHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.ll_item_tel)
        LinearLayout mLlItemTel;
        @BindView(R.id.iv_item_tel_detail)
        ImageView mIvItemTelDetail;
        @BindView(R.id.iv_item_tel_isCall)
        ImageView mIvItemTelIsCall;
        @BindView(R.id.tv_item_tel_add)
        TextView mTvItemTelAdd;

        SeeHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setListener(ClickListener listener) {
        mListener = listener;
    }

    private ClickListener mListener;

    public interface ClickListener {
        void call(String tel, int id);

        void come(int id);

        void clickItem(int id);

        void addBlack(String tel);
    }
}
