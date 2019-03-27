package cn.com.yktour.network.utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author houxiaokang
 * @date 2017/12/25
 * description:
 * changed:
 */
public class ObservableHelper {
    public static Observable initObservable(Observable observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
