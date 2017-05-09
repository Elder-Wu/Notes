package com.wuzhanglao.niubi.base.mvp;
/*
 * date:2017/3/1
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

public abstract class BaseMvpPresenter<V extends BaseMvpView, M extends BaseMvpModel> {
	private V mView;
	private M mModel;

	public void setModel(M model) {
		mModel = model;
	}

	public void setView(V view) {
		mView = view;
	}

	public V getView() {
		return mView;
	}

	public M getModel() {
		return mModel;
	}

	public final void attach() {
		onAttach();
	}

	public final void detach() {
		mView = null;
		mModel = null;
		onDetach();
	}

	public void onAttach() {

	}

	public void onDetach() {

	}
}
