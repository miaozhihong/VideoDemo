package com.jm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者 Created by $miaozhihong on 2018/11/9 11:48
 * 页面功能:
 */

@Entity
public class Titles {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String pkgname;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String time;
    @NotNull
    private String pushtype;
    @NotNull
    private String phone;
    @NotNull
    private String isPushShop;
    @NotNull
    private String sender_letter;
    @NotNull
    private String addressee_letter;
    @Generated(hash = 667985242)
    public Titles(Long id, @NotNull String pkgname, @NotNull String title,
            @NotNull String content, @NotNull String time, @NotNull String pushtype,
            @NotNull String phone, @NotNull String isPushShop,
            @NotNull String sender_letter, @NotNull String addressee_letter) {
        this.id = id;
        this.pkgname = pkgname;
        this.title = title;
        this.content = content;
        this.time = time;
        this.pushtype = pushtype;
        this.phone = phone;
        this.isPushShop = isPushShop;
        this.sender_letter = sender_letter;
        this.addressee_letter = addressee_letter;
    }
    @Generated(hash = 2082159104)
    public Titles() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPkgname() {
        return this.pkgname;
    }
    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getPushtype() {
        return this.pushtype;
    }
    public void setPushtype(String pushtype) {
        this.pushtype = pushtype;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getIsPushShop() {
        return this.isPushShop;
    }
    public void setIsPushShop(String isPushShop) {
        this.isPushShop = isPushShop;
    }
    public String getSender_letter() {
        return this.sender_letter;
    }
    public void setSender_letter(String sender_letter) {
        this.sender_letter = sender_letter;
    }
    public String getAddressee_letter() {
        return this.addressee_letter;
    }
    public void setAddressee_letter(String addressee_letter) {
        this.addressee_letter = addressee_letter;
    }
}
