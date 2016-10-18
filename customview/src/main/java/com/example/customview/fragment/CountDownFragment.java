package com.example.customview.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.ToastUtils;
import com.example.customview.widget.CountDownView;

/**
 * Created by wuming on 16/10/18.
 */

public class CountDownFragment extends BaseFragment {

    private CountDownView countDownView;

    @Override
    protected int setResId() {
        return R.layout.fragment_countdown;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        countDownView = (CountDownView) view.findViewById(R.id.fragment_count_down);
        countDownView.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {
            @Override
            public void onStartCount() {
                ToastUtils.show("开始了");
            }

            @Override
            public void onFinishCount() {
                ToastUtils.show("结束了");
            }
        });
        view.findViewById(R.id.fragment_count_down_start_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                countDownView.start();
            }
        });
    }
}
