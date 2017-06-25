package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.utils.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ming.wu@shanbay.com on 2017/6/12.
 */

public class RoundCornerLayout extends FrameLayout {

	public static final int ROUND = 1;
	public static final int RECT = 2;

	@IntDef({ROUND, RECT})
	@Retention(RetentionPolicy.SOURCE)
	public @interface TYPE {

	}

	private static final int DEFAULT_BACKGROUND_COLOR = Color.LTGRAY;
	private static final int DEFAULT_BACKGROUND_TYPE = ROUND;
	private static final int DEFAULT_BACKGROUND_RADIUS = 0;

	private RectF rectF = new RectF();
	private Paint mPaint = new Paint();

	private int mBackgroundColor;
	private int mCornerType;
	private int mCornerRadius;

	public RoundCornerLayout(@NonNull Context context) {
		this(context, null);
	}

	public RoundCornerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.RoundCornerLayout);
		mBackgroundColor = ta.getColor(R.styleable.RoundCornerLayout_viewColor, DEFAULT_BACKGROUND_COLOR);
		mCornerType = ta.getInt(R.styleable.RoundCornerLayout_cornerType, DEFAULT_BACKGROUND_TYPE);
		mCornerRadius = ta.getDimensionPixelSize(R.styleable.RoundCornerLayout_cornerRadius, DEFAULT_BACKGROUND_RADIUS);
		ta.recycle();

		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(0);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(mBackgroundColor);

		setWillNotDraw(false);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
	}

	public void setCornerType(@TYPE int cornerType) {
		mCornerType = cornerType;
		invalidate();
	}

	public void setCornerRadius(int dip) {
		mCornerRadius = UIUtils.dp2px(dip).intValue();
		invalidate();
	}

	public void setViewColor(int color) {
		mBackgroundColor = color;
		mPaint.setColor(mBackgroundColor);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		float radius = 0;
		switch (mCornerType) {
			case ROUND:
				radius = rectF.height() / 2.0f;
				break;
			case RECT:
				radius = mCornerRadius;
				break;
		}
		canvas.drawRoundRect(rectF, radius, radius, mPaint);
		super.onDraw(canvas);
	}
}
