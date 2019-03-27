package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/21 18:19
 * 页面功能:加载回访数据
 */
public class TalkJmRoomOriginBean {

    /**
     * data : {"list":[{"address":null,"areaId":3,"areaName":"大兴区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":2,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":null,"roomPhoto":null,"settingPhoto":null,"shopName":"放心广百股份","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":3,"areaName":"大兴区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":3,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 19:22:00","roomPhoto":null,"settingPhoto":null,"shopName":"放心广百股份","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":3,"areaName":"大兴区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":4,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 19:22:00","roomPhoto":null,"settingPhoto":null,"shopName":"放心广百股份","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":2,"areaName":"丰台区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":5,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 19:35:00","roomPhoto":null,"settingPhoto":null,"shopName":"郭德纲半个多","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":6,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":7,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":8,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":9,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":10,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":11,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"}],"pageNo":1,"pageSize":10,"total":12,"totalPages":2}
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
         * list : [{"address":null,"areaId":3,"areaName":"大兴区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":2,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":null,"roomPhoto":null,"settingPhoto":null,"shopName":"放心广百股份","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":3,"areaName":"大兴区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":3,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 19:22:00","roomPhoto":null,"settingPhoto":null,"shopName":"放心广百股份","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":3,"areaName":"大兴区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":4,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 19:22:00","roomPhoto":null,"settingPhoto":null,"shopName":"放心广百股份","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":2,"areaName":"丰台区","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":5,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 19:35:00","roomPhoto":null,"settingPhoto":null,"shopName":"郭德纲半个多","signingTime":null,"stationDistance":null,"stationId":5,"stationName":"天安门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":6,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":7,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":8,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":9,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":10,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"},{"address":null,"areaId":1,"areaName":"不限","casement":null,"cooperationFloor":null,"cooperationIntention":0,"cooperationMode":null,"cooperationRoom":null,"corridor":null,"createTime":null,"createUserId":0,"id":11,"isCall":0,"isDel":null,"latitude":null,"longitude":null,"parkPhoto":null,"price":null,"returnTime":"2018-12-20 20:00:00","roomPhoto":null,"settingPhoto":null,"shopName":"唱歌规范化","signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":null,"totalRoom":null,"updateTime":null,"updateUserId":0,"userId":17,"userName":"test3"}]
         * pageNo : 1
         * pageSize : 10
         * total : 12
         * totalPages : 2
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
             * address : null
             * areaId : 3
             * areaName : 大兴区
             * casement : null
             * cooperationFloor : null
             * cooperationIntention : 0
             * cooperationMode : null
             * cooperationRoom : null
             * corridor : null
             * createTime : null
             * createUserId : 0
             * id : 2
             * isCall : 0
             * isDel : null
             * latitude : null
             * longitude : null
             * parkPhoto : null
             * price : null
             * returnTime : null
             * roomPhoto : null
             * settingPhoto : null
             * shopName : 放心广百股份
             * signingTime : null
             * stationDistance : null
             * stationId : 5
             * stationName : 天安门
             * totalFloor : null
             * totalRoom : null
             * updateTime : null
             * updateUserId : 0
             * userId : 17
             * userName : test3
             */

            private Object address;
            private int areaId;
            private String areaName;
            private Object casement;
            private Object cooperationFloor;
            private int cooperationIntention;
            private Object cooperationMode;
            private Object cooperationRoom;
            private Object corridor;
            private Object createTime;
            private int createUserId;
            private int id;
            private int isCall;
            private Object isDel;
            private Object latitude;
            private Object longitude;
            private Object parkPhoto;
            private Object price;
            private Object returnTime;
            private Object roomPhoto;
            private Object settingPhoto;
            private String shopName;
            private Object signingTime;
            private Object stationDistance;
            private int stationId;
            private String stationName;
            private Object totalFloor;
            private Object totalRoom;
            private Object updateTime;
            private int updateUserId;
            private int userId;
            private String userName;

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public Object getCasement() {
                return casement;
            }

            public void setCasement(Object casement) {
                this.casement = casement;
            }

            public Object getCooperationFloor() {
                return cooperationFloor;
            }

            public void setCooperationFloor(Object cooperationFloor) {
                this.cooperationFloor = cooperationFloor;
            }

            public int getCooperationIntention() {
                return cooperationIntention;
            }

            public void setCooperationIntention(int cooperationIntention) {
                this.cooperationIntention = cooperationIntention;
            }

            public Object getCooperationMode() {
                return cooperationMode;
            }

            public void setCooperationMode(Object cooperationMode) {
                this.cooperationMode = cooperationMode;
            }

            public Object getCooperationRoom() {
                return cooperationRoom;
            }

            public void setCooperationRoom(Object cooperationRoom) {
                this.cooperationRoom = cooperationRoom;
            }

            public Object getCorridor() {
                return corridor;
            }

            public void setCorridor(Object corridor) {
                this.corridor = corridor;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
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

            public Object getIsDel() {
                return isDel;
            }

            public void setIsDel(Object isDel) {
                this.isDel = isDel;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
                this.latitude = latitude;
            }

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getParkPhoto() {
                return parkPhoto;
            }

            public void setParkPhoto(Object parkPhoto) {
                this.parkPhoto = parkPhoto;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public Object getReturnTime() {
                return returnTime;
            }

            public void setReturnTime(Object returnTime) {
                this.returnTime = returnTime;
            }

            public Object getRoomPhoto() {
                return roomPhoto;
            }

            public void setRoomPhoto(Object roomPhoto) {
                this.roomPhoto = roomPhoto;
            }

            public Object getSettingPhoto() {
                return settingPhoto;
            }

            public void setSettingPhoto(Object settingPhoto) {
                this.settingPhoto = settingPhoto;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public Object getSigningTime() {
                return signingTime;
            }

            public void setSigningTime(Object signingTime) {
                this.signingTime = signingTime;
            }

            public Object getStationDistance() {
                return stationDistance;
            }

            public void setStationDistance(Object stationDistance) {
                this.stationDistance = stationDistance;
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

            public Object getTotalFloor() {
                return totalFloor;
            }

            public void setTotalFloor(Object totalFloor) {
                this.totalFloor = totalFloor;
            }

            public Object getTotalRoom() {
                return totalRoom;
            }

            public void setTotalRoom(Object totalRoom) {
                this.totalRoom = totalRoom;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public int getUpdateUserId() {
                return updateUserId;
            }

            public void setUpdateUserId(int updateUserId) {
                this.updateUserId = updateUserId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
