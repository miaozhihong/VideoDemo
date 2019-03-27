package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.JmModelDataListBean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2019/1/11 10:13
 * 页面功能:内务详情页集合
 */
public class DetailListAdapter extends BaseQuickAdapter<JmModelDataListBean.DataBean.ArticleVOListBean, BaseViewHolder> {

    private final Context context1;

    public DetailListAdapter(Context context, @Nullable List<JmModelDataListBean.DataBean.ArticleVOListBean> data) {
        super(R.layout.detail_list, data);
        context1 = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final JmModelDataListBean.DataBean.ArticleVOListBean item) {
        helper.setText(R.id.tv_list_name, item.getArticleName() + "");
        helper.setText(R.id.tv_list_num, item.getNumber() + "");
        //时间
        if(null==item.getTime()){
            helper.setText(R.id.tv_list_time, "暂无");
        }else {
            helper.setText(R.id.tv_list_time, item.getTime() + "");
        }
        //图片
        if (null != item.getUrl() &&!item.getUrl().equals("")) {
            helper.setText(R.id.tv_list_image, "点击查看");
        } else {
            helper.setText(R.id.tv_list_image, "上传图片");
        }
        //月末盘点
        if (null != item.getLastUrl()&&!item.getLastUrl().equals("") ) {
            helper.setText(R.id.tv_list_month, "点击查看");
        } else {
            helper.setText(R.id.tv_list_month, "上传图片");
        }
        helper.addOnClickListener(R.id.tv_list_image);
        helper.addOnClickListener(R.id.tv_list_month);
    }
}
