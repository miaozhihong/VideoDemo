package com.jm.bean;

import java.io.Serializable;

/**
 * @author pc 张立男
 * @Description LoginBean 登录
 * @date 2018/5/29 14:02
 * o(＞﹏＜)o
 */

public class LoginBean implements Serializable {

    /**
     * data : {"loginName":"admin","name":"admin","tel":"13278900987"，"type":"0/1/2"}
     * flag : 20000
     * message : 操作成功
     */

    private DataBean data;
    private int flag;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * loginName : admin
         * name : admin
         * tel : 13278900987
         */

        private String loginName;
        private String name;
        private String tel;
        private int type;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
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
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
