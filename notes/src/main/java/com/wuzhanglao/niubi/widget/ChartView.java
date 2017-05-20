package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.wuzhanglao.niubi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ming.wu@shanbay.com on 2017/5/3.
 */

public class ChartView extends FrameLayout {

	private static final int DEFAULT_TEXT_SIZE = 10;  //单位：dp
	private static final int DEFAULT_BASE_COLOR = Color.BLACK;

	private Paint mPaint;
	private HintView mHintView;

	private List<Data> mRawDataList = new ArrayList<>();
	private List<Point> mPointList = new ArrayList<>();
	private List<HintView> mHintViewList = new ArrayList<>();
	private Map<String, Float> mAbscissaMap = new HashMap<>();

	private int mBaseColor; //整个图表的色调
	private int mTextSize;
	private float mViewWidth;
	private float mViewHeight;
	private boolean mIsThumbnailMode = false;
	private DashPathEffect mDashPathEffect = new DashPathEffect(new float[]{5, 5}, 0);

	public ChartView(Context context) {
		this(context, null);
	}

	public ChartView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
		mBaseColor = ta.getColor(R.styleable.ChartView_baseColor, DEFAULT_BASE_COLOR);
		mIsThumbnailMode = ta.getBoolean(R.styleable.ChartView_isThumbnailMode, false);
		mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE, getResources().getDisplayMetrics());
		mTextSize = ta.getDimensionPixelSize(R.styleable.ChartView_android_textSize, mTextSize);
		ta.recycle();

		mPaint = new Paint();
		mPaint.setAlpha(180);
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setTextSize(mTextSize);

		mHintView = new HintView(getContext());

		setWillNotDraw(false);

		//加了这行代码，我们就可以在编辑器里面看到预览了
		isInEditMode();
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
		super.onDraw(canvas);
		//计算出最大值和最小值
		int minValue = Integer.MAX_VALUE;
		int maxValue = Integer.MIN_VALUE;
		for (Data data : mRawDataList) {
			minValue = data.value < minValue ? data.value : minValue;
			maxValue = data.value > maxValue ? data.value : maxValue;
		}

		//纵坐标"数值"上的间距
		int ordinateValueGap = (int) Math.ceil((maxValue - minValue) / 4.0f);
		ordinateValueGap = ordinateValueGap == 0 ? 1 : ordinateValueGap;

		//计算起始x坐标，起始y坐标
		float startX;
		float startY;
		if (mIsThumbnailMode) {
			startX = 0;
			startY = mViewHeight;
		} else {
			int maxWidth = 0;
			for (Data data : mRawDataList) {
				int width = (int) Math.ceil(mPaint.measureText(String.valueOf(data.value)));
				maxWidth = maxWidth > width ? maxWidth : width;
			}
			startX = maxWidth + 10;

			Paint.FontMetrics metrics = mPaint.getFontMetrics();
			startY = mViewHeight - (int) Math.ceil(metrics.bottom - metrics.top);
		}

		//画横线和纵坐标的值
		float leftHeight = startY;
		float topLineHeight = 0;
		int ordinatePixelGap = (int) Math.ceil(leftHeight / 11.0f);
		for (int i = 0; i < 6; i++) {
			float height = startY - i * ordinatePixelGap * 2;
			if (i == 5) {
				maxValue = minValue + ordinateValueGap * i;
				topLineHeight = height;
			}

			if (mIsThumbnailMode) {
				continue;
			}
			if (i == 0 || i == 5) {
				mPaint.setPathEffect(null);
			} else {
				mPaint.setPathEffect(mDashPathEffect);
			}
			Path path = new Path();
			path.moveTo(startX, height);
			path.lineTo(mViewWidth, height);
			mPaint.setColor(Color.BLACK);
			mPaint.setStrokeWidth(0);
			canvas.drawPath(path, mPaint);

			if (i == 0 && minValue - ordinateValueGap >= 0) {
				Rect rect = new Rect();
				String text = String.valueOf(minValue - ordinateValueGap);
				mPaint.getTextBounds(text, 0, text.length(), rect);
				mPaint.setPathEffect(null);
				canvas.drawText(text, startX - rect.width() - 10, height + rect.height() / 2.0f, mPaint);
			}

			if (i != 0) {
				Rect rect = new Rect();
				String text = String.valueOf(minValue + ordinateValueGap * (i - 1));
				mPaint.getTextBounds(text, 0, text.length(), rect);
				mPaint.setPathEffect(null);
				canvas.drawText(text, startX - rect.width() - 10, height + rect.height() / 2.0f, mPaint);
			}
		}

		//画纵线和横坐标的值
		float leftWidth = mViewWidth - startX;
		int abscissaPixelGap = (int) Math.ceil(leftWidth / 14.0f);
		mPaint.setPathEffect(mDashPathEffect);
		mAbscissaMap.clear();
		for (int i = 0; i < 7; i++) {
			float width = startX + abscissaPixelGap + i * 2 * abscissaPixelGap;
			if (!mIsThumbnailMode) {
				Path path = new Path();
				path.moveTo(width, startY);
				path.lineTo(width, topLineHeight);
				mPaint.setPathEffect(mDashPathEffect);
				canvas.drawPath(path, mPaint);
			}
			if (mRawDataList != null && i < mRawDataList.size()) {
				Rect rect = new Rect();
				String text = mRawDataList.get(i).date;
				mPaint.getTextBounds(text, 0, text.length(), rect);
				mAbscissaMap.put(text, width);
				if (!mIsThumbnailMode) {
					mPaint.setPathEffect(null);
					canvas.drawText(text, width - rect.width() / 2.0f, startY + rect.height() + rect.height() / 2, mPaint);
				}
			}
		}


		//计算出点的位置
		mPointList.clear();
		for (Data data : mRawDataList) {
			Point point = new Point();
			point.x = mAbscissaMap.get(data.date);
			point.y = leftHeight - (((leftHeight - ordinatePixelGap) / (maxValue - minValue)) * (data.value - minValue)) - ordinatePixelGap * 2;
			mPointList.add(point);
		}

		//画色块
		Paint blockPaint = new Paint();
		blockPaint.setDither(true);
		blockPaint.setAntiAlias(true);
		blockPaint.setColor(mBaseColor);
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
			colorBlock.moveTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * i, startY);
			colorBlock.lineTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * (i + 1), startY);
			colorBlock.lineTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * (i + 1), mPointList.get(i + 1).y);
			colorBlock.lineTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * i, mPointList.get(i).y);
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
		mPaint.setColor(mBaseColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setPathEffect(null);
		canvas.drawPath(path, mPaint);

		//画点
		Paint pointPaint = new Paint();
		pointPaint.setDither(true);
		pointPaint.setAntiAlias(true);
		pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (Point point : mPointList) {
			pointPaint.setColor(Color.WHITE);
			canvas.drawCircle(point.x, point.y, 7, pointPaint);
			pointPaint.setColor(mBaseColor);
			canvas.drawCircle(point.x, point.y, 3, pointPaint);
		}
	}

	public void setThumbnailMode(boolean isThumbnailMode) {
		mIsThumbnailMode = isThumbnailMode;
		requestLayout();
	}

	public void setColor(int colorTone) {
		mBaseColor = colorTone;
		requestLayout();
	}

	public int getColor() {
		return mBaseColor;
	}

	public void setData(List<Data> dataList) {
		mRawDataList = dataList;
		requestLayout();
	}

	public void removeAllHintView() {
		for (HintView hintView : mHintViewList) {
			removeView(hintView);
		}
		mHintViewList.clear();

		removeView(mHintView);
	}

	public void showAllHintView() {
		if (!mHintViewList.isEmpty()) {
			return;
		}

		for (int i = 0; i < mPointList.size(); i += 2) {
			final Point point = mPointList.get(i);

			final HintView hintView = new HintView(getContext());
			addView(hintView);
			mHintViewList.add(hintView);
			hintView.setVisibility(INVISIBLE);
			hintView.show(getValue(point.x));

			hintView.post(new LayoutHintViewRunnable(hintView, point));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mIsThumbnailMode || event.getAction() != MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(event);
		}

		removeView(mHintView);

		float fingerX = event.getX();
		float fingerY = event.getY();

		for (final Point point : mPointList) {
			if (fingerY < point.y - 50 || fingerY > point.y + 50
					|| fingerX < point.x - 50 || fingerX > point.x + 50) {
				continue;
			}

			addView(mHintView);
			mHintView.setVisibility(INVISIBLE);
			mHintView.show(getValue(point.x));

			post(new LayoutHintViewRunnable(mHintView, point));
			return true;
		}

		return super.onTouchEvent(event);
	}

	private class LayoutHintViewRunnable implements Runnable {

		private View view;
		private Point point;

		public LayoutHintViewRunnable(View view, Point point) {
			this.view = view;
			this.point = point;
		}

		@Override
		public void run() {
			int left;
			int top;

			int hintViewWidth = view.getMeasuredWidth();
			int hintViewHeight = view.getMeasuredHeight();

			if (point.y >= hintViewHeight) {
				//点的上面
				top = (int) Math.ceil(point.y - hintViewHeight - 10);
			} else {
				//点的下面
				top = (int) Math.ceil(point.y + 10);
			}

			if (point.x >= hintViewWidth / 2.0f
					&& (getMeasuredWidth() - point.x) < hintViewWidth / 2.0f) {
				//靠最右边
				left = getMeasuredWidth() - hintViewWidth;
			} else if (point.x < hintViewWidth / 2.0f
					&& (getMeasuredWidth() - point.x) >= hintViewWidth / 2.0f) {
				//靠最左边
				left = getPaddingLeft();
			} else {
				//放中间
				left = (int) (point.x - Math.ceil(hintViewWidth / 2.0f));
			}

			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(hintViewWidth, hintViewHeight);
			layoutParams.leftMargin = left;
			layoutParams.topMargin = top;
			view.setLayoutParams(layoutParams);
			view.setVisibility(VISIBLE);
		}
	}

	private String getValue(float x) {
		String date = null;
		for (Map.Entry<String, Float> data : mAbscissaMap.entrySet()) {
			if (data.getValue() == x) {
				date = data.getKey();
				break;
			}
		}

		if (TextUtils.isEmpty(date)) {
			return "";
		}

		for (Data data : mRawDataList) {
			if (TextUtils.equals(data.date, date)) {
				return String.valueOf(data.value);
			}
		}

		return "";
	}

	private class HintView extends View {

		private Paint paint;
		private String text = "";
		private Rect textRect = new Rect();
		private RectF textRectF = new RectF();

		public HintView(Context context) {
			super(context);
			paint = new Paint();
			paint.setDither(true);
			paint.setAntiAlias(true);
			paint.setTextSize(mTextSize);
			paint.setTypeface(Typeface.SERIF);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			paint.getTextBounds(text, 0, text.length(), textRect);
			int width = textRect.width() + 20;
			int height = textRect.height() + 20;
			textRectF.set(0, 0, width, height);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

		private void show(@NonNull String text) {
			this.text = text;
			requestLayout();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			paint.setStrokeWidth(5);
			paint.setColor(mBaseColor);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRoundRect(textRectF, 5, 5, paint);

			paint.setColor(Color.WHITE);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawRoundRect(textRectF, 5, 5, paint);

			paint.setStrokeWidth(0);
			paint.getTextBounds(text, 0, text.length(), textRect);
			canvas.drawText(text, (textRectF.width() - textRect.width()) / 2.0f, (textRectF.height() + textRect.height()) / 2.0f, paint);
		}
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
	public boolean isInEditMode() {
		mRawDataList.add(new ChartView.Data("Mar 01", 3111));
		mRawDataList.add(new ChartView.Data("Mar 02", 3115));
		mRawDataList.add(new ChartView.Data("Mar 03", 3278));
		mRawDataList.add(new ChartView.Data("Mar 04", 3376));
		mRawDataList.add(new ChartView.Data("Mar 05", 3489));
		mRawDataList.add(new ChartView.Data("Mar 06", 3789));
		mRawDataList.add(new ChartView.Data("Mar 07", 3788));
		return super.isInEditMode();
	}
}
