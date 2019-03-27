package com.jm.mvp.contract;

import com.jm.bean.JmModelDataBean;
import com.jm.bean.ShopBean;
import com.jm.bean.ShowRequestDetailBean;
import com.jm.bean.UserBean;

import java.util.List;

import cn.com.yktour.network.base.IView;

/**
 * 内务管理
 */
public class DetailContract {
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
        /**
         * 获取姓名
         */
        void showUserData( List<UserBean.DataBean> list);
        /**
         * 刷新页面
         */
        void refresh();
       /**
         * 调拨返回数据
         */
        void showDetailData();
        /**
         * 查询是否有调拨请求
         */
        void showRequstDetailData(List<ShowRequestDetailBean.DataBean> bean);
  /**
         * 同意调拨请求
         */
        void showAreggRequstDetailData();
  /**
         * 不同意调拨请求
         */
        void showNotAreggRequstDetailData();

    }
}
