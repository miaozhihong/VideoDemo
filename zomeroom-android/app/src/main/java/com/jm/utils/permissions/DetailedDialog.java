package com.jm.utils.permissions;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jm.R;
import com.jm.bean.TelBean;
import com.jm.mvp.ui.adapter.DetailChooseAdapter;
import com.jm.mvp.ui.adapter.TalkTelChooseAdapter;

import java.util.ArrayList;

/**
 * 作者 Created by $miaozhihong on 2019/1/12 15:12
 * 页面功能:调拨dialog
 */
public class DetailedDialog extends Dialog {
    private Context context;
    ArrayList<String> list=new ArrayList<>();
    private DetailChooseAdapter mDetailChooseAdapter;

    /**
     * @param context 上下文引用
     */
    public DetailedDialog(Context context, ArrayList<String> list) {
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
        TextView mTvTitle =  view.findViewById(R.id.tv_title);
        mTvTitle.setText("请选择以下调拨分店");
        mDetailChooseAdapter = new DetailChooseAdapter(list);
        mRvTel.setLayoutManager(new LinearLayoutManager(context));
        mRvTel.setAdapter(mDetailChooseAdapter);
        mDetailChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (null != itemClickListenerInterface) {
                    itemClickListenerInterface.doClickItem(position,list.get(position));
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
        void doClickItem(int position,String shopName);
    }
}
