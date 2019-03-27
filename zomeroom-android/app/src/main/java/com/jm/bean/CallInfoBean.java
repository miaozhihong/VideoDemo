package com.jm.bean;

/**
 * @author pc 张立男
 * @Description CallInfoBean 获取电话信息的bean类
 * @date 2018/2/19 21:23
 * o(＞﹏＜)o
 */

public class CallInfoBean {
    private String number; //号码
    private long date; // 日期
    private int type; // 类型：来电、去电、未接
    private long duration;//持续时间 秒

    public CallInfoBean(String number, long date, int type, long duration) {
        this.number = number;
        this.date = date;
        this.type = type;
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CallInfoBean{" +
                "number='" + number + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", duration=" + duration +
                '}';
    }
}
