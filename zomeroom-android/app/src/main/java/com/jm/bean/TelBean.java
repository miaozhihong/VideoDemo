package com.jm.bean;

/**
 * 作者 Created by $miaozhihong on 2018/12/25 11:49
 * 页面功能:电话存放实体
 */
public class TelBean {
    private String telName;
    private String tel;

    public TelBean(String telName, String tel) {
        this.telName = telName;
        this.tel = tel;
    }

    public String getTelName() {
        return telName;
    }

    public void setTelName(String telName) {
        this.telName = telName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "TelBean{" +
                "telName='" + telName + '\'' +
                ", tel=" + tel +
                '}';
    }
}
