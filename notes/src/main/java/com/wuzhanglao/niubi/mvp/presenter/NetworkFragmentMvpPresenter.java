package com.wuzhanglao.niubi.mvp.presenter;

import com.wuzhanglao.niubi.mvp.model.HeBeiBeiBean;
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
                view.requestSuccess(weather);
            }
        };
        NetworkRequest.getInstance().getWeather(city, onNext);
    }

    public void getHeBeiBeiDate() {
        Action1<HeBeiBeiBean> onNext = new Action1<HeBeiBeiBean>() {
            @Override
            public void call(HeBeiBeiBean heBeiBeiBean) {
                view.requestSuccess(heBeiBeiBean);
            }
        };
        NetworkRequest.getInstance().getHeBeiBeiData(onNext);

    }
}