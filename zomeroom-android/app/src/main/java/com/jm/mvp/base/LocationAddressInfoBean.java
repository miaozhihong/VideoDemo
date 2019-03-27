package com.jm.mvp.base;

/**
 * 地图poi检索地址实体
 */
public class LocationAddressInfoBean {
    private String context;
    private String title;
    private Double longitude;
    private Double latitude;

    public LocationAddressInfoBean(String context, String title, Double longitude, Double latitude) {
        this.context = context;
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
