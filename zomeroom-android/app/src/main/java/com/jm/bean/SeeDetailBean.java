package com.jm.bean;

import java.util.List;

/**
 * Created by pc on 2018/5/15.
 */

public class SeeDetailBean {

    /**
     * data : {"channelList":[{"channelName":"58","id":1,"isDel":0},{"channelName":"去哪","id":2,"isDel":0},{"channelName":"wechat","id":3,"isDel":0}],"shopList":[{"createTime":1525655287000,"id":1,"isDel":0,"latitude":"0","longitude":"0","shopName":"国贸店","updateTime":1525655287000},{"createTime":1525655287000,"id":2,"isDel":0,"latitude":"0","longitude":"0","shopName":"王府井店","updateTime":1525655287000}],"detailInfo":{"appointmentEnterTimeStr":null,"channelId":2,"channelName":"去哪","clientIntention":1,"createTime":null,"createTimeStr":"2018-05-08 09:52","enterReturnTimeStr":null,"id":2,"isAttention":0,"isCall":0,"pushShopId":0,"pushShopName":null,"roomNum":"","shopId":2,"shopName":"王府井店","stationId":1,"stationName":"天安门","tel":"13212345678"},"pushList":[{"createTime":1525655287000,"id":3,"isDel":0,"latitude":"0","longitude":"0","shopName":"东单店","updateTime":1525655287000}]}
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
         * detailInfo : {"appointmentEnterTimeStr":null,"channelId":2,"channelName":"去哪","clientIntention":1,"createTime":null,"createTimeStr":"2018-05-08 09:52","enterReturnTimeStr":null,"id":2,"isAttention":0,"isCall":0,"pushShopId":0,"pushShopName":null,"roomNum":"","shopId":2,"shopName":"王府井店","stationId":1,"stationName":"天安门","tel":"13212345678"}
         * pushList : [{"createTime":1525655287000,"id":3,"isDel":0,"latitude":"0","longitude":"0","shopName":"东单店","updateTime":1525655287000}]
         */

        private DetailInfoBean detailInfo;
        private List<ChannelListBean> channelList;
        private List<ShopListBean> shopList;
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

        public List<PushListBean> getPushList() {
            return pushList;
        }

        public void setPushList(List<PushListBean> pushList) {
            this.pushList = pushList;
        }

        public static class DetailInfoBean {
            /**
             * appointmentEnterTimeStr : null
             * channelId : 2
             * channelName : 去哪
             * clientIntention : 1
             * createTime : null
             * createTimeStr : 2018-05-08 09:52
             * enterReturnTimeStr : null
             * id : 2
             * isAttention : 0
             * isCall : 0
             * pushShopId : 0
             * pushShopName : null
             * roomNum :
             * shopId : 2
             * shopName : 王府井店
             * stationId : 1
             * stationName : 天安门
             * tel : 13212345678
             */

            private String appointmentEnterTimeStr;
            private int channelId;
            private String channelName;
            private int clientIntention;
            private String createTimeStr;
            private String enterReturnTimeStr;
            private int id;
            private int isAttention;
            private int isCall;
            private int pushShopId;
            private String pushShopName;
            private String roomNum;
            private int shopId;
            private String shopName;
            private int stationId;
            private String stationName;
            private String tel;

            public String getAppointmentEnterTimeStr() {
                return appointmentEnterTimeStr;
            }

            public void setAppointmentEnterTimeStr(String appointmentEnterTimeStr) {
                this.appointmentEnterTimeStr = appointmentEnterTimeStr;
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

            public String getCreateTimeStr() {
                return createTimeStr;
            }

            public void setCreateTimeStr(String createTimeStr) {
                this.createTimeStr = createTimeStr;
            }

            public String getEnterReturnTimeStr() {
                return enterReturnTimeStr;
            }

            public void setEnterReturnTimeStr(String enterReturnTimeStr) {
                this.enterReturnTimeStr = enterReturnTimeStr;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsAttention() {
                return isAttention;
            }

            public void setIsAttention(int isAttention) {
                this.isAttention = isAttention;
            }

            public int getIsCall() {
                return isCall;
            }

            public void setIsCall(int isCall) {
                this.isCall = isCall;
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

            public String getRoomNum() {
                return roomNum;
            }

            public void setRoomNum(String roomNum) {
                this.roomNum = roomNum;
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
