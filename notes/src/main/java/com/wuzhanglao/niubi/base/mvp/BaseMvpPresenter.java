package com.wuzhanglao.niubi.base.mvp;
/*
 * date:2017/3/1
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import com.wuzhanglao.niubi.base.BaseActivity;

public abstract class BaseMvpPresenter<V extends BaseMvpView, M extends BaseMvpModel> {
	private V mView;
	private M mModel;
	private BaseActivity mActivity;

	public BaseMvpPresenter(BaseActivity activity) {
		mActivity = activity;
		mView = setView();
		mModel = setModel();
	}

	public BaseActivity getActivity() {
		return mActivity;
	}

	public abstract M setModel();

	public abstract V setView();

	public V getView() {
		return mView;
	}

	public M getModel() {
		return mModel;
	}

	public final void detach() {
		mView = null;
		mModel = null;
		mActivity = null;
		onDetach();
	}

	public void onDetach() {

	}
}
