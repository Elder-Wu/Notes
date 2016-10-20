package com.example.customview.presenter;

import com.example.customview.view.BaseMvpView;

/**
 * Created by wuming on 16/10/20.
 */

public class BaseMvpPresenter<V extends BaseMvpView> {

    V view;

    public void attachView(V view){
        this.view = view;
    }

    public void detachView(){
        view = null;
    }
}
