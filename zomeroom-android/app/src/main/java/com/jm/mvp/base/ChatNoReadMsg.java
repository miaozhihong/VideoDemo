package com.jm.mvp.base;

/**
 * 作者 Created by $miaozhihong on 2019/1/28 19:22
 * 页面功能:未读消息实体
 */
public class ChatNoReadMsg {
    private String chatName;
    private String imge;
    private String content;
    private String time;
    private String shownum;

    public ChatNoReadMsg() {}

    public ChatNoReadMsg(String chatName, String imge, String content, String time, String shownum) {
        this.chatName = chatName;
        this.imge = imge;
        this.content = content;
        this.time = time;
        this.shownum = shownum;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getImge() {
        return imge;
    }

    public void setImge(String imge) {
        this.imge = imge;
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

    public String getShownum() {
        return shownum;
    }

    public void setShownum(String shownum) {
        this.shownum = shownum;
    }
}
