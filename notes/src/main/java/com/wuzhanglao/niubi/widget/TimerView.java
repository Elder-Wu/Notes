package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.wuzhanglao.niubi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * date:2017/5/20
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

public class TimerView extends View {

    private static final int DEFAULT_TEXT_SIZE = 14; //单位：dp
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    private int mWidth;
    private int mHeight;
    private int mMaxDightWidth;
    private int mMaxDightHeight;
    private int mGap;
    private int mRemainedMillisecond;
    private int mTextColor;
    private float mTextSize;

    private Paint mPaint;
    private OnFinishedListener mListener;
    private List<Point> mPointList = new ArrayList<>();
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public TimerView(Context context) {
        this(context, null);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimerView);
        mTextColor = ta.getColor(R.styleable.TimerView_android_textColor, DEFAULT_TEXT_COLOR);
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE, getResources().getDisplayMetrics());
        mTextSize = ta.getDimensionPixelSize(R.styleable.TimerView_android_textSize, Math.round(mTextSize));
        ta.recycle();

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);

        for (int i = 0; i < 11; i++) {
            mPointList.add(new Point());
        }

        setTextSizeInternal(mTextSize);
        isInEditMode();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Point point : mPointList) {
            canvas.drawText(point.text, point.x, point.y, mPaint);
        }
    }

    public void setCountDownTime(int milllsecond) {
        mRemainedMillisecond = milllsecond;
    }

    public void setCountDownTime(int hour, int minute, int second, int millisecond) {
        mRemainedMillisecond = ((hour * 60 + minute) * 60 + second) * 1000 + millisecond;
        handleTime();
    }

    public void start() {
        mCompositeSubscription.clear();
        mCompositeSubscription.add(Observable.interval(0, 10, TimeUnit.MILLISECONDS)
                .take(mRemainedMillisecond / 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onNext(Long time) {
                        handleTime();
                    }

                    @Override
                    public void onCompleted() {
                        if (mListener != null) {
                            mListener.onFinished();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    private void handleTime() {
        mRemainedMillisecond -= 10;
        int hour = mRemainedMillisecond / 1000 / 60 / 60;
        int minute = (mRemainedMillisecond / 1000 / 60) % 60;
        int second = mRemainedMillisecond / 1000 % 60;
        int millisecond = (mRemainedMillisecond % 1000) / 10;

        //计算出每个字符的x,y坐标
        for (int i = 0; i < mPointList.size(); i++) {
            Point point = mPointList.get(i);
            point.x = mMaxDightWidth / 2.0f + mMaxDightWidth * i + getPaddingLeft();
            point.y = mHeight - getPaddingBottom();

            if ((i + 1) % 3 == 0 && i != mPointList.size() - 1) {
                point.text = ":";
            } else if (i < 3) {
                //时
                point.text = String.valueOf(i % 2 == 0 ? hour / 10 : hour % 10);
            } else if (i < 6) {
                //分
                point.text = String.valueOf(i % 2 == 0 ? minute % 10 : minute / 10);
            } else if (i < 9) {
                //秒
                point.text = String.valueOf(i % 2 == 0 ? second / 10 : second % 10);
            } else {
                //毫秒
                point.text = String.valueOf(i % 2 == 0 ? millisecond % 10 : millisecond / 10);
            }
        }
        invalidate();
    }

    public void stop() {
        mCompositeSubscription.clear();
    }

    public void setTimerColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置计时字体大小和是否加粗
     *
     * @param size   单位：dp
     * @param isbold
     */
    public void setTimerSize(int size, boolean isbold) {
        setTextSizeInternal(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics()));
        if (isbold) {
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            mPaint.setTypeface(Typeface.DEFAULT);
        }
        requestLayout();
    }

    private void setTextSizeInternal(float textSize) {
        mMaxDightWidth = Integer.MIN_VALUE;
        mMaxDightHeight = Integer.MIN_VALUE;

        mPaint.setTextSize(textSize);
        mGap = (int) Math.ceil(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().descent);

        for (int i = 0; i < mPointList.size(); i++) {
            Rect rect = new Rect();
            if (i == mPointList.size() - 1) {
                mPaint.getTextBounds(":", 0, 1, rect);
            } else {
                mPaint.getTextBounds(String.valueOf(i), 0, 1, rect);
            }
            mMaxDightWidth = mMaxDightWidth > rect.width() ? mMaxDightWidth : rect.width();
            mMaxDightHeight = mMaxDightHeight > rect.height() ? mMaxDightHeight : rect.height();
        }

        //00:00:00:00   这种格式的时间，一共有11个字符
        mHeight = mMaxDightHeight + getPaddingTop() + getPaddingBottom() + mGap;
        mWidth = mMaxDightWidth * mPointList.size() + getPaddingLeft() + getPaddingRight();
    }

    public void setOnFinishedListener(OnFinishedListener listener) {
        mListener = listener;
    }

    public boolean isRunning() {
        return mCompositeSubscription.hasSubscriptions();
    }

    public interface OnFinishedListener {
        void onFinished();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCompositeSubscription.clear();
    }

    private class Point {
        private float x;
        private float y;
        private String text = "";
    }

    @Override
    public boolean isInEditMode() {
        setCountDownTime(59, 59, 59, 999);
        handleTime();
        return super.isInEditMode();
    }
}
