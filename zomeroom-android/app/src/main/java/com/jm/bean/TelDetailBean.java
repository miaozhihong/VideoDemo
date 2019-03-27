package com.jm.bean;

import java.util.List;

/**
 * @author pc 张立男
 * @Description TelDetailBean 电话咨询详情
 * @date 2018/5/11 16:00
 * o(＞﹏＜)o
 */

public class TelDetailBean {

    /**
     * data : {"channelList":[{"channelName":"58","id":1,"isDel":0},{"channelName":"去哪","id":2,"isDel":0},{"channelName":"wechat","id":3,"isDel":0}],"shopList":[{"createTime":1525655287000,"id":1,"isDel":0,"latitude":"0","longitude":"0","shopName":"国贸店","updateTime":1525655287000},{"createTime":1525655287000,"id":2,"isDel":0,"latitude":"0","longitude":"0","shopName":"王府井店","updateTime":1525655287000}],"detailInfo":{"appointmentEnterTimeStr":"2018-06-07 09:08","channelId":2,"channelName":"去哪","clientIntention":1,"consultReturnTimeStr":"2018-05-07 09:08","createTimeStr":"2018-05-07 09:08","id":1,"isCall":1,"isEnterShop":1,"isWechat":0,"pushShopId":2,"pushShopName":"王府井店","shopId":3,"shopName":"东单店","stationId":1,"stationName":"天安门","tel":"13212345678"},"remarkList":[{"cId":1,"content":"电话咨询备注测试","createTime":1525655287000,"createTimeStr":"2018-05-07 09:08:07","creator":"admin","id":1,"isDel":0},{"cId":1,"content":"太贵了","createTime":1525655287000,"createTimeStr":"2018-05-07 09:08:07","creator":"admin","id":2,"isDel":0}],"pushList":[{"createTime":1525655287000,"id":3,"isDel":0,"latitude":"0","longitude":"0","shopName":"东单店","updateTime":1525655287000}]}
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
         * channelList : [{"channelName":"58","id":1,"isDel":0},{"channelName":"去哪","id":2,"isDel":0},{"channelName":"wechat","id":3,"isDel":0}]
         * shopList : [{"createTime":1525655287000,"id":1,"isDel":0,"latitude":"0","longitude":"0","shopName":"国贸店","updateTime":1525655287000},{"createTime":1525655287000,"id":2,"isDel":0,"latitude":"0","longitude":"0","shopName":"王府井店","updateTime":1525655287000}]
         * detailInfo : {"appointmentEnterTimeStr":"2018-06-07 09:08","channelId":2,"channelName":"去哪","clientIntention":1,"consultReturnTimeStr":"2018-05-07 09:08","createTimeStr":"2018-05-07 09:08","id":1,"isCall":1,"isEnterShop":1,"isWechat":0,"pushShopId":2,"pushShopName":"王府井店","shopId":3,"shopName":"东单店","stationId":1,"stationName":"天安门","tel":"13212345678"}
         * remarkList : [{"cId":1,"content":"电话咨询备注测试","createTime":1525655287000,"createTimeStr":"2018-05-07 09:08:07","creator":"admin","id":1,"isDel":0},{"cId":1,"content":"太贵了","createTime":1525655287000,"createTimeStr":"2018-05-07 09:08:07","creator":"admin","id":2,"isDel":0}]
         * pushList : [{"createTime":1525655287000,"id":3,"isDel":0,"latitude":"0","longitude":"0","shopName":"东单店","updateTime":1525655287000}]
         */

        private DetailInfoBean detailInfo;
        private List<ChannelListBean> channelList;
        private List<ShopListBean> shopList;
        private List<RemarkListBean> remarkList;
        private List<PushListBean> pushList;

        public DetailInfoBean getDetailInfo() {
            return detailInfo;
        }

        public void setDetailInfo(DetailInfoBean detailInfo) {
            this.detailInfo = detailInfo;
        }

        public List<ChannelListBean> getChannelList() {
            return channelList;
        }

        public void setChannelList(List<ChannelListBean> channelList) {
            this.channelList = channelList;
        }

        public List<ShopListBean> getShopList() {
            return shopList;
        }

        public void setShopList(List<ShopListBean> shopList) {
            this.shopList = shopList;
        }

        public List<RemarkListBean> getRemarkList() {
            return remarkList;
        }

        public void setRemarkList(List<RemarkListBean> remarkList) {
            this.remarkList = remarkList;
        }

        public List<PushListBean> getPushList() {
            return pushList;
        }

        public void setPushList(List<PushListBean> pushList) {
            this.pushList = pushList;
        }

        public static class DetailInfoBean {
            /**
             * appointmentEnterTimeStr : 2018-06-07 09:08
             * channelId : 2
             * channelName : 去哪
             * clientIntention : 1
             * consultReturnTimeStr : 2018-05-07 09:08
             * createTimeStr : 2018-05-07 09:08
             * id : 1
             * isCall : 1
             * isEnterShop : 1
             * isWechat : 0
             * pushShopId : 2
             * pushShopName : 王府井店
             * shopId : 3
             * shopName : 东单店
             * stationId : 1
             * stationName : 天安门
             * tel : 13212345678
             */
            private String loginName;
            private String appointmentEnterTimeStr;
            private int channelId;
            private String channelName;
            private int clientIntention;
            private String consultReturnTimeStr;
            private String createTimeStr;
            private int id;
            private int isCall;
            private int isEnterShop;
            private int isWechat;
            private int pushShopId;
            private String pushShopName;
            private int shopId;
            private String shopName;
            private int stationId;
            private String stationName;
            private String tel;
            private String roomNum;
            public String getAppointmentEnterTimeStr() {
                return appointmentEnterTimeStr;
            }
            public String getRoomNum() {
                return roomNum;
            }

            public void setRoomNum(String roomNum) {
                this.roomNum = roomNum;
            }
            public void setAppointmentEnterTimeStr(String appointmentEnterTimeStr) {
                this.appointmentEnterTimeStr = appointmentEnterTimeStr;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public String getChannelName() {
                return channelName;
            }

            public void setChannelName(String channelName) {
                this.channelName = channelName;
            }

            public int getClientIntention() {
                return clientIntention;
            }

            public void setClientIntention(int clientIntention) {
                this.clientIntention = clientIntention;
            }

            public String getConsultReturnTimeStr() {
                return consultReturnTimeStr;
            }

            public void setConsultReturnTimeStr(String consultReturnTimeStr) {
                this.consultReturnTimeStr = consultReturnTimeStr;
            }

            public String getCreateTimeStr() {
                return createTimeStr;
            }

            public void setCreateTimeStr(String createTimeStr) {
                this.createTimeStr = createTimeStr;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsCall() {
                return isCall;
            }

            public void setIsCall(int isCall) {
                this.isCall = isCall;
            }

            public int getIsEnterShop() {
                return isEnterShop;
            }

            public void setIsEnterShop(int isEnterShop) {
                this.isEnterShop = isEnterShop;
            }

            public int getIsWechat() {
                return isWechat;
            }

            public void setIsWechat(int isWechat) {
                this.isWechat = isWechat;
            }

            public int getPushShopId() {
                return pushShopId;
            }

            public void setPushShopId(int pushShopId) {
                this.pushShopId = pushShopId;
            }

            public String getPushShopName() {
                return pushShopName;
            }

            public void setPushShopName(String pushShopName) {
                this.pushShopName = pushShopName;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public int getStationId() {
                return stationId;
            }

            public void setStationId(int stationId) {
                this.stationId = stationId;
            }

            public String getStationName() {
                return stationName;
            }

            public void setStationName(String stationName) {
                this.stationName = stationName;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }
        }

        public static class ChannelListBean {
            /**
             * channelName : 58
             * id : 1
             * isDel : 0
             */

            private String channelName;
            private int id;
            private int isDel;

            public String getChannelName() {
                return channelName;
            }

            public void setChannelName(String channelName) {
                this.channelName = channelName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsDel() {
                return isDel;
            }

            public void setIsDel(int isDel) {
                this.isDel = isDel;
            }
        }

        public static class ShopListBean {
            /**
             * createTime : 1525655287000
             * id : 1
             * isDel : 0
             * latitude : 0
             * longitude : 0
             * shopName : 国贸店
             * updateTime : 1525655287000
             */

            private long createTime;
            private int id;
            private int isDel;
            private String latitude;
            private String longitude;
            private String shopName;
            private long updateTime;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsDel() {
                return isDel;
            }

            public void setIsDel(int isDel) {
                this.isDel = isDel;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class RemarkListBean {
            /**
             * cId : 1
             * content : 电话咨询备注测试
             * createTime : 1525655287000
             * createTimeStr : 2018-05-07 09:08:07
             * creator : admin
             * id : 1
             * isDel : 0
             */

            private int cId;
            private String content;
            private long createTime;
            private String createTimeStr;
            private String creator;
            private int id;
            private int isDel;

            public int getCId() {
                return cId;
            }

            public void setCId(int cId) {
                this.cId = cId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getCreateTimeStr() {
                return createTimeStr;
            }

            public void setCreateTimeStr(String createTimeStr) {
                this.createTimeStr = createTimeStr;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsDel() {
                return isDel;
            }

            public void setIsDel(int isDel) {
                this.isDel = isDel;
            }
        }

        public static class PushListBean {
            /**
             * createTime : 1525655287000
             * id : 3
             * isDel : 0
             * latitude : 0
             * longitude : 0
             * shopName : 东单店
             * updateTime : 1525655287000
             */

            private long createTime;
            private int id;
            private int isDel;
            private String latitude;
            private String longitude;
            private String shopName;
            private long updateTime;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsDel() {
                return isDel;
            }

            public void setIsDel(int isDel) {
                this.isDel = isDel;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
