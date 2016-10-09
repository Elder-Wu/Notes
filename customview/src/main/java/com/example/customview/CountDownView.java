package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created on 2016/10/8.
 */

public class CountDownView extends View {

    private static final String TAG = CountDownView.class.getSimpleName();
    private static final int BACKGROUND_COLOR = 0x50555555;
    private static final int BORDER_WIDTH = 15;
    private static final int BORDER_COLOR = 0xFF6ADBFE;
    private static final int TEXT_SIZE = 50;
    private static final int TEXT_COLOR = 0xFFFFFFFF;


    private Paint circlePaint;
    private TextPaint textPaint;
    private Paint borderPaint;

    private float progress;

    private CountDownTimerListener listener;

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
        circlePaint.setColor(BACKGROUND_COLOR);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);
        borderPaint.setColor(BORDER_COLOR);
        borderPaint.setStrokeWidth(BORDER_WIDTH);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int min = Math.min(width, height);
        //画底盘
        canvas.drawCircle(width / 2, height / 2, min / 2, circlePaint);

        //画边框
        RectF rectF;
        if (width > height) {
            rectF = new RectF(width / 2 - min / 2 + BORDER_WIDTH / 2, 0 + BORDER_WIDTH / 2, width / 2 + min / 2 - BORDER_WIDTH / 2, height - BORDER_WIDTH / 2);
        } else {
            rectF = new RectF(BORDER_WIDTH / 2, height / 2 - min / 2 + BORDER_WIDTH / 2, width - BORDER_WIDTH / 2, height / 2 - BORDER_WIDTH / 2 + min / 2);
        }
        canvas.drawArc(rectF, -90, progress, false, borderPaint);

        //画居中的文字
//        canvas.drawText("稍等片刻", width / 2, height / 2 - textPaint.descent() + textPaint.getTextSize() / 2, textPaint);
        canvas.drawText("稍等片刻", width / 2, height / 2 - textPaint.descent(), textPaint);
    }

    public void start() {
        CountDownTimer countDownTimer = new CountDownTimer(3600, 36) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress = ((3600 - millisUntilFinished) / 3600f) * 360;
                Log.d(TAG, "progress:" + progress);
                invalidate();
            }

            @Override
            public void onFinish() {
                progress = 360;
                invalidate();
                if (listener != null) {
                    listener.onFinishCount();
                }
            }
        }.start();
        if (listener != null) {
            listener.onStartCount();
        }
    }

    public void setCountDownTimerListener(CountDownTimerListener listener) {
        this.listener = listener;
    }

    public interface CountDownTimerListener {

        void onStartCount();

        void onFinishCount();
    }
}
