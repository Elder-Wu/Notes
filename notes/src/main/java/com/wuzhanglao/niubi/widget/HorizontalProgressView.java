package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wuzhanglao.niubi.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ming.wu@shanbay.com on 2017/5/24.
 */

public class HorizontalProgressView extends View {

	public static final int RECT = 0;
	public static final int ROUND = 1;

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({RECT, ROUND})
	public @interface Type {

	}

	private static final int DEFAULT_PROGRESS = 0;
	private static final int DEFAULT_MAX_PROGRESS = 100;
	private static final int DEFAULT_FOREGROUND_TYPE = RECT;
	private static final int DEFAULT_BACKGROUND_TYPE = RECT;
	private static final int DEFAULT_FOREGROUND_COLOR = Color.RED;
	private static final int DEFAULT_BACKGROUND_COLOR = Color.GRAY;

	private float mProgress;
	private float mMaxProgress;
	private int mForegroundType;
	private int mBackgroundType;
	private int mForegroundColor;
	private int mBackgroundColor;

	private Paint mPaint;
	private RectF mRectF = new RectF();
	private PorterDuffXfermode mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

	public HorizontalProgressView(Context context) {
		this(context, null);
	}

	public HorizontalProgressView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressView);
		mProgress = ta.getInteger(R.styleable.HorizontalProgressView_progress, DEFAULT_PROGRESS);
		mMaxProgress = ta.getInteger(R.styleable.HorizontalProgressView_maxProgress, DEFAULT_MAX_PROGRESS);
		mForegroundType = ta.getInteger(R.styleable.HorizontalProgressView_foregroundType, DEFAULT_FOREGROUND_TYPE);
		mBackgroundType = ta.getInteger(R.styleable.HorizontalProgressView_backgroundType, DEFAULT_BACKGROUND_TYPE);
		mForegroundColor = ta.getInteger(R.styleable.HorizontalProgressView_foregroundColor, DEFAULT_FOREGROUND_COLOR);
		mBackgroundColor = ta.getInteger(R.styleable.HorizontalProgressView_backgroundColor, DEFAULT_BACKGROUND_COLOR);
		ta.recycle();

		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(0);
		mPaint.setStyle(Paint.Style.FILL);

		setProgressInternal(Float.valueOf(mProgress).intValue());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mRectF.set(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);

		canvas.translate(0, 0);
		mPaint.setXfermode(null);
		mPaint.setColor(mBackgroundColor);
		switch (mBackgroundType) {
			case RECT:
				canvas.drawRect(mRectF, mPaint);
				break;
			case ROUND:
				canvas.drawRoundRect(mRectF, mRectF.height() / 2.0f, mRectF.height() / 2.0f, mPaint);
				break;
		}

		canvas.translate((mRectF.width() / mMaxProgress) * mProgress - mRectF.width(), 0);
		mPaint.setXfermode(mPorterDuffXfermode);
		mPaint.setColor(mForegroundColor);
		switch (mForegroundType) {
			case RECT:
				canvas.drawRect(mRectF, mPaint);
				break;
			case ROUND:
				canvas.drawRoundRect(mRectF, mRectF.height() / 2.0f, mRectF.height() / 2.0f, mPaint);
				break;
		}

		canvas.restore();
	}

	public void setProgress(@IntRange(from = 0) int progress) {
		setProgressInternal(progress);
		invalidate();
	}

	private void setProgressInternal(int progress) {
		mProgress = progress < 0 ? 0 : progress;
		mProgress = progress > mMaxProgress ? mMaxProgress : progress;
	}

	public int getProgress() {
		return Float.valueOf(mProgress).intValue();
	}

	public void setMaxProgress(int progress) {
		mMaxProgress = progress;
		invalidate();
	}

	public int getMaxProgress() {
		return Float.valueOf(mMaxProgress).intValue();
	}

	public void setProgressForegroundColor(int color) {
		mForegroundColor = color;
		invalidate();
	}

	public void setProgressBackgroundColor(int color) {
		mBackgroundColor = color;
		invalidate();
	}

	public void setBackgroundType(@Type int type) {
		mBackgroundType = type;
		invalidate();
	}

	public void setForegroundType(@Type int type) {
		mForegroundType = type;
		invalidate();
	}
}
