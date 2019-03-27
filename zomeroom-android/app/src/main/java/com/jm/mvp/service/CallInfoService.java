package com.jm.mvp.service;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import com.jm.bean.CallInfo;
import com.jm.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc 张立男
 * @Description CallInfoService 获取通讯录
 * @date 2018/5/9 15:18
 * o(＞﹏＜)o
 */

public class CallInfoService {
    /**
     * 获取通话记录
     *
     * @param context 上下文。通话记录需要从系统的【通话应用】中的内容提供者中获取，内容提供者需要上下文。通话记录保存在联系人数据库中：data/data/com.android.provider.contacts/databases/contacts2.db库中的calls表。
     * @return 包含所有通话记录的一个集合
     */
    public static List<CallInfo> getCallInfos(Context context) {
        List<CallInfo> infos = new ArrayList<CallInfo>();
        ContentResolver resolver = context.getContentResolver();
        // uri的写法需要查看源码JB\packages\providers\ContactsProvider\AndroidManifest.xml中内容提供者的授权
        // 从清单文件可知该提供者是CallLogProvider，且通话记录相关操作被封装到了Calls类中
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{
                CallLog.Calls.CACHED_NAME,
                // 号码
                CallLog.Calls.NUMBER,
                // 日期
                CallLog.Calls.DATE,
                // 类型：来电、去电、未接
                CallLog.Calls.TYPE,
                //通话时间
                CallLog.Calls.DURATION
        };

        @SuppressLint("MissingPermission")
        Cursor cursor = resolver.query(uri, projection, CallLog.Calls.DATE + ">?", new String[]{CommonUtils.getLast3Day()}, "date desc");
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String number = cursor.getString(1);
            long date = cursor.getLong(2);
            int type = cursor.getInt(3);
            long duration = cursor.getLong(4);
            infos.add(new CallInfo(name, number, date, type, duration));
        }
        cursor.close();
        return infos;
    }
}
