package com.jm.mvp.contract;

import cn.com.yktour.network.base.IView;

/**
 * @author pc 张立男
 * @Description LoginContract 登录的约束类
 * @date 2018/2/19 20:03
 * o(＞﹏＜)o
 */

public interface LoginContract {
    interface View extends IView {
        /**
         * 登录成功
         */
        void loginSuccess();
        /**
         * 网络连接问题
         */
        void showNetError();

        /**
         * 同步通话记录
         */
        void addTelSuccess();
    }
}
