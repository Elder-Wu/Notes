package com.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long lastTime;

    private CountDownView count_down_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        count_down_view = (CountDownView) findViewById(R.id.countDownView);
        count_down_view.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {
            @Override
            public void onStartCount() {
                Toast.makeText(getApplicationContext(),"开始计时",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinishCount() {
                Toast.makeText(getApplicationContext(),"计时结束",Toast.LENGTH_SHORT).show();
            }
        });
        count_down_view.setOnClickListener(this);
    }

    //连按两次退出应用程序
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime < 2 * 1000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "请再按一次", Toast.LENGTH_SHORT).show();
            lastTime = currentTime;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.countDownView:
                count_down_view.start();
                break;
        }
    }
}
