package com.jm.mvp.contract;

import com.jm.bean.RemarkBean;
import com.jm.bean.SeeDetailBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

public interface SeeDetailContract {

    interface View extends IView {
        /**
         * 无网络连接
         */
        void showNetError();

        /**
         * 请求成功后返回的数据
         * 需要自己添加泛型
         *
         * @param data 返回的数据
         */
        void showData(SeeDetailBean.DataBean data);

        /**
         * 更新数据成功
         */
        void refresh();

        /**
         * 添加备注成功 刷新备注列表
         */
        void refreshRemark();

        /**
         * 备注列表
         *
         * @param list
         */
        void showRemarkList(List<RemarkBean.DataBean> list);
    }
}