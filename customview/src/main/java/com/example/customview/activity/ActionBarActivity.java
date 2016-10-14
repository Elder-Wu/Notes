package com.example.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by wuming on 16/10/14.
 */

public abstract class ActionBarActivity extends BaseActicity {

    private View actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        actionBar = setCustomActionBar();
        initView();
    }

    protected abstract View setCustomActionBar();

    protected View getCustomActionBar() {
        if (actionBar != null) {
            return actionBar;
        }
        return null;
    }
}
