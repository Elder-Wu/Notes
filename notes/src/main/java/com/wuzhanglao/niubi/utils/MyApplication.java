package com.wuzhanglao.niubi.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wuming on 16/10/13.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication instance;

    private static final Thread initSDKThread = new Thread() {
        @Override
        public void run() {
            //内存泄漏检测工具
            if (!LeakCanary.isInAnalyzerProcess(instance)) {
                LeakCanary.install(instance);
            }
        }
    };

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initSDKThread.start();
    }
}
