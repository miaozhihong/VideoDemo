package com.jm.mvp.contract;

import com.jm.bean.UserBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

/**
 * 作者 Created by $miaozhihong on 2019/1/26 13:29
 * 页面功能:消息页面
 */
public class ChatFragmentContract {
    public interface View extends IView {
        /**
         * 无网络连接
         */
        void showNetError();

        /**
         * 请求成功后返回的数据
         * 需要自己添加泛型
         /**
         * 没有数据
         */
        void showEmpty();

        /**
         * 没有下一页
         */
        void showNotMore();

        /**
         * 刷新页面
         */
        void refresh();
    }
}
