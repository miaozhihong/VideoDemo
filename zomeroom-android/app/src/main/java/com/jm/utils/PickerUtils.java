package com.jm.utils;

import android.app.Activity;
import android.os.SystemClock;

import cn.com.yktour.network.utils.MTMLogger;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * @author pc 张立男
 * @Description PickerUtils 选择器的utils
 * @date 2018/2/26 16:07
 * o(＞﹏＜)o
 */

public class PickerUtils {
    public interface SingleListener {
        void select(int index, String item);
    }

    public static OptionPicker initSinglePicker(Activity activity, String[] strings, final SingleListener listener) {
        OptionPicker picker = new OptionPicker(activity, strings);
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(13);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                if (listener != null) {
                    listener.select(index, item);
                }
            }
        });
        return picker;
    }

    public static DateTimePicker initDatePicker(Activity activity) {
        DateTimePicker picker = new DateTimePicker(activity, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2018, 1, 1);
        picker.setDateRangeEnd(2058, 12, 31);
        picker.setTimeRangeStart(0, 0);
        picker.setTimeRangeEnd(23, 59);
        int[] time = CommonUtils.toIntTime(CommonUtils.formatDate(System.currentTimeMillis()));
        picker.setSelectedItem(time[0], time[1], time[2], time[3], time[4]);
        ///展示用法
//        picker.setTopLineColor(0x99FF0000);
//        picker.setTextColor(0xFFFF0000);
//        picker.setDividerColor(0xFFFF0000);
        return picker;
    }
}
