package com.wuzhanglao.niubi.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wuming on 16/10/13.
 */

public class NoteApplication extends Application {
	private static NoteApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		LeakCanary.install(this);
	}

	public static NoteApplication getInstance() {
		return instance;
	}
}
