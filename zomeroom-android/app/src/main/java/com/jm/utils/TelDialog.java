package com.jm.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jm.R;
import com.jm.bean.TelBean;
import com.jm.mvp.ui.adapter.TalkTelChooseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/25 11:56
 * 页面功能:自定义打电话dialog
 */
public class TelDialog extends Dialog {

    private Context context;
    ArrayList<TelBean> list=new ArrayList<>();
    private TalkTelChooseAdapter mTalkTelChooseAdapter;

    /**
     * @param context 上下文引用
     */
    public TelDialog(Context context, ArrayList<TelBean> list) {
        super(context, R.style.Theme_AppCompat_DayNight);
        this.context = context;
        this.list = list;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_dialog, null);
        setContentView(view);

        RecyclerView mRvTel =  view.findViewById(R.id.rv_tel);
        mTalkTelChooseAdapter = new TalkTelChooseAdapter(list);
        mRvTel.setLayoutManager(new LinearLayoutManager(context));
        mRvTel.setAdapter(mTalkTelChooseAdapter);
        mTalkTelChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (null != itemClickListenerInterface) {
                    itemClickListenerInterface.doClickItem(list.get(position).getTel()+"");
                    dismiss();
                }
            }
        });

        Window window = getWindow();
        if (null != window) {
            //设置dial dialog 窗体宽度
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = 700;
            lp.height = 900;
            window.setAttributes(lp);
        }
    }

    //按钮点击接口
    private ItemClickListenerInterface itemClickListenerInterface;

    public void setItemClickListenerInterface(ItemClickListenerInterface itemClickListenerInterface){
        this.itemClickListenerInterface = itemClickListenerInterface;
    }

    public interface ItemClickListenerInterface {
        void doClickItem(String tel);
    }
}
