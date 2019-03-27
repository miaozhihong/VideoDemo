package com.jm.mvp.contract;

import cn.com.yktour.network.base.IView;

/**
 * @author pc 张立男
 * @Description SplashContract 引导页面
 * @date 2018/6/23 15:36
 * o(＞﹏＜)o
 */

public interface SplashContract {
    public interface SplashView extends IView {
        /**
         * 登录状态未过期
         */
        void checkSuccess();

        /**
         * 同步通讯录信息
         */
        void addTelSuccess();
    }
}
