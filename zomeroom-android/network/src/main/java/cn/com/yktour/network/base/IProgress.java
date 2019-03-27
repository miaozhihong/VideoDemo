package cn.com.yktour.network.base;

/**
 * Created by sky on 2017/11/20.
 * description: 使用ProgressSubscriber 必须实现 IProgressPresenter接口
 * changed:
 */

public interface IProgress{
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();
    /**
     * 访问网络异常
     * @param code 异常码
     * @param message 异常Message
     * @param type 区分不同请求，在new PregressSubscriber时带入
     * @return 是否处理
     */
    boolean httpError(int code, String message, int type);

    void toLogin();
}
