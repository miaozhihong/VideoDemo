package com.jm.bean;

/**
 * @author pc 张立男
 * @Description UpTelSearchBean 电话咨询的查询条件
 * @date 2018/5/11 10:12
 * o(＞﹏＜)o
 */

public class UpTelSearchBean {

    /**
     * mainType : 0
     * minorType :
     * isCall :
     * shopId :
     * clientIntention :
     * time :
     * tel :
     * pageNo : 1
     * pageSize : 10
     */

    private String mainType;
    private String minorType;
    private String isCall;
    private String shopId;
    private String clientIntention;
    private String time;
    private String tel;
    private String pageNo;
    private String pageSize;

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getMinorType() {
        return minorType;
    }

    public void setMinorType(String minorType) {
        this.minorType = minorType;
    }

    public String getIsCall() {
        return isCall;
    }

    public void setIsCall(String isCall) {
        this.isCall = isCall;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getClientIntention() {
        return clientIntention;
    }

    public void setClientIntention(String clientIntention) {
        this.clientIntention = clientIntention;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
