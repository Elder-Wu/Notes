package com.wuzhanglao.niubi.misc;

import android.content.Intent;
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
		startActivity(new Intent(SplashActivity.this, HomeActivity.class));
		finish();
	}
}
