package com.jm.helper;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * @author houxiaokang
 * @date 2017/12/11
 * description:RxBus2
 * changed:
 */
public class RxBus2 {
    private static volatile RxBus2 instance;
    private final Relay<Object> mBus;

    public RxBus2() {
        this.mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus2 getInstance() {
        if (instance == null) {
            synchronized (RxBus2.class) {
                if (instance == null) {
                    instance = Holder.BUS;
                }
            }
        }
        return instance;
    }
    public void post(Object obj) {
        mBus.accept(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return  mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus2 BUS = new RxBus2();
    }
}
