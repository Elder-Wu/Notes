package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/*
 * Created by ming.wu@shanbay.com on 2017/1/25.
 */
public class NumberTipView extends android.support.v7.widget.AppCompatTextView {

	private Paint mPaint = new Paint();

	public NumberTipView(Context context) {
		this(context, null);
	}

	public NumberTipView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setFilterBitmap(true);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Color.RED);
		setTextColor(Color.WHITE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (getText().toString().isEmpty() || getText().toString().equals("0")) {
			setMeasuredDimension(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int min = Math.min(width, height);
		canvas.drawCircle(width / 2, height / 2, min / 2, mPaint);
		setBackground(null);
		super.onDraw(canvas);
	}

	public void setTipBackgroundColor(int color) {
		mPaint.setColor(color);
		invalidate();
	}

	public void render(int num) {
		if (num >= 100) {
			setText("99+");
		} else {
			setText(String.valueOf(num));
		}
		requestLayout();
	}
}