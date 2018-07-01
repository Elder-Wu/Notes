package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.mvp.presenter.BaseMvpPresenter;
import com.wuzhanglao.niubi.mvp.view.BaseMvpView;

/**
 * Created by wuming on 16/10/19.
 */

public abstract class BaseMvpFragment<V extends BaseMvpView, P extends BaseMvpPresenter> extends BaseFragment {

    public P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        presenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public abstract P initPresenter();
}
