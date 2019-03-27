package cn.com.yktour.network.bean;

import java.io.Serializable;

/**
 * Created by sky on 2017/11/20.
 * description: HTTP服务器返回结果（Data 为空）
 * changed:
 */

public class HttpResult implements Serializable {
    public int flag;
    public String message;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }
}
