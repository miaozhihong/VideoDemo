package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/10 19:37
 * 页面功能:内务系统详情页
 */
public class JmModelDataListBean {

    /**
     * data : {"area":null,"articleVOList":[{"aId":2,"articleName":"床单","lastUrl":null,"mUrlId":2,"number":1,"time":"2019-01-10","url":"/statics/modelImg/20190111/Screenshot_20190111-200425[1].png"},{"aId":3,"articleName":"桌子","lastUrl":null,"mUrlId":null,"number":1,"time":"2019-01-11","url":null},{"aId":1,"articleName":"梳子","lastUrl":"/statics/modelImg/20190111/Screenshot_20190111-200425[1].png","mUrlId":1,"number":1,"time":"2019-01-11","url":"/statics/modelImg/20190111/Screenshot_20190111-200425[1].png"}],"cName":"A套","createTime":null,"id":1,"number":null,"price":null,"roomNum":"100","shopName":null,"status":null,"uploadTime":"2019-01-12","url":"/statics/modelVideo/20190111/video_1547187630681.mp4","userName":null,"vId":1}
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
         * area : null
         * articleVOList : [{"aId":2,"articleName":"床单","lastUrl":null,"mUrlId":2,"number":1,"time":"2019-01-10","url":"/statics/modelImg/20190111/Screenshot_20190111-200425[1].png"},{"aId":3,"articleName":"桌子","lastUrl":null,"mUrlId":null,"number":1,"time":"2019-01-11","url":null},{"aId":1,"articleName":"梳子","lastUrl":"/statics/modelImg/20190111/Screenshot_20190111-200425[1].png","mUrlId":1,"number":1,"time":"2019-01-11","url":"/statics/modelImg/20190111/Screenshot_20190111-200425[1].png"}]
         * cName : A套
         * createTime : null
         * id : 1
         * number : null
         * price : null
         * roomNum : 100
         * shopName : null
         * status : null
         * uploadTime : 2019-01-12
         * url : /statics/modelVideo/20190111/video_1547187630681.mp4
         * userName : null
         * vId : 1
         */

        private String area;
        private String cName;
        private String createTime;
        private int id;
        private String number;
        private String price;
        private String roomNum;
        private String shopName;
        private String status;
        private String uploadTime;
        private String url;
        private String userName;
        private int vId;
        private List<ArticleVOListBean> articleVOList;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCName() {
            return cName;
        }

        public void setCName(String cName) {
            this.cName = cName;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getVId() {
            return vId;
        }

        public void setVId(int vId) {
            this.vId = vId;
        }

        public List<ArticleVOListBean> getArticleVOList() {
            return articleVOList;
        }

        public void setArticleVOList(List<ArticleVOListBean> articleVOList) {
            this.articleVOList = articleVOList;
        }

        public static class ArticleVOListBean {
            /**
             * aId : 2
             * articleName : 床单
             * lastUrl : null
             * mUrlId : 2
             * number : 1
             * time : 2019-01-10
             * url : /statics/modelImg/20190111/Screenshot_20190111-200425[1].png
             */

            private int aId;
            private String articleName;
            private String lastUrl;
            private int mUrlId;
            private int number;
            private String time;
            private String url;

            public int getAId() {
                return aId;
            }

            public void setAId(int aId) {
                this.aId = aId;
            }

            public String getArticleName() {
                return articleName;
            }

            public void setArticleName(String articleName) {
                this.articleName = articleName;
            }

            public String getLastUrl() {
                return lastUrl;
            }

            public void setLastUrl(String lastUrl) {
                this.lastUrl = lastUrl;
            }

            public int getMUrlId() {
                return mUrlId;
            }

            public void setMUrlId(int mUrlId) {
                this.mUrlId = mUrlId;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
