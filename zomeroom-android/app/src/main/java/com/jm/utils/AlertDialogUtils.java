package com.jm.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import com.jm.R;


/**
 * @author wangdeshun
 * @Description AlertDialogUtils 对话框
 * @date 2017/11/24 16:28
 * o(＞﹏＜)o
 */

public class AlertDialogUtils {

    /**
     * AlertDialog对话框
     *
     * @param activity        上下文
     * @param title           Title内容
     * @param msg             Content显示一个信息
     * @param onClickListener PositiveButton 点击
     * @return
     */
    public static Dialog customAlertDialog(Activity activity, String title, String msg, DialogInterface.OnClickListener onClickListener) {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //    设置Title的内容
        builder.setTitle(title);
        //    设置Content来显示一个信息
        builder.setMessage(msg);
        //    设置一个PositiveButton
        builder.setPositiveButton(activity.getString(R.string.submit), onClickListener);
        //    设置一个NegativeButton
        builder.setNegativeButton(activity.getString(R.string.cancel), null);
        //    显示出该对话框
        builder.show();
        return null;
    }

}