package com.wuzhanglao.niubi.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * create by wuzhanglao at 2017/6/10
 */

public class ToastUtil {

	public static void showError(Context context, String msg) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}
		ToastView toastView = new ToastView(context, msg);
		toastView.setColor(Color.RED);
		Toast toast = new Toast(context);
		toast.setView(toastView);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void showWarn(Context context, String msg) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}
		ToastView toastView = new ToastView(context, msg);
		toastView.setColor(Color.YELLOW);
		Toast toast = new Toast(context);
		toast.setView(toastView);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void showInfo(Context context, String msg) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}
		Toast toast = new Toast(context);
		toast.setView(new ToastView(context, msg));
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	private static class ToastView extends FrameLayout {

		private TextView mTextView;
		private float mRadius;
		private RectF mRectF = new RectF();
		private Paint mPaint = new Paint();

		public ToastView(@NonNull Context context, CharSequence msg) {
			super(context);
			mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setAntiAlias(true);
			mPaint.setStrokeWidth(0);
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setColor(Color.LTGRAY);

			mTextView = new TextView(context);
			mTextView.setText(msg);
			addView(mTextView);
			FrameLayout.LayoutParams layoutParams = (LayoutParams) mTextView.getLayoutParams();
			layoutParams.gravity = Gravity.CENTER;
			mTextView.setLayoutParams(layoutParams);

			mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
			setWillNotDraw(false);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			mRectF.set(0, 0, mTextView.getMeasuredWidth(), mTextView.getMeasuredHeight());
		}

		public void setColor(int color) {
			mPaint.setColor(color);
			invalidate();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
			super.onDraw(canvas);
		}
	}
}
