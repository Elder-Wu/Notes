package com.wuzhanglao.niubi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.Gson;
import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.bean.RxBusBean;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.utils.RxBus;

import java.util.Random;

/*
 * Created by wuming on 2016/12/2.
 */

public class TestRxActivity2 extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rxbus2);

        initDefaultToolBar();
        setToolbarTitle("MainActivity2");
        setToolbarBackVisible(false);

        findViewById(R.id.activity_rxbus2_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RxBusBean bean = new RxBusBean();
                bean.setFrom("MainActivity2");
                bean.setTo("MainActivity1");
                bean.setContent(new Random().nextInt() + "");
                Gson gson = new Gson();
                RxBus.send(gson.toJson(bean));
                AppUtils.showToast("消息发送成功,消息内容是:" + bean.getContent());
            }
        });
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestRxActivity2.this, ThirdActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
