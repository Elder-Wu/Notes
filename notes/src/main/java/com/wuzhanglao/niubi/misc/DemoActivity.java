package com.wuzhanglao.niubi.misc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.base.DefaultToolbar;

/**
 * Created by ming.wu@shanbay.com on 2017/6/15.
 */

public class DemoActivity extends BaseActivity {

	private static DemoView sDemoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		DefaultToolbar toolbar = new DefaultToolbar(this);
		toolbar.setTitle(sDemoView.getTitle());
		((ViewGroup) findViewById(R.id.demo_activity_root)).addView(sDemoView.getView());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		sDemoView = null;
	}

	public static void launch(Activity activity, DemoView demoView) {
		sDemoView = demoView;
		Intent intent = new Intent(activity, DemoActivity.class);
		activity.startActivity(intent);
	}

	public interface DemoView {

		View getView();

		String getTitle();
	}
}
