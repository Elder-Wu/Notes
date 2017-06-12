package com.wuzhanglao.niubi.utils;

import android.util.TypedValue;

/**
 * Created by wuming on 16/10/14.
 */

public class UIUtils {
    private static long lastClickTime;

    public static boolean isDoubleClick(int interval) {
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) > interval) {
            lastClickTime = currentClickTime;
            return false;
        }
        return true;
    }

    public static int px2dp(float pxValue) {
        final float scale = NoteApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Float dp2px(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, NoteApplication.getInstance().getResources().getDisplayMetrics());
    }

    public static int px2sp(float pxValue) {
        final float fontScale = NoteApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = NoteApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
