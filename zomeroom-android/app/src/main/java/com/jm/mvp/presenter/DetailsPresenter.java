package com.jm.mvp.presenter;

import android.os.Handler;
import android.text.Spannable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.CommonBean;
import com.jm.bean.GetIdJmBusubess;
import com.jm.bean.JmModelDataListBean;
import com.jm.bean.UpTelDetailBean;
import com.jm.helper.OkHttp3Utils;
import com.jm.helper.ProgressSubscriber;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BasePresenter;
import com.jm.mvp.contract.DetailContract;
import com.jm.mvp.contract.DetailsContract;
import com.jm.mvp.repository.DetailsRepository;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.ConnectedUtils;

import java.io.IOException;
import java.util.List;

import cn.com.yktour.network.subscribers.SubscriberOnNextListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author mzh
 * @Description DetailPersenter 内务管理
 */
public class DetailsPresenter extends BasePresenter<DetailsRepository> {

    private DetailsContract.View mView;
    private Gson mGson;

    public DetailsPresenter(DetailsContract.View view) {
        mView = view;
        mModel = new DetailsRepository();
        mGson = new Gson();
    }

    /**
     * 访问数据前的网络判断，不需要可以删除
     */
    public void netAndData(String id) {
        if (ConnectedUtils.isNetworkAvailable()) {
               getData(id);
        } else {
            mView.showNetError();
        }
    }

    /**
     * 获取数据
     */
    public void getData(String id) {
        mModel.getListDataDetail(id)
                .subscribe(new ProgressSubscriber<JmModelDataListBean>(
                        new SubscriberOnNextListener<JmModelDataListBean>() {
                            @Override
                            public void onNext(JmModelDataListBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                mView.showData(bean);
                            }
                        }, mView, this
                ));
    }

    /**
     * 更新数据
     */
    public void updateData(JmModelDataListBean.DataBean bean) {
        String json = mGson.toJson(bean);
        mModel.updateDetail(json)
                .subscribe(new ProgressSubscriber<CommonBean>(
                        new SubscriberOnNextListener<CommonBean>() {
                            @Override
                            public void onNext(CommonBean bean) {
                                int flag = bean.getFlag();
                                if (flag != Constants.NET_SUCCESS) {
                                    //处理请求失败的相关逻辑
                                    mView.showNetError();
                                    return;
                                }
                                mView.refresh();
                            }
                        }, mView, this
                ));
    }
}
