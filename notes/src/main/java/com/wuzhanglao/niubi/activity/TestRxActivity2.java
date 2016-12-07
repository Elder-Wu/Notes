package com.wuzhanglao.niubi.activity;

import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.utils.RxBus;
import com.wuzhanglao.niubi.bean.RxBusBean;
import com.wuzhanglao.niubi.utils.UIUtils;

import java.util.Random;

/**
 * Created by wuming on 2016/12/2.
 */

public class TestRxActivity2 extends ToolbarActivity {

    private TextView rxbus_tv;

    @Override
    protected void doOnNext(Object o) {
        if (rxbus_tv != null) {
            Gson gson = new Gson();
            RxBusBean bean = gson.fromJson(o.toString(), RxBusBean.class);
            if (bean.getTo().equals("MainActivity2")) {
                rxbus_tv.setText("接收到消息->" + bean.getContent());
            }
        }
    }

    @Override
    protected int setContentResId() {
        return R.layout.activity_test_rxbus2;
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initDefaultToolBar();
        setToolbarTitle("MainActivity2");
        setToolbarBackVisible(false);
    }

    @Override
    protected void initView() {
        rxbus_tv = (TextView) findViewById(R.id.activity_rxbus2_tv);
        findViewById(R.id.activity_rxbus2_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RxBusBean bean = new RxBusBean();
                bean.setFrom("MainActivity2");
                bean.setTo("MainActivity1");
                bean.setContent(new Random().nextInt() + "");
                Gson gson = new Gson();
                RxBus.send(gson.toJson(bean));
                UIUtils.showToast("消息发送成功,消息内容是:" + bean.getContent());
            }
        });
    }
}
