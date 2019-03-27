package com.jm.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.mvp.base.LocationAddressInfoBean;
import com.jm.mvp.ui.activity.MapTalkActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图poi检索地址列表
 */
public class LocationListAdapter extends BaseQuickAdapter<LocationAddressInfoBean, BaseViewHolder> {

    public LocationListAdapter(@Nullable List<LocationAddressInfoBean> data) {
        super(R.layout.activity_map_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationAddressInfoBean item) {
        helper.setText(R.id.area,  "查找分店："+item.getTitle())
                .setText(R.id.shop,"地址："+item.getContext())
                .setText(R.id.longitude,"经度："+item.getLongitude())
                .setText(R.id.latitude,"纬度："+item.getLatitude());
    }
}
