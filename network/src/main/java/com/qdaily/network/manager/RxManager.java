package com.qdaily.network.manager;

import com.qdaily.network.entity.Result;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yuelinghui on 17/8/1.
 */

public class RxManager {

    private static RxManager mRxManager = null;

    private RxManager() {

    }

    public synchronized static RxManager getInstance() {
        if (mRxManager == null) {
            mRxManager = new RxManager();
        }
        return mRxManager;
    }

    /**
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */

    public <T> void doSubscribe(final Observable<Result<T>> observable, final Subscriber<T> subscriber) {
        Action1<Result<T>> onSuccessAction = new Action1<Result<T>>() {
            @Override
            public void call(Result<T> result) {
                if (result == null) {
                    subscriber.onError(new Throwable("没有数据返回"));
                    subscriber.onCompleted();
                    return;
                }
                if (!result.isFlag()) {
                    subscriber.onError(new Throwable(result.getMessage()));
                    subscriber.onCompleted();
                    return;
                }
                if (result.isFlag()) {
                    subscriber.onNext(result.getResultData());
                }
                subscriber.onCompleted();

            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                subscriber.onError(throwable);
                subscriber.onCompleted();
            }
        };

        subscriber.onStart();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccessAction, onErrorAction);
    }

}
