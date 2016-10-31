package com.wuzhanglao.niubi.mvp.presenter;

import com.wuzhanglao.niubi.mvp.model.HeWeatherBean;
import com.wuzhanglao.niubi.mvp.view.NetworkFragmentMvpView;
import com.wuzhanglao.niubi.utils.NetworkRequest;

import rx.functions.Action1;

/**
 * Created by wuming on 16/10/19.
 */

public class NetworkFragmentMvpPresenter extends BaseMvpPresenter<NetworkFragmentMvpView> {

    public void request(String city) {
        Action1<HeWeatherBean> onNext = new Action1<HeWeatherBean>() {
            @Override
            public void call(HeWeatherBean weather) {
                view.requestSuccess();
            }
        };
        NetworkRequest.getInstance().getWeather(city, onNext);
    }
}
