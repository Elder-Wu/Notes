package com.example.notes.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wuming on 16/10/14.
 */

public abstract class ActionBarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(setContentView());
        initActionBar();
        initView();
    }

    protected abstract void initActionBar();
}
