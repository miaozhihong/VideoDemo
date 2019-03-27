package com.jm.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jm.R;
import com.jm.bean.AreaBean;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.ui.adapter.BaseRvAdapter;
import com.jm.mvp.ui.adapter.PopChooseAdapter;
import com.jm.mvp.ui.adapter.PopChooseAreaAdapter;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/12/17 19:42
 * 页面功能:
 */
public class PopAreaUtils {
    static CalendarDay mSelectDate;
    private static int item;
    /**
     * 获取列表和日历的页面
     *
     * @param context  上下文
     * @param list     左侧的列表数据
     * @param listener 回调
     * @return pop弹窗
     */
    public static MyPopupWindow initPop(Context context, final List<String> list, final PopAreaUtils.PopListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose, null);
        final MyPopupWindow popupWindow = new MyPopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        RecyclerView rvChoose = (RecyclerView) view.findViewById(R.id.rv_pop_choose);
        View vChoose = (View) view.findViewById(R.id.v_pop_choose);
        final MaterialCalendarView cvChoose = (MaterialCalendarView) view.findViewById(R.id.cv_pop_choose);
        TextView tvReset = (TextView) view.findViewById(R.id.tv_pop_choose_reset);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_pop_choose_confirm);
        final PopChooseAdapter adapter = new PopChooseAdapter(list, context);
        rvChoose.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvChoose.setAdapter(adapter);
        // 设置默认第一个选中
        adapter.selectPosition(0);
        // 左侧列表点击回调
        adapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                adapter.selectPosition(position);
                if (listener != null) {
                    listener.rvListener(position, list.get(position));
                }
            }
        });
        // 日历的相关设置
        cvChoose.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2008, 1, 1))
                .setMaximumDate(CalendarDay.from(2050, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        cvChoose.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        cvChoose.setWeekDayTextAppearance(R.style.CalendarWidgetHeader);
        // 选择日期后的回调
        cvChoose.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (listener != null) {
                    listener.cvListener(date);
                    mSelectDate = date;
                }
            }
        });
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cvListener(null);
                }
                adapter.selectPosition(0);
                if (listener != null) {
                    listener.rvListener(0, list.get(0));
                }
                cvChoose.setDateSelected(mSelectDate, false);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.confirmListener(adapter);
                }
                hidePop(popupWindow);
            }
        });
        // 点击其他地方的隐藏
        vChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop(popupWindow);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.disMissListener();
                }
            }
        });
        return popupWindow;
    }

    /**
     * 左右两遍边都是listView
     * @param context
     * @param
     * @return
     */
    public static MyPopupWindow initAreaPop(final Context context, final List<String> list, final List<AreaBean.DataBean> mData, final List<AreaBean.DataBean> mSub, final PopAreaUtils.PopListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_area, null);
        final MyPopupWindow popupWindow = new MyPopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        final ArrayList<AreaBean.DataBean> mList=new ArrayList<>();
        final RecyclerView rvChoose = (RecyclerView) view.findViewById(R.id.rv_pop_choose);
        final RecyclerView rvArea = (RecyclerView) view.findViewById(R.id.rv_area);
        View vChoose = (View) view.findViewById(R.id.v_pop_choose);
        TextView tvReset = (TextView) view.findViewById(R.id.tv_pop_choose_reset);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_pop_choose_confirm);
        final PopChooseAdapter adapter = new PopChooseAdapter(list, context);
        mList.clear();
        mList.addAll(mData);
        final PopChooseAreaAdapter areaAdapter = new PopChooseAreaAdapter(mList, context);
        rvChoose.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvChoose.setAdapter(adapter);
        rvArea.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvArea.setAdapter(areaAdapter);
        // 左侧列表点击回调

        adapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                adapter.selectPosition(position);
                item = position;
                if (position==1){//判断是否为地铁
                    mList.clear();
                    mList.addAll(mSub);
                    areaAdapter.notifyDataSetChanged();
                }else {
                    mList.clear();
                    mList.addAll(mData);
                    areaAdapter.notifyDataSetChanged();
                }
                if (listener != null) {
                    listener.rvListener(position, list.get(position));
                }

            }
        });
        // 右侧列表点击回调
        areaAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                //判断是否为地铁
                if (item==1){
                    if (listener != null) {
                        listener.rgListener(position, mSub.get(position));
                        areaAdapter.selectPosition(position);
                    }
                }else {
                    if (listener != null) {
                        listener.rgListener(position, mData.get(position));
                        areaAdapter.selectPosition(position);
                    }
                }
            }
        });
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.selectPosition(0);
                mList.clear();
                mList.addAll(mData);
                areaAdapter.notifyDataSetChanged();
                areaAdapter.selectPosition(0);
                if (listener != null) {
                    listener.rvListener(0, list.get(0));
                    listener.rgListener(0, mData.get(0));
                    listener.rgReset();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.confirmListener(adapter);
                }
                hidePop(popupWindow);
            }
        });
        // 点击其他地方的隐藏
        vChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop(popupWindow);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.disMissListener();
                }
            }
        });
        return popupWindow;
    }


    /**
     * 内务区域查询
     * @param context
     * @param
     * @return
     */
    public static MyPopupWindow initDetailAreaPop(final Context context, final List<String> list,final PopAreaUtils.PopListenerDetail listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_detail_area, null);
        final MyPopupWindow popupWindow = new MyPopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        final RecyclerView rvArea = (RecyclerView) view.findViewById(R.id.rv_area);
        View vChoose = (View) view.findViewById(R.id.v_pop_choose);
        TextView tvReset = (TextView) view.findViewById(R.id.tv_pop_choose_reset);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_pop_choose_confirm);
        final PopChooseAdapter areaAdapter = new PopChooseAdapter(list, context);
        rvArea.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvArea.setAdapter(areaAdapter);
        areaAdapter.selectPosition(0);
        areaAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                areaAdapter.selectPosition(position);
                if (listener != null) {
                    listener.rvListener(position,list.get(position));
                }
            }
        });
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaAdapter.selectPosition(0);
                if (listener != null) {
                    listener.rgReset();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (listener != null) {
                        listener.confirmListener();
                    }
                }
                hidePop(popupWindow);
            }
        });
        // 点击其他地方的隐藏
        vChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop(popupWindow);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.disMissListener();
                }
            }
        });
        return popupWindow;
    }



















































    public static MyPopupWindow initSearchPop(Context context, final PopAreaUtils.SearchListener listenter) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_search, null);
        final MyPopupWindow popupWindow = new MyPopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        final EditText etSearch = (EditText) view.findViewById(R.id.et_pop_search);
        TextView tvSearch = (TextView) view.findViewById(R.id.tv_pop_select);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = etSearch.getText().toString().trim();
                if (listenter != null) {
                    listenter.select(tel);
                }
                hidePop(popupWindow);
            }
        });
        return popupWindow;
    }

    /**
     * 显示时候的状态
     *
     * @param textView 要显示的那个
     */
    public static void tvUp(TextView textView) {
        Drawable drawableUp = SampleApplicationContext.context.getResources().getDrawable(R.mipmap.signtop);
        drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableUp, null);
        textView.setTextColor(SampleApplicationContext.context.getResources().getColor(R.color.text_color_111));
    }

    /**
     * 隐藏时候的状态
     *
     * @param textView 要隐藏的那个
     */
    public static void tvDown(TextView textView) {
        Drawable drawableUp = SampleApplicationContext.context.getResources().getDrawable(R.mipmap.signdown);
        drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableUp, null);
        textView.setTextColor(SampleApplicationContext.context.getResources().getColor(R.color.text_color_666));
    }

    /**
     * 隐藏页面指定pop弹窗
     *
     * @param popupWindow 那个pop
     */
    public static void hidePop(MyPopupWindow popupWindow) {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }

    /**
     * 销毁popupwindow
     *
     * @param popupWindow 需要销毁的
     */
    public static void destroyPop(MyPopupWindow popupWindow) {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow = null;
        }
    }

    public interface PopListener {
        /**
         * 左侧的列表选择回调
         *
         * @param position 位置
         * @param s        内容
         */
        void rvListener(int position, String s);

        /**
         * 右侧的列表选择回调
         *
         * @param position 位置
         * @param
         */
        void rgListener(int position, AreaBean.DataBean dataBean);

        /**
         * 重置
         */
        void rgReset();


        /**
         * 右侧的日期选择回调
         *
         * @param date 日期
         */
        void cvListener(CalendarDay date);


        /**
         * 确定
         * @param adapter
         */
        void confirmListener(PopChooseAdapter adapter);
        /**
         * 消失的回调
         */
        void disMissListener();
    }


    public interface PopListenerDetail {
        /**
         * 列表选择回调
         */
        void rvListener(int position, String s);
        /**
         * 重置
         */
        void rgReset();
        /**
         * 确定
         */
        void confirmListener();
        /**
         * 消失的回调
         */
        void disMissListener();
    }
    public interface SearchListener {
        void select(String tel);
    }

    /**
     * 添加备注
     *
     * @param context
     * @param activity
     * @return
     */
    public static MyPopupWindow initAddRemark(Context context, final Activity activity,
                                              final PopAreaUtils.RemarkListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_add_remart, null);
        final MyPopupWindow addBlackPop = new MyPopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        addBlackPop.setBackgroundDrawable(new ColorDrawable(0));
        //不设置true无法弹起输入框
        addBlackPop.setFocusable(true);
        addBlackPop.setOutsideTouchable(false);
        addBlackPop.setTouchable(true);

        ImageView ivBlackClose = (ImageView) view.findViewById(R.id.iv_pop_add_close);
        final EditText etBlackReason = (EditText) view.findViewById(R.id.et_pop_add_reason);
        TextView tvBlackConfirm = (TextView) view.findViewById(R.id.tv_pop_add_confirm);

        ivBlackClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addBlackPop.isShowing()) {
                    addBlackPop.dismiss();
                }
            }
        });
        tvBlackConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = CommonUtils.getEditText(etBlackReason);
                //添加成功后清除 edit的内容
                if (TextUtils.isEmpty(reason)) {
                    ToastUtils.ToastCenter("备注不能为空");
                    return;
                }
                if (listener != null) {
                    listener.addRemark(reason);
                    etBlackReason.setText("");
                }
            }
        });
        addBlackPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CommonUtils.setBackgroundAlpha(activity, 1f);
            }
        });
        return addBlackPop;
    }

    public interface RemarkListener {
        void addRemark(String remark);
    }
}
