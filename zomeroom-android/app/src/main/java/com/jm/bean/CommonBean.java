package com.jm.bean;

/**
 * @author pc 张立男
 * @Description CommonBean 通用的数据类
 * @date 2018/5/9 17:58
 * o(＞﹏＜)o
 */

public class CommonBean {

    /**
     * data : null
     * flag : 20000
     * message : 操作成功
     */

    private Object data;
    private int flag;
    private String message;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
}
