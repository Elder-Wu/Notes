package com.wuzhanglao.niubi.utils;
/*
 * date:2017/2/16
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import android.os.Looper;

import es.dmoral.toasty.Toasty;

public class AppUtils {
    public static void showToast(String msg) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toasty.info(NoteApplication.getInstance(), msg, 2).show();
        }
    }
}
