package com.wuzhanglao.niubi.utils;


import com.wuzhanglao.niubi.mvp.model.HeWeatherBean;
import com.wuzhanglao.niubi.mvp.model.ShanbayResp;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wuming on 16/10/19.
 */

public class NetworkRequest {

    public static final NetworkRequest getInstance() {
        return SingletonHolder.instance;
    }

    private static final NetworkService getHeWeatherService() {
        return SingletonHolder.heweather_service;
    }

    private static final NetworkService getShanbayService() {
        return SingletonHolder.shanbay_service;
    }

    public void getWeather(String city, Action1 onNext) {
        getHeWeatherService().getWeatherService(city, GlobleConfig.HEFENG_KEY).compose(new ComposeThread<HeWeatherBean>()).subscribe(onNext);
    }

    public void getScenic(String cityid, Action1 onNext) {
        getHeWeatherService().getScenicService(cityid, GlobleConfig.HEFENG_KEY).compose(new ComposeThread<HeWeatherBean>()).subscribe(onNext);
    }

    public void getShanbayTranslation(String word, Action1 onNext, Action1 onError) {
        getShanbayService().getTranslation(word).compose(new ComposeThread<ShanbayResp>()).subscribe(onNext, onError);
    }

    private static class SingletonHolder {
        private static final NetworkRequest instance = new NetworkRequest();
        private static final NetworkService heweather_service = NetworkService.Factory.create(NetworkService.BASE_URL_HeWeather);
        private static final NetworkService shanbay_service = NetworkService.Factory.create(NetworkService.BASE_URL_SHANBAY);
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
}
