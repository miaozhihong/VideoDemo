package com.jm.mvp.contract;

import com.jm.bean.BlackListBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

/**
 * Created by pc on 2018/5/9.
 */

public interface BlackListContract {
    public interface View extends IView {
        /**
         * 无网络连接
         */
        void showNetError();

        /**
         * 请求成功  返回数据
         *
         * @param list
         */
        void display(List<BlackListBean.DataBean.ListBean> list);

        /**
         * 请求成功  无数据
         */
        void showEmpty();

        /**
         * 没有更多了
         */
        void showNotMore();

        /**
         * 请求成功后刷新页面
         */
        void refresh();
    }
}
