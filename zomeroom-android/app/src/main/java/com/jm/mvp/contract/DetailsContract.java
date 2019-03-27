package com.jm.mvp.contract;

import com.jm.bean.JmModelDataBean;
import com.jm.bean.JmModelDataListBean;

import cn.com.yktour.network.base.IView;

/**
 * 作者 Created by $miaozhihong on 2019/1/10 10:44
 * 页面功能:
 */
public class DetailsContract {
    public interface View extends IView {
        /**
         * 无网络连接
         */
        void showNetError();

        /**
         * 请求成功后返回的数据
         * 需要自己添加泛型
         */
        void showData(JmModelDataListBean data);

        /**
         * 更新数据成功
         */
        void refresh();
    }
}
