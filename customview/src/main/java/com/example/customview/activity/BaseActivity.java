package com.example.customview.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by wuming on 16/10/13.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        beforeSetContentView();
        setContentView(setContentView());
        initView();
    }

    protected void beforeSetContentView() {
    }

    protected abstract int setContentView();

    protected abstract void initView();
}
