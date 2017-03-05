package com.wuzhanglao.niubi.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wuzhanglao.niubi.R;

public class HomeActivity extends ToolbarActivity {

    private HomePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new HomePresenter(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPresenter.getView().hideBackButton();
    }
}
