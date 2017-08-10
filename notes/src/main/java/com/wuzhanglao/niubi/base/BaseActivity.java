package com.wuzhanglao.niubi.base;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/*
 * Created by wuming on 16/10/13.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
//		MobclickAgent.onPause(this);
	}
}
