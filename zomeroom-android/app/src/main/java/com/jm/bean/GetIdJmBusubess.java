package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/22 11:32
 * 页面功能:fragment数据
 */
public class GetIdJmBusubess {

    /**
     * data : {"address":"吃刚吃过","areaId":1,"areaName":"不限","casement":1,"cooperationFloor":0,"cooperationIntention":0,"cooperationMode":1,"cooperationRoom":0,"corridor":1,"countInt":null,"createTime":"2018-12-18 10:15:00","createUserId":0,"followInt":null,"id":7,"isCall":0,"isDel":0,"latitude":null,"longitude":null,"managerList":[{"createTime":"2018-12-20 20:02:02","id":15,"name":"刚吃过","rId":7,"tel":"现场发挥","updateTime":"2018-12-20 20:02:02"}],"parkPhoto":"\"/statics/fileImg/1545307294522.jpg\",","passInt":null,"price":"恩","remarkList":[{"content":"吃不饱捶捶背","createTime":"2018-12-20 20:02:02","id":8,"rId":7,"type":0,"updateTime":"2018-12-20 20:02:02"}],"returnTime":"2018-12-20 20:00:00","roomPhoto":"\"/statics/fileImg/1545307299526.jpg\",","settingPhoto":"\"/statics/fileImg/1545307289209.jpg\",","shopName":"唱歌规范化","signingInt":null,"signingTime":null,"stationDistance":null,"stationId":7,"stationName":"宣武门","totalFloor":1,"totalRoom":0,"updateTime":"2018-12-18 10:15:00","updateUserId":0,"userId":17,"userName":"test3"}
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
         * address : 吃刚吃过
         * areaId : 1
         * areaName : 不限
         * casement : 1
         * cooperationFloor : 0
         * cooperationIntention : 0
         * cooperationMode : 1
         * cooperationRoom : 0
         * corridor : 1
         * countInt : null
         * createTime : 2018-12-18 10:15:00
         * createUserId : 0
         * followInt : null
         * id : 7
         * isCall : 0
         * isDel : 0
         * latitude : null
         * longitude : null
         * managerList : [{"createTime":"2018-12-20 20:02:02","id":15,"name":"刚吃过","rId":7,"tel":"现场发挥","updateTime":"2018-12-20 20:02:02"}]
         * parkPhoto : "/statics/fileImg/1545307294522.jpg",
         * passInt : null
         * price : 恩
         * remarkList : [{"content":"吃不饱捶捶背","createTime":"2018-12-20 20:02:02","id":8,"rId":7,"type":0,"updateTime":"2018-12-20 20:02:02"}]
         * returnTime : 2018-12-20 20:00:00
         * roomPhoto : "/statics/fileImg/1545307299526.jpg",
         * settingPhoto : "/statics/fileImg/1545307289209.jpg",
         * shopName : 唱歌规范化
         * signingInt : null
         * signingTime : null
         * stationDistance : null
         * stationId : 7
         * stationName : 宣武门
         * totalFloor : 1
         * totalRoom : 0
         * updateTime : 2018-12-18 10:15:00
         * updateUserId : 0
         * userId : 17
         * userName : test3
         */

        private String address;
        private int areaId;
        private String areaName;
        private int casement;
        private int cooperationFloor;
        private int cooperationIntention;
        private int cooperationMode;
        private int cooperationRoom;
        private int corridor;
        private Object countInt;
        private String createTime;
        private int createUserId;
        private Object followInt;
        private int id;
        private int isCall;
        private int isDel;
        private Object latitude;
        private Object longitude;
        private String parkPhoto;
        private Object passInt;
        private String price;
        private String returnTime;
        private String roomPhoto;
        private String settingPhoto;
        private String shopName;
        private Object signingInt;
        private Object signingTime;
        private Object stationDistance;
        private int stationId;
        private String stationName;
        private int totalFloor;
        private int totalRoom;
        private String updateTime;
        private int updateUserId;
        private int userId;
        private String userName;
        private List<ManagerListBean> managerList;
        private List<RemarkListBean> remarkList;
        private List<JmRoomType> typeList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
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

        public int getCasement() {
            return casement;
        }

        public void setCasement(int casement) {
            this.casement = casement;
        }

        public int getCooperationFloor() {
            return cooperationFloor;
        }

        public void setCooperationFloor(int cooperationFloor) {
            this.cooperationFloor = cooperationFloor;
        }

        public int getCooperationIntention() {
            return cooperationIntention;
        }

        public void setCooperationIntention(int cooperationIntention) {
            this.cooperationIntention = cooperationIntention;
        }

        public int getCooperationMode() {
            return cooperationMode;
        }

        public void setCooperationMode(int cooperationMode) {
            this.cooperationMode = cooperationMode;
        }

        public int getCooperationRoom() {
            return cooperationRoom;
        }

        public void setCooperationRoom(int cooperationRoom) {
            this.cooperationRoom = cooperationRoom;
        }

        public int getCorridor() {
            return corridor;
        }

        public void setCorridor(int corridor) {
            this.corridor = corridor;
        }

        public Object getCountInt() {
            return countInt;
        }

        public void setCountInt(Object countInt) {
            this.countInt = countInt;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public Object getFollowInt() {
            return followInt;
        }

        public void setFollowInt(Object followInt) {
            this.followInt = followInt;
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

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
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

        public String getParkPhoto() {
            return parkPhoto;
        }

        public void setParkPhoto(String parkPhoto) {
            this.parkPhoto = parkPhoto;
        }

        public Object getPassInt() {
            return passInt;
        }

        public void setPassInt(Object passInt) {
            this.passInt = passInt;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(String returnTime) {
            this.returnTime = returnTime;
        }

        public String getRoomPhoto() {
            return roomPhoto;
        }

        public void setRoomPhoto(String roomPhoto) {
            this.roomPhoto = roomPhoto;
        }

        public String getSettingPhoto() {
            return settingPhoto;
        }

        public void setSettingPhoto(String settingPhoto) {
            this.settingPhoto = settingPhoto;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getSigningInt() {
            return signingInt;
        }

        public void setSigningInt(Object signingInt) {
            this.signingInt = signingInt;
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

        public int getTotalFloor() {
            return totalFloor;
        }

        public void setTotalFloor(int totalFloor) {
            this.totalFloor = totalFloor;
        }

        public int getTotalRoom() {
            return totalRoom;
        }

        public void setTotalRoom(int totalRoom) {
            this.totalRoom = totalRoom;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
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

        public List<ManagerListBean> getManagerList() {
            return managerList;
        }

        public void setManagerList(List<ManagerListBean> managerList) {
            this.managerList = managerList;
        }

        public List<RemarkListBean> getRemarkList() {
            return remarkList;
        }

        public void setRemarkList(List<RemarkListBean> remarkList) {
            this.remarkList = remarkList;
        }

        public List<JmRoomType> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<JmRoomType> typeList) {
            this.typeList = typeList;
        }

        public static class ManagerListBean {
            /**
             * createTime : 2018-12-20 20:02:02
             * id : 15
             * name : 刚吃过
             * rId : 7
             * tel : 现场发挥
             * updateTime : 2018-12-20 20:02:02
             */

            private String createTime;
            private int id;
            private String name;
            private int rId;
            private String tel;
            private String updateTime;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRId() {
                return rId;
            }

            public void setRId(int rId) {
                this.rId = rId;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class RemarkListBean {
            /**
             * content : 吃不饱捶捶背
             * createTime : 2018-12-20 20:02:02
             * id : 8
             * rId : 7
             * type : 0
             * updateTime : 2018-12-20 20:02:02
             */

            private String content;
            private String createTime;
            private int id;
            private int rId;
            private int type;
            private String updateTime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public int getRId() {
                return rId;
            }

            public void setRId(int rId) {
                this.rId = rId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
