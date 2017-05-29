package com.wuzhanglao.niubi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.widget.HorizontalProgressView;
import com.wuzhanglao.niubi.widget.TimerView;

/**
 * Created by ming.wu@shanbay.com on 2017/4/27.
 */

public class MultiItemFragment extends BaseFragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_multi_item, container, false);

		TimerView timerView = (TimerView) rootView.findViewById(R.id.timer_view);
		timerView.setCountDownTime(1, 0, 0, 10);
		timerView.setOnFinishedListener(new TimerView.OnFinishedListener() {
			@Override
			public void onFinished() {
				AppUtils.showToast("finish");
			}
		});

		HorizontalProgressView progressView = (HorizontalProgressView) rootView.findViewById(R.id.progress);
		progressView.setMaxProgress(100);
		progressView.setProgress(50);

		progressView.setProgressForegroundColor(Color.GRAY);
		progressView.setProgressBackgroundColor(Color.GREEN);
		return rootView;
	}
}
