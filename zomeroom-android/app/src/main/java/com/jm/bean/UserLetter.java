package com.jm.bean;

/**
 * 作者 Created by $miaozhihong on 2018/11/23 14:50
 * 页面功能:
 */
public class UserLetter {
    private String phone;
    private String sender;
    private String addressee;

    public UserLetter(String phone, String sender, String addressee) {
        this.phone = phone;
        this.sender = sender;
        this.addressee = addressee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    @Override
    public String toString() {
        return "UserLetter{" +
                "phone='" + phone + '\'' +
                ", sender='" + sender + '\'' +
                ", addressee='" + addressee + '\'' +
                '}';
    }
}
