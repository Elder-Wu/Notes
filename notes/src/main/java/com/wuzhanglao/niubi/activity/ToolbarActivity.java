package com.wuzhanglao.niubi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wuming on 16/10/14.
 */

public abstract class ToolbarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(setContentView());
        initToolBar();
        initView();
    }

    protected void initToolBar(){

    };
}
