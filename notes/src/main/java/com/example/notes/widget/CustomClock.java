package com.example.notes.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义钟表表盘
 * Created by wuming on 16/9/28.
 */
public class CustomClock extends View {

    private static final String TAG = "CustomClock";

    private Paint paint;
    private float textHeight;

    public CustomClock(Context context) {
        this(context, null);
    }

    public CustomClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        if (width_mode == MeasureSpec.AT_MOST) {
            width = 400;
        }
        if (height_mode == MeasureSpec.AT_MOST) {
            height = 400;
        }
        setMeasuredDimension(width, height);
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(15);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        textHeight = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        for (int i = 1; i <= 12; i++) {
            canvas.rotate(30, width / 2, height / 2);
            canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 - radius + 15, paint);
            canvas.drawText(i + "", width / 2 - paint.measureText(i + "") / 2, height / 2 - radius + 15 + textHeight, paint);
        }

        canvas.rotate(-30, width / 2, height / 2);
        paint.setColor(Color.BLACK);
        canvas.drawLine(width / 2, width / 2, height / 2, width / 2, paint);

    }
}
