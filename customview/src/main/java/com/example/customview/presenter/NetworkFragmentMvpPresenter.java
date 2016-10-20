package com.example.customview.presenter;

import com.example.customview.bean.HeWeatherBean;
import com.example.customview.utils.NetworkRequest;
import com.example.customview.utils.ToastUtils;

import rx.functions.Action1;

/**
 * Created by wuming on 16/10/19.
 */

public class NetworkFragmentMvpPresenter extends BaseMvpPresenter {

    public void request(String city) {
        Action1<HeWeatherBean> onNext = new Action1<HeWeatherBean>() {
            @Override
            public void call(HeWeatherBean weather) {
                ToastUtils.show(weather.toString());
            }
        };
        NetworkRequest.getInstance().getWeather(city, onNext);
    }
}
