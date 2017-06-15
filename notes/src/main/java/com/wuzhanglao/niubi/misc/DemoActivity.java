package com.wuzhanglao.niubi.misc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;

/**
 * Created by ming.wu@shanbay.com on 2017/6/15.
 */

public class DemoActivity extends BaseActivity {

	private static DemoView sDemoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initTranslucentStatusBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		((ViewGroup) findViewById(R.id.demo_activity_root)).addView(sDemoView.getView());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		sDemoView = null;
	}

	public static void start(Activity activity, DemoView demoView) {
		sDemoView = demoView;
		Intent intent = new Intent(activity, DemoActivity.class);
		activity.startActivity(intent);
	}

	public interface DemoView {

		String getTitle();

		View getView();
	}
}
