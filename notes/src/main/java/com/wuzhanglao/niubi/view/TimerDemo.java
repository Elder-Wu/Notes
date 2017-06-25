package com.wuzhanglao.niubi.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.misc.DemoActivity;
import com.wuzhanglao.niubi.utils.ToastUtil;
import com.wuzhanglao.niubi.widget.RoundDrawable;
import com.wuzhanglao.niubi.widget.TimerView;

/**
 * Created by ming.wu@shanbay.com on 2017/6/25.
 */

public class TimerDemo implements DemoActivity.DemoView {

	private Activity mActivity;
	private View mRoot;

	public TimerDemo(@NonNull Activity activity) {
		mActivity = activity;

		mRoot = activity.getLayoutInflater().inflate(R.layout.layout_demo_timer, null);
		Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.avatar);
		ViewCompat.setBackground(mRoot, new RoundDrawable(bitmap));

		final TimerView timerView = (TimerView) mRoot.findViewById(R.id.timer_view);
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
				ToastUtil.showInfo("finished");
			}
		});
	}

	@Override
	public View getView() {
		return mRoot;
	}

	@Override
	public String getTitle() {
		return getClass().getSimpleName();
	}
}
