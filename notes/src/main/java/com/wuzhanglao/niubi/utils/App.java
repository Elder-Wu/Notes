package com.wuzhanglao.niubi.utils;

import android.app.Application;

/**
 * Created by wuming on 16/10/13.
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
