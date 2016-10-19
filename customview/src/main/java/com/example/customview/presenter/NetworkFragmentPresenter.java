package com.example.customview.presenter;

import com.example.customview.bean.BaseResp;
import com.example.customview.bean.WeatherBean;
import com.example.customview.utils.NetworkRequest;
import com.example.customview.utils.ToastUtils;

import rx.functions.Action1;

/**
 * Created by wuming on 16/10/19.
 */

public class NetworkFragmentPresenter {
    public void request(String city) {
        Action1<WeatherBean> onNext = new Action1<WeatherBean>() {
            @Override
            public void call(WeatherBean weather) {
                ToastUtils.show(weather.toString());
            }
        };
        NetworkRequest.getInstance().getWeather(city, onNext);
    }
}
