package com.jm.bean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/20 13:26
 * 页面功能:回访模块add实体
 */
public class JmRoomOrigin implements Serializable {
    /**
     * 用户ID
     */
    private int id;

    /**
     * 用户ID
     */
    private int userId;

    /**
     * 分店名称
     */
    private String shopName;

    /**
     * 行政区域ID
     */
    private int areaId;

    /**
     * 地铁ID
     */
    private int  stationId;

    /**
     * 店与地铁距离
     */
    private String stationDistance;

    /**
     * 地址
     */
    private String address;

    /**
     * 地理经度
     */
    private String longitude;

    /**
     * 地理纬度
     */
    private String latitude;

    /**
     * 合作模式(0.直营店 1.平台管理店)
     */
    private int cooperationMode;

    /**
     * 总楼层
     */
    private int totalFloor;

    /**
     * 总房间数
     */
    private int totalRoom;

    /**
     * 意向合作楼层
     */
    private int cooperationFloor;

    /**
     * 意向合作房量
     */
    private int cooperationRoom;

    /**
     * 明窗
     */
    private int casement;

    /**
     * 楼道窗
     */
    private int corridor;

    /**
     * 报价
     */
    private String price;

    /**
     * 合作意向(0.跟进 1.PASS 2.签约)
     */
    private int cooperationIntention;

    /**
     * 物业外围环境照片
     */
    private String settingPhoto;

    /**
     * 公区照片
     */
    private String parkPhoto;

    /**
     * 房间照片
     */
    private String roomPhoto;
    /**
     * 回访时间
     */
    private String returnTimeStr;

    /**
     * 回拨  0-未回拨
     */
    private int isCall;

    /**
     * 签约时间
     */
    private String signingTimeStr;


    /**
     * 物业负责人
     */
    private List<JmBusinessManager> managerList;
    /**
     * 当前条数
     */
    private int pageNo;
    /**
     *显示条数
     */
    private int pageSize;
    /**
     *显示条数
     */
    private int type;

    /**
     * 备注
     */
    private List<JmRoomRemark> remarkList;
    /**
     * 时间
     */
    private String date;

    /**
     * 时间段 1 七天  2 十五天  3  三十天
     */
    private Integer time;

    /**
     * 按时间查询  回访时间
     */
    private String returnDateStr;

    /**
     * 房型  照片
     */
    private List<JmRoomType> typeList;

    public JmRoomOrigin() {}

    public JmRoomOrigin(int id, int userId, String shopName, int areaId, int stationId, String stationDistance, String address, String longitude, String latitude, int cooperationMode, int totalFloor, int totalRoom, int cooperationFloor, int cooperationRoom, int casement, int corridor, String price, int cooperationIntention, String settingPhoto, String parkPhoto, String roomPhoto, String returnTimeStr, int isCall, String signingTimeStr, List<JmBusinessManager> managerList, int pageNo, int pageSize, int type, List<JmRoomRemark> remarkList, String date, Integer time, String returnDateStr, List<JmRoomType> typeList) {
        this.id = id;
        this.userId = userId;
        this.shopName = shopName;
        this.areaId = areaId;
        this.stationId = stationId;
        this.stationDistance = stationDistance;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cooperationMode = cooperationMode;
        this.totalFloor = totalFloor;
        this.totalRoom = totalRoom;
        this.cooperationFloor = cooperationFloor;
        this.cooperationRoom = cooperationRoom;
        this.casement = casement;
        this.corridor = corridor;
        this.price = price;
        this.cooperationIntention = cooperationIntention;
        this.settingPhoto = settingPhoto;
        this.parkPhoto = parkPhoto;
        this.roomPhoto = roomPhoto;
        this.returnTimeStr = returnTimeStr;
        this.isCall = isCall;
        this.signingTimeStr = signingTimeStr;
        this.managerList = managerList;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.type = type;
        this.remarkList = remarkList;
        this.date = date;
        this.time = time;
        this.returnDateStr = returnDateStr;
        this.typeList = typeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationDistance() {
        return stationDistance;
    }

    public void setStationDistance(String stationDistance) {
        this.stationDistance = stationDistance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getCooperationMode() {
        return cooperationMode;
    }

    public void setCooperationMode(int cooperationMode) {
        this.cooperationMode = cooperationMode;
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

    public int getCooperationFloor() {
        return cooperationFloor;
    }

    public void setCooperationFloor(int cooperationFloor) {
        this.cooperationFloor = cooperationFloor;
    }

    public int getCooperationRoom() {
        return cooperationRoom;
    }

    public void setCooperationRoom(int cooperationRoom) {
        this.cooperationRoom = cooperationRoom;
    }

    public int getCasement() {
        return casement;
    }

    public void setCasement(int casement) {
        this.casement = casement;
    }

    public int getCorridor() {
        return corridor;
    }

    public void setCorridor(int corridor) {
        this.corridor = corridor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCooperationIntention() {
        return cooperationIntention;
    }

    public void setCooperationIntention(int cooperationIntention) {
        this.cooperationIntention = cooperationIntention;
    }

    public String getSettingPhoto() {
        return settingPhoto;
    }

    public void setSettingPhoto(String settingPhoto) {
        this.settingPhoto = settingPhoto;
    }

    public String getParkPhoto() {
        return parkPhoto;
    }

    public void setParkPhoto(String parkPhoto) {
        this.parkPhoto = parkPhoto;
    }

    public String getRoomPhoto() {
        return roomPhoto;
    }

    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    public String getReturnTimeStr() {
        return returnTimeStr;
    }

    public void setReturnTimeStr(String returnTimeStr) {
        this.returnTimeStr = returnTimeStr;
    }

    public int getIsCall() {
        return isCall;
    }

    public void setIsCall(int isCall) {
        this.isCall = isCall;
    }

    public String getSigningTimeStr() {
        return signingTimeStr;
    }

    public void setSigningTimeStr(String signingTimeStr) {
        this.signingTimeStr = signingTimeStr;
    }

    public List<JmBusinessManager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<JmBusinessManager> managerList) {
        this.managerList = managerList;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<JmRoomRemark> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<JmRoomRemark> remarkList) {
        this.remarkList = remarkList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getReturnDateStr() {
        return returnDateStr;
    }

    public void setReturnDateStr(String returnDateStr) {
        this.returnDateStr = returnDateStr;
    }

    public List<JmRoomType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<JmRoomType> typeList) {
        this.typeList = typeList;
    }

    @Override
    public String toString() {
        return "JmRoomOrigin{" +
                "id=" + id +
                ", userId=" + userId +
                ", shopName='" + shopName + '\'' +
                ", areaId=" + areaId +
                ", stationId=" + stationId +
                ", stationDistance='" + stationDistance + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", cooperationMode=" + cooperationMode +
                ", totalFloor=" + totalFloor +
                ", totalRoom=" + totalRoom +
                ", cooperationFloor=" + cooperationFloor +
                ", cooperationRoom=" + cooperationRoom +
                ", casement=" + casement +
                ", corridor=" + corridor +
                ", price='" + price + '\'' +
                ", cooperationIntention=" + cooperationIntention +
                ", settingPhoto='" + settingPhoto + '\'' +
                ", parkPhoto='" + parkPhoto + '\'' +
                ", roomPhoto='" + roomPhoto + '\'' +
                ", returnTimeStr='" + returnTimeStr + '\'' +
                ", isCall=" + isCall +
                ", signingTimeStr='" + signingTimeStr + '\'' +
                ", managerList=" + managerList +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", type=" + type +
                ", remarkList=" + remarkList +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", returnDateStr='" + returnDateStr + '\'' +
                ", typeList=" + typeList +
                '}';
    }
}
