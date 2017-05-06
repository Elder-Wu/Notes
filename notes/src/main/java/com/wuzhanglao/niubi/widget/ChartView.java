package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ming.wu@shanbay.com on 2017/5/3.
 */

public class ChartView extends View {

	private static final int DEFAULT_TEXT_SIZE = 10;

	private List<Data> mRawDataList;
	private float mStartX;
	private float mStartY;

	private float mViewHeight;
	private float mViewWidth;

	//这是绘制文字的笔
	private Paint mTextPaint;

	private boolean mIsThumbnailMode = false;

	private Map<String, Float> mAbscissaMap = new HashMap<>();
	private List<Point> mPointList = new ArrayList<>();

	public ChartView(Context context) {
		this(context, null);
	}

	public ChartView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mRawDataList = new ArrayList<>();
		mRawDataList.add(new Data("Mar 01", 3111));
//		mRawDataList.add(new Data("Mar 02", 0));
//		mRawDataList.add(new Data("Mar 03", 0));
//		mRawDataList.add(new Data("Mar 04", 0));
//		mRawDataList.add(new Data("Mar 05", 0));
//		mRawDataList.add(new Data("Mar 06", 0));
//		mRawDataList.add(new Data("Mar 07", 0));
		mRawDataList.add(new Data("Mar 02", 3115));
//		mRawDataList.add(new Data("Mar 03", 3278));
//		mRawDataList.add(new Data("Mar 04", 3376));
//		mRawDataList.add(new Data("Mar 05", 3489));
//		mRawDataList.add(new Data("Mar 06", 3789));
//		mRawDataList.add(new Data("Mar 07", 3788));

		mTextPaint = new Paint();
		mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE, getResources().getDisplayMetrics()));
		mTextPaint.setStyle(Paint.Style.STROKE);
		mTextPaint.setDither(true);
		mTextPaint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
		}

		if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
		mViewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//把纵坐标显示的值计算出来，这样的话所有的值都有了
		int min = 99999;
		int max = 0;
		for (Data data : mRawDataList) {
			min = data.value < min ? data.value : min;
			max = data.value > max ? data.value : max;
		}
		//TODO 试一下除不尽的情况
		//纵坐标"数值"上的间距
		int ordinateGap = (int) Math.ceil((max - min) / 4.0f);
		ordinateGap = ordinateGap == 0 ? 1 : ordinateGap;

		//计算起始x坐标，起始y坐标
		if (mIsThumbnailMode) {
			mStartX = 0;
			mStartY = mViewHeight;
		} else {
			int maxWidth = 0;
			for (Data data : mRawDataList) {
				int width = (int) Math.ceil(mTextPaint.measureText(String.valueOf(data.value)));
				maxWidth = maxWidth > width ? maxWidth : width;
			}
			mStartX = maxWidth + 10;

			Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
			mStartY = mViewHeight - (int) Math.ceil(metrics.bottom - metrics.top);
		}

		//画横线和纵坐标的值
		float leftHeight = mStartY;
		float topLineHeight = 0;
		int ordinatePixelGap = (int) Math.ceil(leftHeight / 11.0f);
		for (int i = 0; i < 6; i++) {
			float height = mStartY - i * ordinatePixelGap * 2;
			if (i == 5) {
				max = min + ordinateGap * i;
				topLineHeight = height;
			}

			if (mIsThumbnailMode) {
				continue;
			}
			if (i == 0 || i == 5) {
				mTextPaint.setPathEffect(null);
			} else {
				mTextPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
			}
			Path path = new Path();
			path.moveTo(mStartX, height);
			path.lineTo(mViewWidth, height);
			canvas.drawPath(path, mTextPaint);

			if (i == 0 && min - ordinateGap >= 0) {
				Rect rect = new Rect();
				String text = String.valueOf(min - ordinateGap);
				mTextPaint.getTextBounds(text, 0, text.length(), rect);
				canvas.drawText(text, mStartX - rect.width() - 10, height + rect.height() / 2.0f, mTextPaint);
			}

			if (i != 0) {
				Rect rect = new Rect();
				String text = String.valueOf(min + ordinateGap * (i - 1));
				mTextPaint.getTextBounds(text, 0, text.length(), rect);
				canvas.drawText(text, mStartX - rect.width() - 10, height + rect.height() / 2.0f, mTextPaint);
			}
		}

		//画纵线和横坐标的值
		float leftWidth = mViewWidth - mStartX;
		int abscissaPixelGap = (int) Math.ceil(leftWidth / 14.0f);
		mTextPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
		mAbscissaMap.clear();
		for (int i = 0; i < 7; i++) {
			float width = mStartX + abscissaPixelGap + i * 2 * abscissaPixelGap;
			if (!mIsThumbnailMode) {
				Path path = new Path();
				path.moveTo(width, mStartY);
				path.lineTo(width, topLineHeight);
				canvas.drawPath(path, mTextPaint);
//				canvas.drawLine(width, mStartY, width, topLineHeight, mTextPaint);
			}
			if (mRawDataList != null && i < mRawDataList.size()) {
				Rect rect = new Rect();
				String text = mRawDataList.get(i).date;
				mTextPaint.getTextBounds(text, 0, text.length(), rect);
				mAbscissaMap.put(text, width);
				if (!mIsThumbnailMode) {
					canvas.drawText(text, width - rect.width() / 2.0f, mStartY + rect.height() + rect.height() / 2, mTextPaint);
				}
			}
		}

		//计算出点的位置
		mPointList.clear();
		for (Data data : mRawDataList) {
			Point point = new Point();
			point.x = mAbscissaMap.get(data.date);
			point.y = leftHeight - (((leftHeight - ordinatePixelGap) / (max - min)) * (data.value - min)) - ordinatePixelGap * 2;
			mPointList.add(point);
		}

		//画色块
		Paint blockPaint = new Paint();
		blockPaint.setDither(true);
		blockPaint.setAntiAlias(true);
		blockPaint.setColor(Color.parseColor("#fecd44"));
		blockPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (int i = 0; i < 7; i++) {
			if (i + 1 >= mPointList.size()) {
				break;
			}
			if (i % 2 == 0) {
				blockPaint.setAlpha(50);
			} else {
				blockPaint.setAlpha(100);
			}
			Path colorBlock = new Path();
			colorBlock.moveTo(mStartX + abscissaPixelGap + abscissaPixelGap * 2 * i, mStartY);
			colorBlock.lineTo(mStartX + abscissaPixelGap + abscissaPixelGap * 2 * (i + 1), mStartY);
			colorBlock.lineTo(mStartX + abscissaPixelGap + abscissaPixelGap * 2 * (i + 1), mPointList.get(i + 1).y);
			colorBlock.lineTo(mStartX + abscissaPixelGap + abscissaPixelGap * 2 * i, mPointList.get(i).y);
			colorBlock.close();
			canvas.drawPath(colorBlock, blockPaint);
		}

		//画折线
		Path path = new Path();
		for (int i = 0; i < mPointList.size(); i++) {
			Point point = mPointList.get(i);
			if (i == 0) {
				path.moveTo(point.x, point.y);
			} else {
				path.lineTo(point.x, point.y);
			}
		}
		mTextPaint.setColor(Color.parseColor("#fecd44"));
		mTextPaint.setStyle(Paint.Style.STROKE);
		mTextPaint.setStrokeWidth(2);
		canvas.drawPath(path, mTextPaint);

		//画点
		Paint pointPaint = new Paint();
		pointPaint.setDither(true);
		pointPaint.setAntiAlias(true);
		pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (Point point : mPointList) {
			pointPaint.setColor(Color.WHITE);
			canvas.drawCircle(point.x, point.y, 7, pointPaint);
			pointPaint.setColor(Color.parseColor("#fecd44"));
			canvas.drawCircle(point.x, point.y, 3, pointPaint);
		}

		//点击事件

		//
	}

	private class Point {
		private float x;
		private float y;
	}

	public static class Data {
		public String date;
		public int value;

		public Data(String date, int value) {
			this.date = date;
			this.value = value;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if(mIsThumbnailMode){
//			return super.onTouchEvent(event);
//		}
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(event);
		}

		float fingerX = event.getX();
		float fingerY = event.getY();

		for (final Point point : mPointList) {
			if (fingerY >= point.y - 50 && fingerY <= point.y + 50
					&& fingerX >= point.x - 50 && fingerX <= point.x + 50) {
				AppUtils.showToast("您点击了一个图标");
				final HintView hintView = new HintView(getContext());
				((ViewGroup) getRootView()).addView(hintView);
				hintView.show("3278");

				hintView.post(new Runnable() {
					@Override
					public void run() {
						int left;
						int top;
						int right;
						int bottom;

						if (point.y >= hintView.getMeasuredHeight()) {
							//点的上面
							top = (int) Math.ceil(point.y - hintView.getMeasuredHeight() - 10);
							bottom = (int) Math.ceil(point.y-10);
						} else {
							//点的下面
							top = (int) Math.ceil(point.y+10);
							bottom = (int) Math.ceil(point.y + hintView.getMeasuredHeight() + 10);
						}

						if (point.x >= hintView.getMeasuredWidth() / 2.0f
								&& (getMeasuredWidth() - point.x) < hintView.getMeasuredWidth() / 2.0f) {
							//靠最右边
							left = getMeasuredWidth() - hintView.getMeasuredWidth();
							right = getMeasuredWidth();
						} else if (point.x < hintView.getMeasuredWidth() / 2.0f
								&& (getMeasuredWidth() - point.x) >= hintView.getMeasuredWidth() / 2.0f) {
							//靠最左边
							left = getPaddingLeft();
							right = getPaddingLeft() + hintView.getMeasuredWidth();
						} else {
							//放中间
							left = (int) (point.x - Math.ceil(hintView.getMeasuredWidth() / 2.0f));
							right = (int) (point.x + Math.ceil(hintView.getMeasuredWidth() / 2.0f));
						}

						hintView.layout(left, top, right, bottom);
					}
				});
				return true;
			}
		}

		return super.onTouchEvent(event);
	}

	private class HintView extends View {

		private Paint paint;
		private String text = "234234";
		private Rect textRect = new Rect();
		private RectF textRectF = new RectF();

		public HintView(Context context) {
			super(context);
			paint = new Paint();
			paint.setDither(true);
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);

			setBackgroundColor(Color.RED);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			paint.getTextBounds(text, 0, text.length(), textRect);
			textRectF.roundOut(textRect);
//			widthMeasureSpec = MeasureSpec.makeMeasureSpec(textRect.width(), MeasureSpec.EXACTLY);
//			heightMeasureSpec = MeasureSpec.makeMeasureSpec(textRect.height(), MeasureSpec.EXACTLY);

			widthMeasureSpec = MeasureSpec.makeMeasureSpec(50, MeasureSpec.EXACTLY);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(30, MeasureSpec.EXACTLY);
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

		private void show(@NonNull String text) {
			this.text = text;
			requestLayout();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			paint.setColor(Color.WHITE);
			canvas.drawRoundRect(textRectF, 5, 5, paint);
			paint.setColor(Color.parseColor("#fecd44"));
			canvas.drawRoundRect(textRectF, 5, 5, paint);
			canvas.drawText(text, 0, text.length(), paint);
		}
	}
}
