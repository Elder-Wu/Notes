package com.wuzhanglao.niubi.mvp.view;

import com.wuzhanglao.niubi.mvp.view.BaseMvpView;

/**
 * Created by wuming on 16/10/19.
 */

public interface NetworkFragmentView extends BaseMvpView {
    void requestSuccess(Object obj);
    void requestFailed(String msg);
}
