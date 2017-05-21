package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

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

	private static final int DEFAULT_TEXT_SIZE = 44; //单位：dp

	private int mWidth;
	private int mHeight;
	private int mMaxDightWidth;
	private int mMaxDightHeight;

	private int mHour;
	private int mMinute;
	private int mSecond;
	private int mMillisecond;
	private int mRemainedMillisecond;

	private Paint mPaint;
	private OnFinishedListener mListener;
	private List<Point> mPointList = new ArrayList<>();
	private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

	public TimerView(Context context) {
		this(context, null);
	}

	public TimerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.RED);
		mPaint.setTextAlign(Paint.Align.CENTER);

		setTextSizeInternal(DEFAULT_TEXT_SIZE);

		for (int i = 0; i < 11; i++) {
			mPointList.add(new Point());
		}

		setBackgroundColor(Color.GREEN);
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
		startCountDown();
	}

	public void setCountDownTime(int hour, int minute, int second, int millisecond) {
		mRemainedMillisecond = ((hour * 60 + minute) * 60 + second) * 1000 + millisecond;
		startCountDown();
	}

	private void startCountDown() {
		mCompositeSubscription.clear();
		mCompositeSubscription.add(Observable.interval(0, 1, TimeUnit.SECONDS)
				.take(mRemainedMillisecond / 10)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<Long>() {

					@Override
					public void onNext(Long time) {
						mRemainedMillisecond -= 10;
						mHour = mRemainedMillisecond / 1000 / 60 / 60;
						mMinute = (mRemainedMillisecond / 1000 / 60) % 60;
						mSecond = mRemainedMillisecond / 1000 % 60;
						mMillisecond = (mRemainedMillisecond % 1000) / 10;
						Log.d("value", "mRemainedMillisecond->" + mRemainedMillisecond);
						Log.d("value", "mHour->" + mHour);
						Log.d("value", "mMinute->" + mMinute);
						Log.d("value", "mSecond->" + mSecond);
						Log.d("value", "mMillisecond->" + mMillisecond);
						Log.d("value", "           ");
						Log.d("value", "           ");
						Log.d("value", "           ");
						Log.d("value", "           ");
						Log.d("value", "           ");
						Log.d("value", "           ");
						Log.d("value", "           ");
						Log.d("value", "           ");

						//计算出每个字符的x,y坐标
						for (int i = 0; i < mPointList.size(); i++) {
							int value;
							if (i < 3) {
								value = mHour;
							} else if (i < 6) {
								value = mMinute;
							} else if (i < 9) {
								value = mSecond;
							} else {
								value = mMillisecond;
							}

							Point point = mPointList.get(i);
							point.x = mMaxDightWidth / 2.0f + mMaxDightWidth * i;
							point.y = mMaxDightHeight;
							if ((i + 1) % 3 == 0 && i != mPointList.size() - 1) {
								point.text = ":";
							} else if (i > mPointList.size() - 3) {
								point.text = String.valueOf(i % 2 == 0 ? value % 10 : value / 10);
							} else {
								point.text = String.valueOf(i % 2 == 0 ? value / 10 : value % 10);
							}
							Log.d("value", "text->" + point.text);
						}
						invalidate();
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
		setTextSizeInternal(size);
		if (isbold) {
			Typeface font = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
			mPaint.setTypeface(font);
		}
		requestLayout();
	}

	private void setTextSizeInternal(int textSize) {
		mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSize, getResources().getDisplayMetrics()));
		mMaxDightWidth = Integer.MIN_VALUE;
		mMaxDightHeight = Integer.MIN_VALUE;

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
		mWidth = mMaxDightWidth * mPointList.size();
		mHeight = mMaxDightHeight;
	}

	public void setOnFinishedListener(OnFinishedListener listener) {
		mListener = listener;
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
}
