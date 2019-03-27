package com.jm.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.jm.bean.DaoMaster;
import com.jm.bean.DaoSession;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/11/7 18:08
 * 页面功能:
 */
@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {
private boolean isPush=false;
public static String pak_Name="com.jm";
    // 在收到消息时触发
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的标题
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        //未读数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "jm_notread.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TitlesDao titlesDao = daoSession.getTitlesDao();
        if (notificationPkg.equals(pak_Name) && !notificationTitle.equals("“己美LEAN”正在运行") && !notificationTitle.equals("正在运行中")){
            //获取当前时间
            SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            String time =formatter.format(curDate);
            titlesDao.insert(new Titles(null,notificationPkg,notificationTitle,notificationText,time,"系统邮件","0","否","系统推送",(String) SPUtils.get("userName","")));
        }
    }

    // 在删除消息时触发
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
    }
}

