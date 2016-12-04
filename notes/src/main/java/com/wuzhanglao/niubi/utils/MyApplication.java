package com.wuzhanglao.niubi.utils;

import android.app.Application;
import android.media.MediaPlayer;

import com.squareup.leakcanary.LeakCanary;

import java.io.IOException;

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
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource("https://words-audio.baydn.com/us%2Fa%2Fad%2Fadditional.mp3");
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
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
