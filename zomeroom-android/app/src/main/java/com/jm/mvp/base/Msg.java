package com.jm.mvp.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 18:18
 * 页面功能:聊天数据
 */
public class Msg implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemTypes;
    private String content;
    private String time;
    private String imge;

    public Msg() {
    }

    public Msg(int itemTypes, String content, String time, String imge) {
        this.itemTypes = itemTypes;
        this.content = content;
        this.time = time;
        this.imge = imge;
    }

    public void setItemType(int itemType) {
        this.itemTypes = itemTypes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImge() {
        return imge;
    }

    public void setImge(String imge) {
        this.imge = imge;
    }

    public Msg(int itemTypes) {
        this.itemTypes = itemTypes;
    }

    @Override
    public int getItemType() {
        return itemTypes;
    }
}
