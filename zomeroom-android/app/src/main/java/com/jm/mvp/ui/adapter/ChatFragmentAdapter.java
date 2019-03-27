package com.jm.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.UserBean;
import com.jm.mvp.base.ChatNoReadMsg;
import com.jm.mvp.base.Msg;

import org.w3c.dom.Text;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 13:42
 * 页面功能:聊天来消息adapter
 */
public class ChatFragmentAdapter extends BaseQuickAdapter<ChatNoReadMsg, BaseViewHolder> {
    public ChatFragmentAdapter(@Nullable List<ChatNoReadMsg> data) {
        super(R.layout.item_chat_fragment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChatNoReadMsg item) {
        helper.setText(R.id.tv_item_subway, item.getChatName());
        helper.setText(R.id.tv_state,"[文本消息]");
        final TextView tvShowNum = helper.getView(R.id.tv_show_num);
        if (item.getShownum().equals("0")){
            tvShowNum.setVisibility(View.GONE);
        }else {
            helper.setText(R.id.tv_show_num, item.getShownum());
        }
        final ImageView imageView = helper.getView(R.id.images);
        final TextView detelTalk = helper.getView(R.id.detelTalk);
        final TextView talkTop = helper.getView(R.id.talkTop);
        final RelativeLayout relativeLayoutel = helper.getView(R.id.relativeLayoutel);

        /**
         *item点击事件
         */
        relativeLayoutel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickItem(item.getChatName());
                }
            }
        });
        //删除会话
        detelTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.deletTalk();
                }
            }
        });
        //消息顶置
        talkTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.messageTop();
                }
            }
        });

    }
    public void setListener(ClickListener listener) {
        mListener = listener;
    }

    private ClickListener mListener;

    public interface ClickListener {
        void messageTop();//消息顶置
        void deletTalk();//删除会话
        void clickItem(String id);//点击事件
    }
}
