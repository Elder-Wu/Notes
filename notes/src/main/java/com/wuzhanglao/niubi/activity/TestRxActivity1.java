package com.wuzhanglao.niubi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;

/*
 * Created by wuming on 2016/12/2.
 */

public class TestRxActivity1 extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rxbus1);
        initDefaultToolBar();
        setToolbarTitle("MainActivity1");
        setToolbarBackVisible(false);

        findViewById(R.id.activity_rxbus1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestRxActivity1.this, TestRxActivity2.class);
                startActivity(intent);
            }
        });
    }
}
