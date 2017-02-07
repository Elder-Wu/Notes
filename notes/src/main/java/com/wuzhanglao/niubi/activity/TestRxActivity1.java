package com.wuzhanglao.niubi.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.bean.RxBusBean;

/*
 * Created by wuming on 2016/12/2.
 */

public class TestRxActivity1 extends ToolbarActivity {

    private TextView rxbus_tv;

    @Override
    protected void doOnNext(Object o) {
        if (rxbus_tv != null) {
            Gson gson = new Gson();
            RxBusBean bean = gson.fromJson(o.toString(), RxBusBean.class);
            if (bean.getTo().equals("MainActivity1")) {
                rxbus_tv.setText("接收到消息->" + bean.getContent());
            }
        }
    }

    @Override
    protected int setContentResId() {
        return R.layout.activity_test_rxbus1;
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initDefaultToolBar();
        setToolbarTitle("MainActivity1");
        setToolbarBackVisible(false);
    }

    @Override
    protected void initView() {
        rxbus_tv = (TextView) findViewById(R.id.activity_rxbus1_tv);
        findViewById(R.id.activity_rxbus1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TestRxActivity2.class);
                startActivity(intent);
            }
        });
    }
}
