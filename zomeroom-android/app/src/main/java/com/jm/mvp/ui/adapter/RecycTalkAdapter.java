package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.TalkJmRoomOriginBean;
import com.jm.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 回访数据item页面
 */
public class RecycTalkAdapter extends BaseRvAdapter<TalkJmRoomOriginBean.DataBean.ListBean, RecycTalkAdapter.TelHolder> {
    private List<TalkJmRoomOriginBean.DataBean.ListBean> mList;
    public static int color;
    public String mTag="";
    public RecycTalkAdapter(List<TalkJmRoomOriginBean.DataBean.ListBean> list, Context context,String tag) {
        super(list, context);
        mTag=tag;
    }

    @Override
    protected TelHolder getHolder(View view) {
        return new TelHolder(view);
    }

    @Override
    protected int getResId() {
        return R.layout.item_talk_admin;
    }

    @Override
    protected void convert(TelHolder holder, final TalkJmRoomOriginBean.DataBean.ListBean bean, final int position) {
        holder.mTvItemTelTel.setText(bean.getShopName().replace(" ", ""));
        String updateTime = (String) bean.getUpdateTime();
        String createTimes = (String) bean.getCreateTime();
        if (updateTime.equals(createTimes)){
            holder.mTvItemTelTime.setText((String) bean.getCreateTime());
        }else {
            holder.mTvItemTelTime.setText((String) bean.getUpdateTime());
        }
        holder.mIvItemTelIsCall.setVisibility(View.INVISIBLE);
        holder.mTvItemTelShop.setText(bean.getAreaName());
       if (mTag.equals(Constants.TALK)){
           holder.mTvItemTelIntention.setText(bean.getReturnTime()+"");
       }else {
           int cooperationIntention = bean.getCooperationIntention();
           //意向(0- 跟进，1-Pass，2-签约,
           if (cooperationIntention==0){
               holder.mTvItemTelIntention.setText("跟进");
           }else if (cooperationIntention==1){
               holder.mTvItemTelIntention.setText("pass");
           }else {
               holder.mTvItemTelIntention.setText("签约");
           }
       }
        holder.mTvItemTelCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // 打电话
                    mListener.call(bean.getId());

                }
            }
        });
       holder.mTvItemTelCallDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // 删除
                    mListener.delet(bean.getId(),position);
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
        @BindView(R.id.tv_item_tel_delet)
        TextView mTvItemTelCallDelet;
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

    public void setListener(ClickListener listener) {
        mListener = listener;
    }

    private ClickListener mListener;

    public interface ClickListener {
        void call(int id);

        void delet(int id,int position);

        void clickItem(int id);

        void addBlack(String tel);
    }
}
