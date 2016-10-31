package com.wuzhanglao.niubi.mvp.presenter;

import com.wuzhanglao.niubi.mvp.view.BaseMvpView;

/**
 * Created by wuming on 16/10/20.
 */

public abstract class BaseMvpPresenter<V extends BaseMvpView> {

    V view;

    public void attachView(V view){
        this.view = view;
    }

    public void detachView(){
        view = null;
    }
}
