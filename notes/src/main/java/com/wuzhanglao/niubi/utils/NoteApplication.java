package com.wuzhanglao.niubi.utils;

import android.app.Application;

/**
 * Created by wuming on 16/10/13.
 */

public class NoteApplication extends Application {
    private static NoteApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static NoteApplication getInstance() {
        return instance;
    }
}
