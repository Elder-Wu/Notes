package com.wuzhanglao.niubi.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.misc.DemoActivity;

/**
 * Created by ming.wu@shanbay.com on 2017/6/15.
 */

public class HorizontalProgressDemo implements DemoActivity.DemoView {

	private View mRoot;
	private BaseActivity mActivity;

	public HorizontalProgressDemo(final BaseActivity activity) {
		mActivity = activity;
		mRoot = activity.getLayoutInflater().inflate(R.layout.layout_demo_horizontal_progress, (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT), false);
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
