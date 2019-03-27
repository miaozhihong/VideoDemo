package com.jm.mvp.contract;

import com.jm.bean.RemarkBean;
import com.jm.bean.TelDetailBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

/**
 * @author pc 张立男
 * @Description TelDetailContract 电话详情
 * @date 2018/5/11 16:12
 * o(＞﹏＜)o
 */
public interface TelDetailContract {

    interface View extends IView {
        /**
         * 无网络连接
         */
        void showNetError();

        /**
         * 请求成功后返回的数据
         * 需要自己添加泛型
         */
        void showData(TelDetailBean.DataBean data);

        /**
         * 更新数据成功
         */
        void refresh();

        /**
         * 备注列表
         *
         * @param list
         */
        void showRemarkList(List<RemarkBean.DataBean> list);

        /**
         * 添加备注成功 刷新备注列表
         */
        void refreshRemark();
    }
}