package com.example.customview.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by wuming on 16/10/14.
 */

public class UIUtils {
    public static int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, MyApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, context.getResources().getDisplayMetrics());
    }
}
