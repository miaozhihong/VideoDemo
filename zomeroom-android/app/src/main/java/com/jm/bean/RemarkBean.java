package com.jm.bean;

import java.util.List;

/**
 * @author pc 张立男
 * @Description RemarkBean 备注
 * @date 2018/5/14 15:56
 * o(＞﹏＜)o
 */

public class RemarkBean {

    /**
     * data : [{"cId":17,"content":"备注1","createTime":1526281561182,"createTimeStr":"2018-05-14 15:06:01","creator":"","id":6,"isDel":0}]
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
         * cId : 17
         * content : 备注1
         * createTime : 1526281561182
         * createTimeStr : 2018-05-14 15:06:01
         * creator :
         * id : 6
         * isDel : 0
         */

        private int cId;
        private String content;
        private long createTime;
        private String createTimeStr;
        private String creator;
        private int id;
        private int isDel;

        public int getCId() {
            return cId;
        }

        public void setCId(int cId) {
            this.cId = cId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
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
    }
}
