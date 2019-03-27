package com.jm.bean;

import java.util.List;

/**
 * @author pc 张立男
 * @Description ShopBean 筛选商店列表
 * @date 2018/5/14 17:25
 * o(＞﹏＜)o
 */
public class ShopBean {

    /**
     * data : [{"createTime":1525655287000,"id":1,"isDel":0,"latitude":"0","longitude":"0","shopName":"国贸店","updateTime":1525655287000},{"createTime":1525655287000,"id":2,"isDel":0,"latitude":"0","longitude":"0","shopName":"王府井店","updateTime":1525655287000}]
     * flag : 20000
     * message : 操作成功
     */

    private int flag;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
}
