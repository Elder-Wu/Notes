package com.wuzhanglao.niubi.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * date:2017/5/20
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

public class TimerView extends View {
    private long mTime;
    private long mStartSystemTime;
    private boolean shouldDraw = true;
    private Paint mPaint;

    private int mWidth;
    private int mHeight;


    private OnFinishedListener listener;

    public TimerView(Context context) {
        this(context, null);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setTextSizeInternal();
    }

    public void setTimeColor(int color) {
        mPaint.setColor(color);
    }

    public void setTextSize() {

    }

    private void setTextSizeInternal() {
        int maxDightWidth = Integer.MIN_VALUE;
        int maxDightHeight = Integer.MIN_VALUE;

        for (int i = 0; i <= 10; i++) {
            Rect rect = new Rect();
            if (i == 10) {
                mPaint.getTextBounds(":", 0, 1, rect);
            } else {
                mPaint.getTextBounds(String.valueOf(i), 0, 1, rect);
            }
            maxDightWidth = maxDightWidth > rect.width() ? maxDightWidth : rect.width();
            maxDightHeight = maxDightHeight > rect.height() ? maxDightHeight : rect.height();
        }

        //00:00:00:00   这种格式的时间，一共有11个字符
        mWidth = maxDightWidth * 11;
        mHeight = maxDightHeight;
    }

    /**
     * 设置计时字体大小和是否加粗
     *
     * @param size
     * @param isbold
     */
    public void setTimeSize(int size, boolean isbold) {
        mPaint.setTextSize(size);
        if (isbold) {
            Typeface font = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
            mPaint.setTypeface(font);
        }
    }

    public void setTime(long milliseconds) {
        mTime = milliseconds;
        if (mStartSystemTime == 0) {
            mStartSystemTime = SystemClock.elapsedRealtime();
        }
    }

    public void setOnFinishedListener(OnFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint.setTextSize(1.0f);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        int maxDightWidth = Integer.MIN_VALUE;
        int maxDightHeight = Integer.MIN_VALUE;

        for (int i = 0; i <= 10; i++) {
            Rect rect = new Rect();
            if (i == 10) {
                mPaint.getTextBounds(":", 0, 1, rect);
            } else {
                mPaint.getTextBounds(String.valueOf(i), 0, 1, rect);
            }
            maxDightWidth = maxDightWidth > rect.width() ? maxDightWidth : rect.width();
            maxDightHeight = maxDightHeight > rect.height() ? maxDightHeight : rect.height();
        }

        //00:00:00:00   这种格式的时间，一共有11个字符


        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();


        mWidth = w;
        mHeight = h;
    }

//    private int measureWidthWrap() {
//        return (int) mPaint.measureText("00:00:00:00");
//    }

    private int measureDimensionHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);//模式
        int specSize = MeasureSpec.getSize(measureSpec);//大小

        int size = 0;

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                //要多大 有多大 一般是用不上
                size = measureHeightWrap();
                break;
            case MeasureSpec.EXACTLY:
                //是多大用多大
                size = specSize;
                break;
            case MeasureSpec.AT_MOST:
                //在是多大和要多大选一个小的。
                size = Math.min(specSize, measureHeightWrap());
                break;
        }
        return size;
    }


    private int measureHeightWrap() {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        return fontMetrics.bottom - fontMetrics.top;
    }

    private int measureWidthWrap() {
        return (int) mPaint.measureText("00000000000");
    }
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (shouldDraw) {
//            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
//            String time;
//            long remain = mTime - (SystemClock.elapsedRealtime() - mStartSystemTime);
//            if (remain < 0) {
//                shouldDraw = false;
//                time = "00:00:00:00";
//                if (listener != null) {
//                    listener.onFinished();
//                }
//            } else {
//                shouldDraw = true;
//                time = formatTime(mTime - (SystemClock.elapsedRealtime() - mStartSystemTime));
//            }
//            canvas.drawText(time, (mWidth - (int) mPaint.measureText(time)) / 2, (getMeasuredHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent), mPaint);
//            if (shouldDraw) {
//                invalidate();
//            }
//        } else {
//            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
//            canvas.drawText("00:00:00:00", (mWidth - (int) mPaint.measureText("00:00:00:00")) / 2, (getMeasuredHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent), mPaint);
//        }
//    }

    private int mHour;
    private int mMinute;
    private int mSecond;
    private int mMillisecond;
    private long mTotalMillisecond;

    public void setCountDownTime(int hour, int minute, int second, int millisecond) {
        mHour = hour;
        mMinute = minute;
        mSecond = second;
        mMillisecond = millisecond;
        mTotalMillisecond = ((mHour * 60 + mMinute) * 60 + mSecond) * 1000;

        startCountDown();
    }

    private void startCountDown() {
        Observable.interval(10, TimeUnit.MILLISECONDS)
                .take(((mHour * 60 + mMinute) * 60 + mSecond) / 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long time) {

                    }
                });
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        long hour = milliseconds / (3600 * 1000);
//        long minute = (milliseconds % (3600 * 1000)) / (60 * 1000);
//        long second = ((milliseconds % (3600 * 1000)) % (60 * 1000)) / 1000;
//        long mills = (((milliseconds % (3600 * 1000)) % (60 * 1000)) % 1000) / 10;

        //画"时"
            //画"分"
            //画"秒"
            //画"毫秒"
            if (shouldDraw) {
                Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
                ArrayList<String> times = new ArrayList<>();
                long remain = mTime - (SystemClock.elapsedRealtime() - mStartSystemTime);
                if (remain < 0) {
                    shouldDraw = false;
                    times.add("0");
                    times.add("0");
                    times.add(":");
                    times.add("0");
                    times.add("0");
                    times.add(":");
                    times.add("0");
                    times.add("0");
                    times.add(":");
                    times.add("0");
                    times.add("0");
                    if (listener != null) {
                        listener.onFinished();
                    }
                } else {
                    shouldDraw = true;
                    times.clear();
                    times.addAll(toStringWithFormat(mTime - (SystemClock.elapsedRealtime() - mStartSystemTime)));
                }
                for (int i = 0; i < times.size(); i++) {
                    String time = times.get(i);
                    if (time.equals(":")) {
                        canvas.drawText(time, mPaint.measureText("0") * i + (mPaint.measureText("0") - mPaint.measureText(":")) / 2, (getMeasuredHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent), mPaint);

                    } else {
                        canvas.drawText(time, mPaint.measureText("0") * i, (getMeasuredHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent), mPaint);
                    }
                }
                if (shouldDraw) {
                    invalidate();
                }
            } else {
                Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
                canvas.drawText("00:00:00:00", (mWidth - (int) mPaint.measureText("00:00:00:00")) / 2, (getMeasuredHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent), mPaint);
            }
    }


    public interface OnFinishedListener {
        void onFinished();
    }

//    private String formatTime(long milliseconds) {
//
//        StringBuffer returnDate = new StringBuffer();
//
//        long hour = milliseconds / (3600 * 1000);
//        long minute = (milliseconds % (3600 * 1000)) / (60 * 1000);
//        long second = ((milliseconds % (3600 * 1000)) % (60 * 1000)) / 1000;
//        long mills = (((milliseconds % (3600 * 1000)) % (60 * 1000)) % 1000) / 10;
//
//        returnDate.append(hour > 9 ? String.valueOf(hour) : 0 + String.valueOf(hour));
//        returnDate.append(":");
//        returnDate.append(minute > 9 ? String.valueOf(minute) : 0 + String.valueOf(minute));
//        returnDate.append(":");
//        returnDate.append(second > 9 ? String.valueOf(second) : 0 + String.valueOf(second));
//        returnDate.append(":");
//        returnDate.append(mills > 9 ? String.valueOf(mills) : 0 + String.valueOf(mills));
//
//        return returnDate.toString();
//    }

    private ArrayList<String> toStringWithFormat(long milliseconds) {
        ArrayList<String> arrays = new ArrayList<>();
        StringBuffer returnDate = new StringBuffer();

        long hour = milliseconds / (3600 * 1000);
        long minute = (milliseconds % (3600 * 1000)) / (60 * 1000);
        long second = ((milliseconds % (3600 * 1000)) % (60 * 1000)) / 1000;
        long mills = (((milliseconds % (3600 * 1000)) % (60 * 1000)) % 1000) / 10;

        returnDate.append(hour > 9 ? String.valueOf(hour) : 0 + String.valueOf(hour));
        returnDate.append(":");
        returnDate.append(minute > 9 ? String.valueOf(minute) : 0 + String.valueOf(minute));
        returnDate.append(":");
        returnDate.append(second > 9 ? String.valueOf(second) : 0 + String.valueOf(second));
        returnDate.append(":");
        returnDate.append(mills > 9 ? String.valueOf(mills) : 0 + String.valueOf(mills));

        for (int i = 0; i < returnDate.toString().length(); i++) {
            if (i < returnDate.toString().length() - 1) {
                arrays.add(returnDate.substring(i, i + 1));
            } else {
                arrays.add(returnDate.substring(i));
            }
        }
        return arrays;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
