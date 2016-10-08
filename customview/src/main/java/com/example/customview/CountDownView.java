package com.example.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created on 2016/10/8.
 */

public class CountDownView extends View {

    private static final int BORDER_WIDTH = 10;

    private Paint circlePaint;
    private Paint textPaint;
    private Paint borderPaint;

    private float progress;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        circlePaint.setColor(Color.GRAY);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);
        borderPaint.setColor(Color.DKGRAY);
        borderPaint.setStrokeWidth(BORDER_WIDTH);
        borderPaint.setStyle(Paint.Style.STROKE);

        progress = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int min = Math.min(width, height);
        //画底盘
        canvas.drawCircle(width / 2, height / 2, min / 2, circlePaint);
        //画边框
        if (width > height) {
            RectF rectF = new RectF(width / 2 - min / 2, 0, width / 2 + min / 2, height);
            canvas.drawArc(rectF, 0, progress, true, borderPaint);
        } else {
            RectF rectF = new RectF(0, height / 2 - min / 2, width, height / 2 - min / 2);
            canvas.drawArc(rectF, 0, progress, true, borderPaint);
        }
        //画居中的文字
        canvas.drawText("稍等片刻", width / 2, height / 2 - textPaint.descent() + textPaint.getTextSize() / 2, textPaint);
    }

    public void start() {
        CountDownTimer countDownTimer = new CountDownTimer(3600, 60) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress = (3600 - millisUntilFinished) / 3600 * 360;
                invalidate();
            }

            @Override
            public void onFinish() {
                progress = 0;
                invalidate();
            }
        };
    }
}
