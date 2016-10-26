package com.example.notes.view;

/**
 * Created by wuming on 16/10/19.
 */

public interface NetworkFragmentMvpView extends BaseMvpView {
    void requestSuccess();
    void requestFailed(String msg);
}
