package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/10 14:44
 * 页面功能:用户实体
 */
public class UserBean {


    /**
     * data : [{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":2,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"test","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":3,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"郭霞霞","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":4,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"翟琳","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":6,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"苗志芳","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":7,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"时爽","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":8,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"景霞","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":10,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"候飞龙","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":12,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"候飞龙","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":13,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"薛俊峰","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":14,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"魏玉玲","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null},{"deviceToken":null,"disabled":null,"email":null,"flag":null,"id":15,"isDel":null,"isRecording":null,"isStatus":null,"loginName":null,"loginType":null,"msg":null,"name":"冯明明","newTime":null,"oldTime":null,"pageNo":null,"pageSize":null,"password":null,"qq":null,"registerTime":null,"remark":null,"salt":null,"sex":null,"startNo":null,"tel":null,"updateTime":null}]
     * flag : 20000
     * message : 操作成功
     */

    private int flag;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * deviceToken : null
         * disabled : null
         * email : null
         * flag : null
         * id : 2
         * isDel : null
         * isRecording : null
         * isStatus : null
         * loginName : null
         * loginType : null
         * msg : null
         * name : test
         * newTime : null
         * oldTime : null
         * pageNo : null
         * pageSize : null
         * password : null
         * qq : null
         * registerTime : null
         * remark : null
         * salt : null
         * sex : null
         * startNo : null
         * tel : null
         * updateTime : null
         */

        private Object deviceToken;
        private Object disabled;
        private Object email;
        private Object flag;
        private int id;
        private Object isDel;
        private Object isRecording;
        private Object isStatus;
        private Object loginName;
        private Object loginType;
        private Object msg;
        private String name;
        private Object newTime;
        private Object oldTime;
        private Object pageNo;
        private Object pageSize;
        private Object password;
        private Object qq;
        private Object registerTime;
        private Object remark;
        private Object salt;
        private Object sex;
        private Object startNo;
        private Object tel;
        private Object updateTime;

        public Object getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(Object deviceToken) {
            this.deviceToken = deviceToken;
        }

        public Object getDisabled() {
            return disabled;
        }

        public void setDisabled(Object disabled) {
            this.disabled = disabled;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getFlag() {
            return flag;
        }

        public void setFlag(Object flag) {
            this.flag = flag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getIsDel() {
            return isDel;
        }

        public void setIsDel(Object isDel) {
            this.isDel = isDel;
        }

        public Object getIsRecording() {
            return isRecording;
        }

        public void setIsRecording(Object isRecording) {
            this.isRecording = isRecording;
        }

        public Object getIsStatus() {
            return isStatus;
        }

        public void setIsStatus(Object isStatus) {
            this.isStatus = isStatus;
        }

        public Object getLoginName() {
            return loginName;
        }

        public void setLoginName(Object loginName) {
            this.loginName = loginName;
        }

        public Object getLoginType() {
            return loginType;
        }

        public void setLoginType(Object loginType) {
            this.loginType = loginType;
        }

        public Object getMsg() {
            return msg;
        }

        public void setMsg(Object msg) {
            this.msg = msg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getNewTime() {
            return newTime;
        }

        public void setNewTime(Object newTime) {
            this.newTime = newTime;
        }

        public Object getOldTime() {
            return oldTime;
        }

        public void setOldTime(Object oldTime) {
            this.oldTime = oldTime;
        }

        public Object getPageNo() {
            return pageNo;
        }

        public void setPageNo(Object pageNo) {
            this.pageNo = pageNo;
        }

        public Object getPageSize() {
            return pageSize;
        }

        public void setPageSize(Object pageSize) {
            this.pageSize = pageSize;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(Object registerTime) {
            this.registerTime = registerTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getSalt() {
            return salt;
        }

        public void setSalt(Object salt) {
            this.salt = salt;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getStartNo() {
            return startNo;
        }

        public void setStartNo(Object startNo) {
            this.startNo = startNo;
        }

        public Object getTel() {
            return tel;
        }

        public void setTel(Object tel) {
            this.tel = tel;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}
