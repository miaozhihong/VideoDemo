package com.jm.bean;

import java.util.List;

/**
 * @author pc 张立男
 * @Description BlackListBean 黑名单
 * @date 2018/5/9 17:09
 * o(＞﹏＜)o
 */

public class BlackListBean {

    /**
     * data : {"list":[{"createTime":0,"id":1,"isDel":0,"remark":"","tel":"13212345123","updateTime":1525855197000,"userId":1}],"pageNo":1,"pageSize":1,"total":2,"totalPages":2}
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
         * list : [{"createTime":0,"id":1,"isDel":0,"remark":"","tel":"13212345123","updateTime":1525855197000,"userId":1}]
         * pageNo : 1
         * pageSize : 1
         * total : 2
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
             * createTime : 0
             * id : 1
             * isDel : 0
             * remark :
             * tel : 13212345123
             * updateTime : 1525855197000
             * userId : 1
             */

            private String id;
            private String remark;
            private String tel;
            private int userId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
