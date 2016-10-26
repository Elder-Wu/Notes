package com.example.notes.utils;

import android.app.Application;

/**
 * Created by wuming on 16/10/13.
 */

public class MyApplication extends Application {
    private static  MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance(){
        return instance;
    }
}
