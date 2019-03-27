package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/18 11:03
 * 页面功能:
 */
public class AreaBean {

    /**
     * data : [{"code":null,"createTime":null,"id":1,"isDel":null,"name":"不限","updateTime":null},{"code":null,"createTime":null,"id":2,"isDel":null,"name":"丰台区","updateTime":null},{"code":null,"createTime":null,"id":3,"isDel":null,"name":"大兴区","updateTime":null},{"code":null,"createTime":null,"id":4,"isDel":null,"name":"密云区","updateTime":null},{"code":null,"createTime":null,"id":5,"isDel":null,"name":"平谷区","updateTime":null},{"code":null,"createTime":null,"id":6,"isDel":null,"name":"延庆区","updateTime":null},{"code":null,"createTime":null,"id":7,"isDel":null,"name":"怀柔区","updateTime":null},{"code":null,"createTime":null,"id":8,"isDel":null,"name":"房山区","updateTime":null},{"code":null,"createTime":null,"id":9,"isDel":null,"name":"昌平区","updateTime":null},{"code":null,"createTime":null,"id":10,"isDel":null,"name":"朝阳区","updateTime":null},{"code":null,"createTime":null,"id":11,"isDel":null,"name":"海淀区","updateTime":null},{"code":null,"createTime":null,"id":12,"isDel":null,"name":"石景山区","updateTime":null},{"code":null,"createTime":null,"id":13,"isDel":null,"name":"西城区","updateTime":null},{"code":null,"createTime":null,"id":14,"isDel":null,"name":"通州区","updateTime":null},{"code":null,"createTime":null,"id":15,"isDel":null,"name":"门头沟区","updateTime":null},{"code":null,"createTime":null,"id":16,"isDel":null,"name":"顺义区","updateTime":null},{"code":null,"createTime":null,"id":17,"isDel":null,"name":"东城区","updateTime":null}]
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
         * code : null
         * createTime : null
         * id : 1
         * isDel : null
         * name : 不限
         * updateTime : null
         */

        private Object code;
        private Object createTime;
        private int id;
        private Object isDel;
        private String name;
        private Object updateTime;

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getIsDel() {
            return isDel;
        }

        public void setIsDel(Object isDel) {
            this.isDel = isDel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}
