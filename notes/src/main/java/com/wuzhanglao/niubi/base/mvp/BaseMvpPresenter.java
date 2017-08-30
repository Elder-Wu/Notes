package com.wuzhanglao.niubi.base.mvp;

public abstract class BaseMvpPresenter<V extends BaseMvpView, M extends BaseMvpModel> {

    private V mView;
    private M mModel;

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

    public M getModel(){
        return mModel;
    }

    public void setModel(M model){
        mModel = model;
    }
}
