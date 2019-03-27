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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jm.R;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.ui.adapter.BaseRvAdapter;
import com.jm.mvp.ui.adapter.PopChooseAdapter;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.List;

/**
 * @author pc 张立男
 * @Description PopUtils 弹窗的工具类
 * @date 2018/5/7 9:51
 * o(＞﹏＜)o
 */

public class PopUtils {
    static CalendarDay mSelectDate;
    public static int positions=0;
    /**
     * 获取列表和日历的页面
     *
     * @param context  上下文
     * @param list     左侧的列表数据
     * @param listener 回调
     * @return pop弹窗
     */
    public static MyPopupWindow initPop(Context context, final List<String> list, final PopListener listener) {
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
        //adapter.selectPosition(0);
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
                if (listener != null) {
                    listener.resets();
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

    public static MyPopupWindow initSearchPop(Context context, final SearchListener listenter) {
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
         * 右侧的日期选择回调
         *
         * @param date 日期
         */
        void cvListener(CalendarDay date);


        /**
         * 确定
         *
         * @param adapter
         */
        void confirmListener(PopChooseAdapter adapter);
        /**
         * 确定
         *
         * @param
         */
        void confirmListener();


        /**
         * 消失的回调
         */
        void disMissListener();
        /**
         * 消失的回调
         */
        void resets();

        /**
         * radioButton接口回调
         */
        void onRadioChildClikc(int position);

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
                                              final RemarkListener listener) {
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

    /**
     * 添加备注
     *
     * @param context
     * @param activity
     * @return
     */
    public static MyPopupWindow initAddTalkRemark(Context context, final Activity activity,
                                                  final RemarkTalkListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_add_talk_remart, null);
        final MyPopupWindow addBlackPop = new MyPopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        addBlackPop.setBackgroundDrawable(new ColorDrawable(0));
        //不设置true无法弹起输入框
        addBlackPop.setFocusable(true);
        addBlackPop.setOutsideTouchable(false);
        addBlackPop.setTouchable(true);

        ImageView ivBlackClose = (ImageView) view.findViewById(R.id.iv_pop_add_close);
        final EditText etBlackReason = (EditText) view.findViewById(R.id.et_pop_add_reason);
        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg);
        final RadioButton rbCustom = (RadioButton) view.findViewById(R.id.rb_custom);
        final RadioButton rbShop = (RadioButton) view.findViewById(R.id.rb_shop);
        final RadioButton rbTel = (RadioButton) view.findViewById(R.id.rb_tel);
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

            private String type;
            private int tag;

            @Override
            public void onClick(View v) {
                String reason = CommonUtils.getEditText(etBlackReason);
                //添加成功后清除 edit的内容
                if (TextUtils.isEmpty(reason)) {
                    ToastUtils.ToastCenter("备注不能为空");
                    return;
                }
                if (listener != null) {
                    if (rbCustom.isChecked()) {
                        tag = 0;
                        type = "自定义回访";
                    } else if (rbShop.isChecked()) {
                        tag = 1;
                        type = "进店回访";
                    } else {
                        tag = 2;
                        type = "电话回访";
                    }
                    listener.addTalkRemark(reason, tag, type);
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

    public interface RemarkTalkListener {
        void addTalkRemark(String remark, int tag, String type);
    }

    public static MyPopupWindow initTalkPop(Context context, final List<String> list, final PopListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_talk, null);
        final MyPopupWindow popupWindow = new MyPopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        RecyclerView rvChoose = (RecyclerView) view.findViewById(R.id.rv_pop_choose);
        View vChoose = (View) view.findViewById(R.id.v_pop_choose);
        final MaterialCalendarView cvChoose = (MaterialCalendarView) view.findViewById(R.id.cv_pop_choose);
        TextView tvReset = (TextView) view.findViewById(R.id.tv_pop_choose_reset);
        final RadioGroup rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        final RadioButton rbNo = (RadioButton) view.findViewById(R.id.rb_no);

        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_pop_choose_confirm);
        final PopChooseAdapter adapter = new PopChooseAdapter(list, context);
        rvChoose.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvChoose.setAdapter(adapter);
        // 设置默认第一个选中
        //adapter.selectPosition(0);
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

        //点击button的回调
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (listener != null) {
                        switch (group.getCheckedRadioButtonId()) {
                            case R.id.rb_no:
                                positions = 0;
                                break;
                            case R.id.rb_work:
                                positions = 1;
                                break;
                            case R.id.rb_half:
                                positions = 2;
                                break;
                            case R.id.rb_month:
                                positions = 3;
                                break;
                        }
                    listener.onRadioChildClikc(positions);
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
                    rbNo.setChecked(true);
                }
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


    public static MyPopupWindow initTalkTimePop(Context context, final PopListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_talk_time, null);
        final MyPopupWindow popupWindow = new MyPopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        View vChoose = (View) view.findViewById(R.id.v_pop_choose);
        final MaterialCalendarView cvChoose = (MaterialCalendarView) view.findViewById(R.id.cv_pop_choose);
        TextView tvReset = (TextView) view.findViewById(R.id.tv_pop_choose_reset);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_pop_choose_confirm);

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
                listener.resets();
                cvChoose.setDateSelected(mSelectDate, false);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.confirmListener();
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

}
