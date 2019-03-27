package cn.com.yktour.network.base;

import android.app.Activity;

/**
 * Created by sky on 2017/11/7.
 * description: APP Presenter 接口 框架要求框架中的每个 Presenter 都需要实现此类,以满足规范
 * changed: 创建
 */
public interface IPresenter {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();


}
