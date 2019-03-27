package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/12 18:24
 * 页面功能:调拨返回数据实体
 */
public class ShowRequestDetailBean {
    /**
     * data : [{"combinationId":2,"combinationName":"B套","createTime":"2019-01-12 17:55:55","id":1,"isAdminAssent":0,"isDel":0,"isShopAssent":0,"mId":2,"receiveShopId":2,"receiveShopName":"方庄2店","receiveUserId":3,"receiveUserName":"郭霞霞","sendShopId":1,"sendShopName":"方庄1店","sendUserId":2,"sendUserName":"test","updateTime":"2019-01-12 17:55:55"},{"combinationId":1,"combinationName":"A套","createTime":"2019-01-12 18:12:36","id":2,"isAdminAssent":0,"isDel":0,"isShopAssent":0,"mId":3,"receiveShopId":2,"receiveShopName":"方庄2店","receiveUserId":3,"receiveUserName":"郭霞霞","sendShopId":2,"sendShopName":"方庄2店","sendUserId":2,"sendUserName":"test","updateTime":"2019-01-12 18:12:36"},{"combinationId":1,"combinationName":"A套","createTime":"2019-01-12 18:13:44","id":3,"isAdminAssent":0,"isDel":0,"isShopAssent":0,"mId":3,"receiveShopId":2,"receiveShopName":"方庄2店","receiveUserId":3,"receiveUserName":"郭霞霞","sendShopId":2,"sendShopName":"方庄2店","sendUserId":2,"sendUserName":"test","updateTime":"2019-01-12 18:13:44"}]
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
         * combinationId : 2
         * combinationName : B套
         * createTime : 2019-01-12 17:55:55
         * id : 1
         * isAdminAssent : 0
         * isDel : 0
         * isShopAssent : 0
         * mId : 2
         * receiveShopId : 2
         * receiveShopName : 方庄2店
         * receiveUserId : 3
         * receiveUserName : 郭霞霞
         * sendShopId : 1
         * sendShopName : 方庄1店
         * sendUserId : 2
         * sendUserName : test
         * updateTime : 2019-01-12 17:55:55
         */

        private int combinationId;
        private String combinationName;
        private String createTime;
        private int id;
        private int isAdminAssent;
        private int isDel;
        private int isShopAssent;
        private int mId;
        private int receiveShopId;
        private String receiveShopName;
        private int receiveUserId;
        private String receiveUserName;
        private int sendShopId;
        private String sendShopName;
        private int sendUserId;
        private String sendUserName;
        private String updateTime;

        public int getCombinationId() {
            return combinationId;
        }

        public void setCombinationId(int combinationId) {
            this.combinationId = combinationId;
        }

        public String getCombinationName() {
            return combinationName;
        }

        public void setCombinationName(String combinationName) {
            this.combinationName = combinationName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsAdminAssent() {
            return isAdminAssent;
        }

        public void setIsAdminAssent(int isAdminAssent) {
            this.isAdminAssent = isAdminAssent;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public int getIsShopAssent() {
            return isShopAssent;
        }

        public void setIsShopAssent(int isShopAssent) {
            this.isShopAssent = isShopAssent;
        }

        public int getMId() {
            return mId;
        }

        public void setMId(int mId) {
            this.mId = mId;
        }

        public int getReceiveShopId() {
            return receiveShopId;
        }

        public void setReceiveShopId(int receiveShopId) {
            this.receiveShopId = receiveShopId;
        }

        public String getReceiveShopName() {
            return receiveShopName;
        }

        public void setReceiveShopName(String receiveShopName) {
            this.receiveShopName = receiveShopName;
        }

        public int getReceiveUserId() {
            return receiveUserId;
        }

        public void setReceiveUserId(int receiveUserId) {
            this.receiveUserId = receiveUserId;
        }

        public String getReceiveUserName() {
            return receiveUserName;
        }

        public void setReceiveUserName(String receiveUserName) {
            this.receiveUserName = receiveUserName;
        }

        public int getSendShopId() {
            return sendShopId;
        }

        public void setSendShopId(int sendShopId) {
            this.sendShopId = sendShopId;
        }

        public String getSendShopName() {
            return sendShopName;
        }

        public void setSendShopName(String sendShopName) {
            this.sendShopName = sendShopName;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public String getSendUserName() {
            return sendUserName;
        }

        public void setSendUserName(String sendUserName) {
            this.sendUserName = sendUserName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
//    /**
//     *  id自增
//     */
//    private int id;
//
//    /**
//     * 调拨数据Id
//     */
//    private int mId;
//
//    /**
//     * 组合Id
//     */
//    private int combinationId;
//
//    /**
//     * 组合名称
//     */
//    private String combinationName;
//
//    /**
//     * 调拨人Id
//     */
//    private int sendUserId;
//
//    /**
//     * 调拨人姓名
//     */
//    private String sendUserName;
//
//    /**
//     * 调拨店Id
//     */
//    private int sendShopId;
//
//    /**
//     * 调拨店名称
//     */
//    private String sendShopName;
//
//    /**
//     * 接收调拨人Id
//     */
//    private int receiveUserId;
//
//    /**
//     * 接收调拨人姓名
//     */
//    private String receiveUserName;
//
//    /**
//     * 接收调拨店Id
//     */
//    private int receiveShopId;
//
//    /**
//     * 接收调拨店名称
//     */
//    private String receiveShopName;
//
//    /**
//     * 接收店是否同意
//     */
//    private int isShopAssent;
//
//    /**
//     * admin是否同意
//     */
//    private int isAdminAssent;
//
//    /**
//     * 创建时间
//     */
//    private String createTime;
//
//    /**
//     * 修改时间
//     */
//    private String updateTime;
//
//    /**
//     * 逻辑删除
//     */
//    private int isDel;


}
