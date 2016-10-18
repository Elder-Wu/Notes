package com.example.customview.utils;

import android.util.TypedValue;

/**
 * Created by wuming on 16/10/14.
 */

public class UIUtils {
    private static long lastClickTime;

    public static int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, MyApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics());
    }

    public static boolean isDoubleClick() {
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) > 500) {
            lastClickTime = currentClickTime;
            return false;
        }
        return true;
    }
}
