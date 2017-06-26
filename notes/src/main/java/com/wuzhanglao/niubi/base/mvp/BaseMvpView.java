package com.wuzhanglao.niubi.base.mvp;

import com.wuzhanglao.niubi.base.BaseActivity;

public abstract class BaseMvpView<CB> {

	private BaseActivity mActivity;
	private CB mCallback;

	public BaseMvpView(BaseActivity activity) {
		mActivity = activity;
	}

	protected BaseActivity getActivity() {
		return mActivity;
	}

	public void setCallback(CB callback) {
		mCallback = callback;
	}

	protected CB getCallback() {
		return mCallback;
	}
}
