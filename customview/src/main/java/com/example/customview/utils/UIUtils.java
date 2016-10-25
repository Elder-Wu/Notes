package com.example.customview.utils;

/**
 * Created by wuming on 16/10/14.
 */

public class UIUtils {
    private static long lastClickTime;

    public static boolean isDoubleClick() {
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) > 500) {
            lastClickTime = currentClickTime;
            return false;
        }
        return true;
    }

    public static int px2dp(float pxValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(float dipValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = MyApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = MyApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
