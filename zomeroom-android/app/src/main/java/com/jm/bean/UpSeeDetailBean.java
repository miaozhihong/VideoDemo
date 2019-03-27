package com.jm.bean;

/**
 * @author pc 张立男
 * @Description UpTelDetailBean 进店详情上传数据
 * @date 2018/5/11 16:09
 * o(＞﹏＜)o
 */

public class UpSeeDetailBean {

    /**
     * appointmentEnterTimeStr : 2018-05-07 09:08
     * channelId : 2
     * channelName : 去哪
     * clientIntention : 0
     * consultReturnTimeStr : 2018-05-07 09:08
     * createTimeStr : 2018-05-07 09:08
     * id : 2
     * isCall : 1
     * isEnterShop : 0
     * isWechat : 0
     * pushShopId : 2
     * pushShopName : 王府井店
     * shopId : 1
     * shopName : 国贸店
     * stationId : 1
     * stationName : 天安门
     * tel : 14312345678
     */

    private String appointmentEnterTimeStr;
    private int channelId;
    private String channelName;
    private int clientIntention;
    private String enterReturnTimeStr;
    private String createTimeStr;
    private int id;
    private int isCall;
    private int isEnterShop;
    private int isAttention;
    private int pushShopId;
    private String pushShopName;
    private int shopId;
    private String shopName;
    private int stationId;
    private String stationName;
    private String tel;
    private String roomNum;
    private String transactionTime;

    public UpSeeDetailBean() {
    }

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

    public String getEnterReturnTimeStr() {
        return enterReturnTimeStr;
    }

    public void setEnterReturnTimeStr(String enterReturnTimeStr) {
        this.enterReturnTimeStr = enterReturnTimeStr;
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

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
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

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }
}
