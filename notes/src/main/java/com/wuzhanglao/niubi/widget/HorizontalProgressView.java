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
	private static final int DEFAULT_BACKGROUND_COLOR = Color.LTGRAY;

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
		mForegroundType = ta.getInteger(R.styleable.HorizontalProgressView_foregroundProgressType, DEFAULT_FOREGROUND_TYPE);
		mBackgroundType = ta.getInteger(R.styleable.HorizontalProgressView_backgroundProgressType, DEFAULT_BACKGROUND_TYPE);
		mForegroundColor = ta.getInteger(R.styleable.HorizontalProgressView_foregroundProgressColor, DEFAULT_FOREGROUND_COLOR);
		mBackgroundColor = ta.getInteger(R.styleable.HorizontalProgressView_backgroundProgressColor, DEFAULT_BACKGROUND_COLOR);
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
		// 1.保存系统的默认canvas图层
		canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);

		// 2.在新建的图层上绘制内容
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

		// 3.将两个图层进行组合
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

	public void setForegroundProgressColor(int color) {
		mForegroundColor = color;
		invalidate();
	}

	public void setBackgroundProgressColor(int color) {
		mBackgroundColor = color;
		invalidate();
	}

	public void setBackgroundProgressType(@Type int type) {
		mBackgroundType = type;
		invalidate();
	}

	public void setForegroundProgressType(@Type int type) {
		mForegroundType = type;
		invalidate();
	}
}
