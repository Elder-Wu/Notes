package com.wuzhanglao.niubi.misc;

import android.os.Bundle;

import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.home.HomeActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ming.wu@shanbay.com on 2017/5/30.
 */

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Observable.timer(1, TimeUnit.MICROSECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Long>() {

					@Override
					public void call(Long aLong) {
						overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
						HomeActivity.launch(SplashActivity.this);
						finish();
					}
				});
	}
}
