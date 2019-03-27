package com.jm.bean;

import java.util.List;

/**
 * @author pc 张立男
 * @Description TelListBean 电话咨询列表
 * @date 2018/5/11 13:49
 * o(＞﹏＜)o
 */

public class TelListBean {

    /**
     * data : {"list":[{"appointmentEnterTimeStr":"2018-05-07 09:08","channelId":1,"channelName":"58","clientIntention":0,"consultReturnTimeStr":"2018-05-07 09:08","createTimeStr":"2018-05-07 09:08","id":3,"isCall":0,"isEnterShop":0,"isWechat":0,"pushShopId":2,"pushShopName":null,"shopId":1,"shopName":null,"stationId":2,"stationName":null,"tel":"18912345678"},{"appointmentEnterTimeStr":"2018-05-07 09:08","channelId":2,"channelName":"去哪","clientIntention":0,"consultReturnTimeStr":"2018-05-07 09:08","createTimeStr":"2018-05-07 09:08","id":2,"isCall":1,"isEnterShop":0,"isWechat":0,"pushShopId":2,"pushShopName":null,"shopId":1,"shopName":null,"stationId":1,"stationName":null,"tel":"14312345678"},{"appointmentEnterTimeStr":null,"channelId":0,"channelName":"32","clientIntention":0,"consultReturnTimeStr":null,"createTimeStr":null,"id":4,"isCall":0,"isEnterShop":0,"isWechat":0,"pushShopId":0,"pushShopName":null,"shopId":2,"shopName":null,"stationId":0,"stationName":null,"tel":"13212345678"},{"appointmentEnterTimeStr":null,"channelId":0,"channelName":"32","clientIntention":0,"consultReturnTimeStr":null,"createTimeStr":null,"id":5,"isCall":0,"isEnterShop":0,"isWechat":0,"pushShopId":0,"pushShopName":null,"shopId":2,"shopName":null,"stationId":0,"stationName":null,"tel":"13212345678"}],"pageNo":1,"pageSize":10,"total":4,"totalPages":1}
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
         * list : [{"appointmentEnterTimeStr":"2018-05-07 09:08","channelId":1,"channelName":"58","clientIntention":0,"consultReturnTimeStr":"2018-05-07 09:08","createTimeStr":"2018-05-07 09:08","id":3,"isCall":0,"isEnterShop":0,"isWechat":0,"pushShopId":2,"pushShopName":null,"shopId":1,"shopName":null,"stationId":2,"stationName":null,"tel":"18912345678"},{"appointmentEnterTimeStr":"2018-05-07 09:08","channelId":2,"channelName":"去哪","clientIntention":0,"consultReturnTimeStr":"2018-05-07 09:08","createTimeStr":"2018-05-07 09:08","id":2,"isCall":1,"isEnterShop":0,"isWechat":0,"pushShopId":2,"pushShopName":null,"shopId":1,"shopName":null,"stationId":1,"stationName":null,"tel":"14312345678"},{"appointmentEnterTimeStr":null,"channelId":0,"channelName":"32","clientIntention":0,"consultReturnTimeStr":null,"createTimeStr":null,"id":4,"isCall":0,"isEnterShop":0,"isWechat":0,"pushShopId":0,"pushShopName":null,"shopId":2,"shopName":null,"stationId":0,"stationName":null,"tel":"13212345678"},{"appointmentEnterTimeStr":null,"channelId":0,"channelName":"32","clientIntention":0,"consultReturnTimeStr":null,"createTimeStr":null,"id":5,"isCall":0,"isEnterShop":0,"isWechat":0,"pushShopId":0,"pushShopName":null,"shopId":2,"shopName":null,"stationId":0,"stationName":null,"tel":"13212345678"}]
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
             * appointmentEnterTimeStr : 2018-05-07 09:08
             * channelId : 1
             * channelName : 58
             * clientIntention : 0
             * consultReturnTimeStr : 2018-05-07 09:08
             * createTimeStr : 2018-05-07 09:08
             * id : 3
             * isCall : 0
             * isEnterShop : 0
             * isWechat : 0
             * pushShopId : 2
             * pushShopName : null
             * shopId : 1
             * shopName : null
             * stationId : 2
             * stationName : null
             * tel : 18912345678
             */

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
    }
}
