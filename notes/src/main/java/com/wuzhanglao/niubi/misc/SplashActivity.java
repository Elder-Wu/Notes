package com.wuzhanglao.niubi.misc;

import android.os.Bundle;

import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.home.HomeActivity;

/**
 * Created by ming.wu@shanbay.com on 2017/5/30.
 */

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		HomeActivity.launch(SplashActivity.this);
		finish();
	}
}
