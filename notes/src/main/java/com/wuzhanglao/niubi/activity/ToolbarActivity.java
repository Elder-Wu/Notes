package com.wuzhanglao.niubi.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;

/**
 * Created by wuming on 16/10/14.
 */

public abstract class ToolbarActivity extends BaseActivity implements View.OnClickListener {

    private TextView toolbar_title_tv;
    private TextView toolbar_back_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(setContentView());
        initDefaultToolBar();
        initView();
    }

    private void initDefaultToolBar() {
        toolbar_back_tv = (TextView) findViewById(R.id.general_toolbar_back_tv);
        toolbar_title_tv = (TextView) findViewById(R.id.general_toolbar_title_tv);

        toolbar_back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setToolbarTitle(@NonNull String title) {
        toolbar_title_tv.setText(title);
    }

    public void setToolbarBackVisible(boolean visible) {
        if (visible) {
            toolbar_back_tv.setVisibility(View.VISIBLE);
        } else {
            toolbar_back_tv.setVisibility(View.INVISIBLE);
        }
    }
}
