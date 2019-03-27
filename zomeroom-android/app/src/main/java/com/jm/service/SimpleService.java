/*
package com.jm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jm.bean.TelListBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

*/
/**
 * 作者 Created by Administrator on 2018/9/13 10:27
 * 页面功能:
 *//*

public class SimpleService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //开启一个线程获取列表数据，判断是否录入，如果没有录入调用短信api接口
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        Thread.sleep(1800000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("service线程数据","-->请求服务器");
                }
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(List<TelListBean.DataBean.ListBean> list){
        Log.e("list","listSize-->"+list.size());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
*/
