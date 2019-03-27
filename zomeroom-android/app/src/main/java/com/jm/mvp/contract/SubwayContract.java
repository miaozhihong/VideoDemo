package com.jm.mvp.contract;

import com.jm.bean.SubwayBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

public interface SubwayContract {

    interface View extends IView {
        /**
         * 无网络连接
         */
        void showNetError();

        /**
         * 请求成功后返回的数据
         * 需要自己添加泛型
         *
         * @param list 返回的数据
         */
        void showData(List<SubwayBean.DataBean> list);

        void showEmpty();
    }
}