package com.wuzhanglao.niubi.base.mvp;

import com.wuzhanglao.niubi.base.BaseActivity;

public abstract class BaseMvpView<T> {

    private BaseActivity mActivity;
    private T mCallback;

    public BaseMvpView(BaseActivity activity) {
        mActivity = activity;
    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    public void setCallback(T callback) {
        mCallback = callback;
    }

    public T getCallback() {
        return mCallback;
    }
}