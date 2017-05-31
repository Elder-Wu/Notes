package com.wuzhanglao.niubi.misc;

import android.os.Bundle;

import com.wuzhanglao.niubi.base.BaseActivity;

/**
 * Created by ming.wu@shanbay.com on 2017/5/30.
 */

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initTranslucentStatusBar();
		super.onCreate(savedInstanceState);
//		getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);

//		setContentView(R.layout.activity_splash);
	}
}
