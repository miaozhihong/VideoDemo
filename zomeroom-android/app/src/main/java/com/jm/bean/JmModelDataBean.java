package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/10 11:14
 * 页面功能:内务管理返回数据实体
 */
public class JmModelDataBean {

    /**
     * data : {"list":[{"area":"B区","articleVOList":null,"cName":"B套","createTime":"2019-01-10 12:48:45","id":2,"number":2,"price":20,"roomNum":null,"shopName":"方庄1店","status":0,"uploadTime":null,"url":null,"userName":"test"},{"area":"A区","articleVOList":null,"cName":"A套","createTime":"2019-01-10 12:50:20","id":3,"number":2,"price":140,"roomNum":null,"shopName":"方庄2店","status":0,"uploadTime":null,"url":null,"userName":"test"},{"area":"A区","articleVOList":null,"cName":"A套","createTime":"2019-01-10 10:26:54","id":1,"number":1,"price":10,"roomNum":"100","shopName":"测试","status":0,"uploadTime":null,"url":null,"userName":"test"}],"pageNo":1,"pageSize":11,"total":3,"totalPages":1}
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
         * list : [{"area":"B区","articleVOList":null,"cName":"B套","createTime":"2019-01-10 12:48:45","id":2,"number":2,"price":20,"roomNum":null,"shopName":"方庄1店","status":0,"uploadTime":null,"url":null,"userName":"test"},{"area":"A区","articleVOList":null,"cName":"A套","createTime":"2019-01-10 12:50:20","id":3,"number":2,"price":140,"roomNum":null,"shopName":"方庄2店","status":0,"uploadTime":null,"url":null,"userName":"test"},{"area":"A区","articleVOList":null,"cName":"A套","createTime":"2019-01-10 10:26:54","id":1,"number":1,"price":10,"roomNum":"100","shopName":"测试","status":0,"uploadTime":null,"url":null,"userName":"test"}]
         * pageNo : 1
         * pageSize : 11
         * total : 3
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
             * area : B区
             * articleVOList : null
             * cName : B套
             * createTime : 2019-01-10 12:48:45
             * id : 2
             * number : 2
             * price : 20
             * roomNum : null
             * shopName : 方庄1店
             * status : 0
             * uploadTime : null
             * url : null
             * userName : test
             */

            private String area;
            private Object articleVOList;
            private String cName;
            private String createTime;
            private int id;
            private int number;
            private int price;
            private Object roomNum;
            private String shopName;
            private int status;
            private Object uploadTime;
            private Object url;
            private String userName;

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public Object getArticleVOList() {
                return articleVOList;
            }

            public void setArticleVOList(Object articleVOList) {
                this.articleVOList = articleVOList;
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

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public Object getRoomNum() {
                return roomNum;
            }

            public void setRoomNum(Object roomNum) {
                this.roomNum = roomNum;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getUploadTime() {
                return uploadTime;
            }

            public void setUploadTime(Object uploadTime) {
                this.uploadTime = uploadTime;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
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
