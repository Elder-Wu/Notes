package com.wuzhanglao.niubi.utils;
/*
 * date:2017/2/16
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import android.os.Looper;
import android.widget.Toast;

public class AppUtils {
    public static void showToast(String msg) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
