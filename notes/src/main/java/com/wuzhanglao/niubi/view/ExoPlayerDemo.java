package com.wuzhanglao.niubi.view;

import android.app.Activity;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.misc.DemoActivity;
import com.wuzhanglao.niubi.utils.ToastUtil;

/**
 * Created by ming.wu@shanbay.com on 2017/6/15.
 */

public class ExoPlayerDemo implements DemoActivity.DemoView {

	private View mRoot;

	public ExoPlayerDemo(final Activity activity) {
		mRoot = activity.getLayoutInflater().inflate(R.layout.layout_exo_player_demo, null);
		mRoot.findViewById(R.id.exo_player_demo_play).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtil.showInfo(activity, "播放音频");
			}
		});
	}

	@Override
	public String getTitle() {
		return getClass().getSimpleName();
	}

	@Override
	public View getView() {
		return mRoot;
	}
}
