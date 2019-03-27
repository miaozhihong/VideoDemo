package com.jm.bean;

/**miaozhihong
 * 内务管理查询实体
 */
public class ModelDataDTOBean {
    /**
     * 页数
     */
    private int pageNo;
    /**
     * 条数
     */
    private int pageSize;
    private int startNo;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 分店id
     */
    private String shopId;
    /**
     * 地区
     */
    private String area;
    /**
     * 时间
     */
    private String date;
    /**
     * 店名
     */
    private String shopName;

    private String type;
    /**
     * 使用类型
     */
    private String modelType;
    public ModelDataDTOBean() {
    }

    public ModelDataDTOBean(int pageNo, int pageSize, int startNo, String userId, String shopId, String area, String date, String shopName, String type, String modelType) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.startNo = startNo;
        this.userId = userId;
        this.shopId = shopId;
        this.area = area;
        this.date = date;
        this.shopName = shopName;
        this.type = type;
        this.modelType = modelType;
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

    public int getStartNo() {
        return startNo;
    }

    public void setStartNo(int startNo) {
        this.startNo = startNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
}
