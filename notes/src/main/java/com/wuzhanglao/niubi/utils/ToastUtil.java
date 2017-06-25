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

	public static void showError(String msg) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}
		ToastView toastView = new ToastView(NoteApplication.getInstance(), msg);
		toastView.setColor(Color.RED);
		Toast toast = new Toast(NoteApplication.getInstance());
		toast.setView(toastView);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void showWarn(String msg) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}
		ToastView toastView = new ToastView(NoteApplication.getInstance(), msg);
		toastView.setColor(Color.YELLOW);
		Toast toast = new Toast(NoteApplication.getInstance());
		toast.setView(toastView);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void showInfo(String msg) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}
		Toast toast = new Toast(NoteApplication.getInstance());
		toast.setView(new ToastView(NoteApplication.getInstance(), msg));
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
			mPaint.setColor(Color.GRAY);
			mPaint.setAlpha(230);

			mTextView = new TextView(context);
			mTextView.setText(msg);
			mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			addView(mTextView);
			FrameLayout.LayoutParams layoutParams = (LayoutParams) mTextView.getLayoutParams();
			layoutParams.gravity = Gravity.CENTER;
			mTextView.setLayoutParams(layoutParams);
			mTextView.setTextColor(Color.WHITE);
			int padding_5_dp = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
			int padding_10_dp = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
			mTextView.setPadding(padding_10_dp, padding_5_dp, padding_10_dp, padding_5_dp);

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
