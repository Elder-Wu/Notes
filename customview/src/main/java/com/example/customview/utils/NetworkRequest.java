package com.example.customview.utils;

import com.example.customview.bean.BaseResp;
import com.example.customview.bean.WeatherBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wuming on 16/10/19.
 */

public class NetworkRequest {

    private static class NetworkRequestSingletonHolder {
        private static final NetworkRequest instance = new NetworkRequest();
        private static final NetworkService service = NetworkService.Factory.create();
    }

    public static final NetworkRequest getInstance() {
        return NetworkRequestSingletonHolder.instance;
    }

    private static final NetworkService getService() {
        return NetworkRequestSingletonHolder.service;
    }

    private class ComposeThread<T> implements Observable.Transformer<T, T> {
        @Override
        public Observable<T> call(Observable<T> observable) {
            return observable
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public void getWeather(String city, Action1 onNext) {
        getService().getWeatherService(city, GlobleData.HEFENG_KEY).compose(new ComposeThread<WeatherBean>()).subscribe(onNext);
    }

    public void getScenic(String cityid, Action1 onNext) {
        getService().getScenicService(cityid, GlobleData.HEFENG_KEY).compose(new ComposeThread<BaseResp>()).subscribe(onNext);
    }
}
