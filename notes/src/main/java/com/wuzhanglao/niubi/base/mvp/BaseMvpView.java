package com.wuzhanglao.niubi.base.mvp;
/*
 * date:2017/3/1
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import android.app.Activity;

import com.wuzhanglao.niubi.base.BaseActivity;

public abstract class BaseMvpView<CB extends BaseMvpViewCallback> {

	private BaseActivity mActivity;
	private CB mCallback;

	public BaseMvpView(BaseActivity activity) {
		mActivity = activity;
	}

	public Activity getActivity() {
		return mActivity;
	}

	public void setCallback(CB callback) {
		mCallback = callback;
	}

	public CB getCallback() {
		return mCallback;
	}
}
