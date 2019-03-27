package com.jm.bean;

/**
 * Created by pc on 2018/2/13.
 */

public class CallInfo {
    public String name;
    public String number; // 号码
    public long date;     // 日期
    public int type;      // 类型：1来电、2去电、3未接
    public long duration;

    public CallInfo(String name, String number, long date, int type, long duration) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.type = type;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CallInfo{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", duration=" + duration +
                '}';
    }
}
