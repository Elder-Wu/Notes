package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.notes.R;
import com.wuzhanglao.niubi.utils.ToastUtils;
import com.wuzhanglao.niubi.widget.CountDownView;

/**
 * Created by wuming on 16/10/18.
 */

public class CountDownFragment extends BaseFragment {

    private CountDownView countDownView;

    @Override
    public int setResId() {
        return R.layout.fragment_countdown;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
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
