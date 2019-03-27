package cn.com.yktour.network.subscribers;

/**
 * Created by sky on 2017/11/19.
 * description:用于ProgressDialog 接口
 * changed:创建
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
