package com.jm.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.mvp.base.Msg;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 18:17
 * 页面功能:聊天左右布局
 */
public class ChatAdapter extends BaseMultiItemQuickAdapter<Msg, BaseViewHolder> {
    public ChatAdapter(List<Msg> data) {
        super(data);
        addItemType(Msg.TEXT, R.layout.item_chat_left);
        addItemType(Msg.IMG, R.layout.item_chat_right);
    }

    @Override
    protected void convert(BaseViewHolder helper, Msg item) {
        switch (helper.getItemViewType()) {
            case Msg.TEXT:
                helper.setText(R.id.tv_left_time, item.getTime());
              //  helper.setText(R.id.tv_left_time, item.getContent());
                helper.setText(R.id.tv_msg_left, item.getContent());
                break;
            case Msg.IMG:
                helper.setText(R.id.tv_right_time, item.getTime());
                //  helper.setText(R.id.tv_left_time, item.getContent());
                helper.setText(R.id.tv_msg_right, item.getContent());
                break;
        }
    }
}
