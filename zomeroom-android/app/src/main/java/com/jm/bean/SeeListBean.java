package com.jm.bean;

import java.util.List;

/**
 * @author pc 张立男
 * @Description SeeListBean 进店列表
 * @date 2018/5/14 18:53
 * o(＞﹏＜)o
 */

public class SeeListBean {

    /**
     * data : {"list":[{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":1,"createTime":null,"createTimeStr":"2018-05-14 16:13","enterReturnTimeStr":null,"id":4,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":1,"shopName":"国贸店","stationId":null,"stationName":null,"tel":"17610307122"},{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":0,"createTime":null,"createTimeStr":"2018-05-14 16:02","enterReturnTimeStr":null,"id":3,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":2,"shopName":"王府井店","stationId":null,"stationName":null,"tel":"17610607128"},{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":1,"createTime":null,"createTimeStr":"2018-05-08 09:52","enterReturnTimeStr":null,"id":2,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":2,"shopName":"王府井店","stationId":null,"stationName":null,"tel":"13212345678"},{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":1,"createTime":null,"createTimeStr":"2018-05-07 10:07","enterReturnTimeStr":null,"id":1,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":2,"shopName":"王府井店","stationId":null,"stationName":null,"tel":"13212345678"}],"pageNo":1,"pageSize":10,"total":4,"totalPages":1}
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
         * list : [{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":1,"createTime":null,"createTimeStr":"2018-05-14 16:13","enterReturnTimeStr":null,"id":4,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":1,"shopName":"国贸店","stationId":null,"stationName":null,"tel":"17610307122"},{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":0,"createTime":null,"createTimeStr":"2018-05-14 16:02","enterReturnTimeStr":null,"id":3,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":2,"shopName":"王府井店","stationId":null,"stationName":null,"tel":"17610607128"},{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":1,"createTime":null,"createTimeStr":"2018-05-08 09:52","enterReturnTimeStr":null,"id":2,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":2,"shopName":"王府井店","stationId":null,"stationName":null,"tel":"13212345678"},{"appointmentEnterTimeStr":null,"channelId":null,"channelName":null,"clientIntention":1,"createTime":null,"createTimeStr":"2018-05-07 10:07","enterReturnTimeStr":null,"id":1,"isAttention":null,"isCall":0,"pushShopId":null,"pushShopName":null,"roomNum":null,"shopId":2,"shopName":"王府井店","stationId":null,"stationName":null,"tel":"13212345678"}]
         * pageNo : 1
         * pageSize : 10
         * total : 4
         * totalPages : 1
         */

        private int pageNo;
        private int pageSize;
        private int total;
        private int totalPages;
        private List<ListBean> list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * appointmentEnterTimeStr : null
             * channelId : null
             * channelName : null
             * clientIntention : 1
             * createTime : null
             * createTimeStr : 2018-05-14 16:13
             * enterReturnTimeStr : null
             * id : 4
             * isAttention : null
             * isCall : 0
             * pushShopId : null
             * pushShopName : null
             * roomNum : null
             * shopId : 1
             * shopName : 国贸店
             * stationId : null
             * stationName : null
             * tel : 17610307122
             */

            private String appointmentEnterTimeStr;
            private int channelId;
            private String channelName;
            private int clientIntention;
            private String createTimeStr;
            private String enterReturnTimeStr;
            private int id;
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
    }
}
