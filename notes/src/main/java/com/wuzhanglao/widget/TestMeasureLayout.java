package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by wuming on 2017/1/16.
 */

public class TestMeasureLayout extends ScrollView {

    public TestMeasureLayout(Context context) {
        this(context, null);
    }

    public TestMeasureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TextView tv1 = new TextView(context);
        tv1.setText("tv1");
        tv1.setBackgroundColor(Color.RED);
        addView(tv1);
        TextView tv2 = new TextView(context);
        tv2.setText("tv2");
        tv2.setBackgroundColor(Color.GRAY);
        addView(tv2);
        TextView tv3 = new TextView(context);
        tv3.setText("tv3");
        tv3.setBackgroundColor(Color.YELLOW);
        addView(tv3);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);

        Log.d("123123123", "width->" + width + " width_mode->" + width_mode + " height->" + height + " height_mode->" + height_mode);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(480 - i * 100, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(480 - i * 100, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("123123123", "onLayout");

        int currentLeft = 0;
        int currentTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(currentLeft, currentTop, currentLeft + child.getMeasuredWidth(), currentTop + child.getMeasuredHeight());
            currentLeft = 0;
            currentTop = child.getBottom();
        }
    }
}
