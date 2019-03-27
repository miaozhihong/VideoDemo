package com.jm.utils;

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
import com.jm.mvp.ui.adapter.TalkTelChooseAdapter;

import java.util.ArrayList;

public class SwitchingDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView tvOperate;
    private TextView tvInterior;
    private TextView tvMarket;
    //按钮点击接口
    private ItemClickListenerInterface itemClickListenerInterface;

    /**
     * @param context 上下文引用
     */
    public SwitchingDialog(Context context) {
        super(context, R.style.Theme_AppCompat_DayNight);
        this.context = context;

        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.switch_dialog, null);
        setContentView(view);
        tvOperate = view.findViewById(R.id.tv_operate);
        tvInterior = view.findViewById(R.id.tv_interior);
        tvMarket = view.findViewById(R.id.tv_market);
        tvOperate.setOnClickListener(this);
        tvInterior.setOnClickListener(this);
        tvMarket.setOnClickListener(this);
        Window window = getWindow();
        if (null != window) {
            //设置dial dialog 窗体宽度
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = 800;
            lp.height = 800;
            window.setAttributes(lp);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_operate:
                itemClickListenerInterface.doOperateClickItem();
                break;
                case R.id.tv_interior:
                itemClickListenerInterface.doInteriorClickItem();
                break;
                case R.id.tv_market:
                itemClickListenerInterface.doMarketClickItem();
                break;
        }
    }

    public void setItemClickListenerInterface(ItemClickListenerInterface itemClickListenerInterface){
        this.itemClickListenerInterface = itemClickListenerInterface;
    }

    public interface ItemClickListenerInterface {
        void doOperateClickItem();//销售
        void doInteriorClickItem();//内务
        void doMarketClickItem();//开发
    }
}
