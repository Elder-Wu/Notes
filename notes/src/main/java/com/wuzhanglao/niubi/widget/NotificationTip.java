package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

/*
 * Created by ming.wu@shanbay.com on 2017/1/25.
 */
public class NotificationTip extends android.support.v7.widget.AppCompatTextView {

	private Paint paint = new Paint();

	public NotificationTip(Context context) {
		this(context, null);
	}

	public NotificationTip(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setDither(true);
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
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
		canvas.drawCircle(width / 2, height / 2, min / 2, paint);
		ViewCompat.setBackground(this, null);
		super.onDraw(canvas);
	}

	public void update(int num) {
		if (num >= 100) {
			setText("99+");
		} else {
			setText(String.valueOf(num));
		}
		requestLayout();
	}
}