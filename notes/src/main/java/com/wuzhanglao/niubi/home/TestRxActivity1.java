package com.wuzhanglao.niubi.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;

/*
 * Created by wuming on 2016/12/2.
 */

public class TestRxActivity1 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rxbus1);

        findViewById(R.id.activity_rxbus1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestRxActivity1.this, TestRxActivity2.class);
                startActivity(intent);
            }
        });
    }
}
