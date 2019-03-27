package com.jm.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.jm.helper.SampleApplicationContext;



/**
 * Created by sky on 2017/11/19.
 * description: 吐丝工具类
 * changed:
 */

public class ToastUtils {
    private static Toast toast;
    /**
     * 屏幕中间弹窗
     *
     * @param msg
     */
    public static void ToastCenterLong(String msg) {
        if (null == toast)
        {
            toast = Toast.makeText(SampleApplicationContext.context, msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        else
            toast.setText(msg);
        toast.show();
    }
    /**
     * 屏幕中间弹窗
     *
     * @param msg
     */
    public static void ToastCenter(String msg) {
        if (null == toast)
        {
            toast = Toast.makeText(SampleApplicationContext.context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        else
            toast.setText(msg);
        toast.show();
    }

    /**
     * 屏幕底端
     *
     * @param msg
     */
    public static void ToastBottow( String msg) {
        if (null == toast)
        {
            toast = Toast.makeText(SampleApplicationContext.context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
        }
        else
            toast.setText(msg);
        toast.show();
    }

    /**
     * 默认位置
     *
     * @param msg
     */
    public static void ToastDefult( String msg) {
        if (null == toast)
        {
            toast = Toast.makeText(SampleApplicationContext.context, msg, Toast.LENGTH_SHORT);
        }
        else
            toast.setText(msg);
        toast.show();
    }
}
