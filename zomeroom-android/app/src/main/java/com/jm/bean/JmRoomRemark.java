package com.jm.bean;

/**
 * 作者 Created by $miaozhihong on 2018/12/20 14:25
 * 页面功能:备注
 */
public class JmRoomRemark {
    /**
     * 内容
     */
    private String content;

    /**
     * 备注类型(0.自定义 1.进店回访 2.电话回访)
     */
    private int type;

    public JmRoomRemark() {}

    public JmRoomRemark(String content) {
        this.content = content;
    }

    public JmRoomRemark(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JmRoomRemark{" +
                "content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
