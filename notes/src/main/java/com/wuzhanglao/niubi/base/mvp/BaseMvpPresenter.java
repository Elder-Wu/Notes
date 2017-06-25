package com.wuzhanglao.niubi.base.mvp;
/*
 * date:2017/3/1
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

public abstract class BaseMvpPresenter<V extends BaseMvpView> {

	private V mView;

	public final void attach() {
		onAttach();
	}

	public final void detach() {
		onDetach();
		mView = null;
	}

	public void onAttach() {

	}

	public void onDetach() {

	}

	public V getView() {
		return mView;
	}

	public void setView(V view) {
		mView = view;
	}
}
