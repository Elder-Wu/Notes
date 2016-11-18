package com.wuzhanglao.niubi.utils;

import android.app.Application;

/**
 * Created by wuming on 16/10/13.
 */

public class MyApplication extends Application {
    private static  MyApplication instance;

    private static final Thread initSDKThread = new Thread(){
        @Override
        public void run() {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initSDKThread.start();

    }

    public static MyApplication getInstance(){
        return instance;
    }
}
