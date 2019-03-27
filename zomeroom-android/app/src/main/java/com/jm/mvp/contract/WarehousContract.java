package com.jm.mvp.contract;

import com.jm.bean.JmModelDataBean;
import com.jm.bean.ShopBean;
import com.jm.bean.ShowRequestDetailBean;
import com.jm.bean.UserBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

/**
 * 作者 Created by $miaozhihong on 2019/1/15 11:09
 * 页面功能:
 */
public class WarehousContract {
    public interface View extends IView {
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
        void showData( List<JmModelDataBean.DataBean.ListBean> list);

        /**
         * 没有数据
         */
        void showEmpty();

        /**
         * 没有下一页
         */
        void showNotMore();

        /**
         * 获取店名
         */
        void showShopData( List<ShopBean.DataBean> list);
    }
}
