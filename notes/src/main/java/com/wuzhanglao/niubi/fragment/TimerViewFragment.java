package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.widget.TimerView;

/**
 * Created by ming.wu@shanbay.com on 2017/4/27.
 */

public class TimerViewFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timer_view, container, false);

        final TimerView timerView = (TimerView) rootView.findViewById(R.id.timer_view);
        timerView.setTimerSize(18, false);
        timerView.setCountDownTime(0, 0, 10, 10);
        timerView.start();

        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerView.isRunning()) {
                    timerView.stop();
                } else {
                    timerView.start();
                }
            }
        });
        timerView.setOnFinishedListener(new TimerView.OnFinishedListener() {
            @Override
            public void onFinished() {
                AppUtils.showToast("finish");
            }
        });
        return rootView;
    }
}
