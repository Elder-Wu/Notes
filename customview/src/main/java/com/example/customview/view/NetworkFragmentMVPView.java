package com.example.customview.view;

/**
 * Created by wuming on 16/10/19.
 */

public interface NetworkFragmentMVPView extends BaseMVPView {
    void requestSuccess();
    void requestFailed(String msg);
}
