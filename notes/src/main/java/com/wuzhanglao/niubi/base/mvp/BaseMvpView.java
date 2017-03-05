package com.wuzhanglao.niubi.base.mvp;
/*
 * date:2017/3/1
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import android.content.Context;
import android.view.View;

public abstract class BaseMvpView<CB extends BaseMvpViewCallback> {

    private View mRootView;
    private CB mCallback;

    public BaseMvpView(View rootView) {
        mRootView = rootView;
    }

    public Context getContext() {
        return mRootView.getContext();
    }

    public View getRootView() {
        return mRootView;
    }

    public void setCallback(CB callback) {
        mCallback = callback;
    }

    public CB getCallback() {
        return mCallback;
    }
}
