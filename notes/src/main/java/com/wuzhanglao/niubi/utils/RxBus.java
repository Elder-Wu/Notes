package com.wuzhanglao.niubi.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by wuming on 2016/11/22.
 */

//单例模式的一种写法
public enum RxBus {
    INSTANCE;

    private Subject<Object, Object> mRxBusObserverable = new SerializedSubject<>(PublishSubject.create());

    public static void send(Object object) {
        if (INSTANCE.mRxBusObserverable.hasObservers()) {
            INSTANCE.mRxBusObserverable.onNext(object);
        }
    }

    public static Observable<Object> toObserverable() {
        return INSTANCE.mRxBusObserverable;
    }
}
