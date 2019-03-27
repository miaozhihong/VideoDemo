package com.jm.bean;
/**
 * 房型实体
 */
public class JmRoomType {
    /**
     * 内容
     */
    private String content;

    /**
     * 图片地址
     */
    private String url;

    public JmRoomType() {
    }

    public JmRoomType(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JmRoomManager{" +
                "content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
