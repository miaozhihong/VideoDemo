package com.jm.bean;

/**
 * 作者 Created by $miaozhihong on 2018/12/20 13:50
 * 页面功能:联系人实体
 */
public class JmBusinessManager {
    private String name;
    private String tel;


    public JmBusinessManager(){}
    public JmBusinessManager(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "JmBusinessManager{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
