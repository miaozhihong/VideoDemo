package com.jm.bean;

import java.io.Serializable;

/**
 * @author pc 张立男
 * @Description BlackTelBean 黑名单的bean
 * @date 2018/2/23 19:08
 * o(＞﹏＜)o
 */

public class BlackTelBean implements Serializable {
    private String tel;
    private String reason;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
