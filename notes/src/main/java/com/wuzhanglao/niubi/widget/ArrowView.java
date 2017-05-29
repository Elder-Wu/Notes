package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * date:2017/5/21
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

public class ArrowView extends View {

    private Paint mPaint;

    public ArrowView(Context context) {
        this(context, null);
    }

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getMeasuredHeight() / 2.0f);
        path.lineTo(getMeasuredWidth(), getMeasuredHeight() / 2.0f);
        canvas.drawPath(path, mPaint);

        Path path1 = new Path();
        path.moveTo(getMeasuredWidth(), getMeasuredHeight() / 2.0f);
        path.lineTo(getMeasuredWidth() - 100, getMeasuredHeight() / 2.0f - 50);
        path.lineTo(getMeasuredWidth() - 100, getMeasuredHeight() / 2.0f + 50);
        path.close();
        canvas.drawPath(path1,mPaint);

    }
}
