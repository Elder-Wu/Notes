package com.wuzhanglao.niubi.utils;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

/**
 * Created by wuming on 16/10/13.
 */

public class MyApplication extends MultiDexApplication {
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

    }

    public static MyApplication getInstance(){
        return instance;
    }
}
