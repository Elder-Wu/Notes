package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.widget.CountDownView;

/**
 * Created by wuming on 16/10/18.
 */

public class CountDownFragment extends BaseFragment {

    private CountDownView countDownView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_countdown, container, false);
        countDownView = (CountDownView) rootView.findViewById(R.id.fragment_count_down);
        countDownView.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {
            @Override
            public void onStartCount() {
                AppUtils.showToast("开始了");
            }

            @Override
            public void onFinishCount() {
                AppUtils.showToast("结束了");
            }
        });
        rootView.findViewById(R.id.fragment_count_down_start_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                countDownView.start();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownView.stop();
    }
}
