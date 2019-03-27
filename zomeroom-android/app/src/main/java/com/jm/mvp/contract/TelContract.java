package com.jm.mvp.contract;

import com.jm.bean.ShopBean;
import com.jm.bean.TelListBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

public interface TelContract {

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
        void showData(List<TelListBean.DataBean.ListBean> list);

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

        /**
         * 筛选店铺列表
         *
         * @param data
         */
        void showShopList(List<ShopBean.DataBean> data);

        void showToShopError(String message);

        void showMessage(String msg);
    }
}