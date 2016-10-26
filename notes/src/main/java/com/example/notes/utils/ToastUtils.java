package com.example.notes.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by wuming on 16/10/13.
 */

public class ToastUtils {
    //全局Toast
    public static void show(String msg) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    //指定界面Toast
    public static void show(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
