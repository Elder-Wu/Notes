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

public class ProgressChartView extends FrameLayout {

	private static final int DEFAULT_TEXT_SIZE = 10;  //单位：dp
	private static final int DEFAULT_BASE_COLOR = Color.BLACK;
	private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
	private static final int DEFAULT_COORDINATE_OFFSET = 10;
	private static final int DEFAULT_TOUCH_RANGE = 50;
	private static final int DEFAULT_ABSCISSA_COUNT = 7;
	private static final int DEFAULT_ORDINATE_COUNT = 6;

	private Paint mPaint;
	private HintView mHintView;

	private List<Data> mRawDataList = new ArrayList<>();
	private List<Point> mPointList = new ArrayList<>();
	private List<HintView> mHintViewList = new ArrayList<>();
	private Map<String, Float> mAbscissaMap = new HashMap<>();

	private int mBaseColor; //整个图表的色调
	private int mTextSize;
	private boolean mIsThumbnailMode;
	private DashPathEffect mDashPathEffect = new DashPathEffect(new float[]{5, 5}, 0);
	private int mTextColor = DEFAULT_TEXT_COLOR;

	private int mMaxOrdinateValue;
	private int mMinOrdinateValue;
	private int mOrdinateValueGap;

	private Path mPolylinePath = new Path();
	private List<Path> mColorBlockPathList = new ArrayList<>();

	private List<Dial> mAbscissaDialList = new ArrayList<>();
	private List<Path> mAbscissaLinePathList = new ArrayList<>();

	private List<Dial> mOrdinateDialList = new ArrayList<>();
	private List<Path> mOrdinateLinePathList = new ArrayList<>();

	public ProgressChartView(Context context) {
		this(context, null);
	}

	public ProgressChartView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressChartView);
		mBaseColor = ta.getColor(R.styleable.ProgressChartView_baseColor, DEFAULT_BASE_COLOR);
		mIsThumbnailMode = ta.getBoolean(R.styleable.ProgressChartView_isThumbnailMode, false);
		mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE, getResources().getDisplayMetrics());
		mTextSize = ta.getDimensionPixelSize(R.styleable.ProgressChartView_android_textSize, mTextSize);
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
		float viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
		float viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

		//计算起始x坐标，起始y坐标
		float startX;
		float startY;
		if (mIsThumbnailMode) {
			startX = 0;
			startY = viewHeight;
		} else {
			int maxWidth = (int) Math.ceil(mPaint.measureText(String.valueOf(mMinOrdinateValue + mOrdinateValueGap * (DEFAULT_ORDINATE_COUNT - 1))));
			startX = maxWidth + DEFAULT_COORDINATE_OFFSET;

			Paint.FontMetrics metrics = mPaint.getFontMetrics();
			startY = viewHeight - (metrics.bottom - metrics.top) - DEFAULT_COORDINATE_OFFSET;
		}

		//计算每条横线的位置和纵坐标刻度的位置
		float leftHeight = startY;
		float topLineHeight = 0;
		int ordinatePixelGap = (int) Math.ceil(leftHeight / (DEFAULT_ORDINATE_COUNT * 2 - 1));

		mAbscissaDialList.clear();
		mAbscissaLinePathList.clear();
		for (int i = 0; i < DEFAULT_ORDINATE_COUNT; i++) {
			float height = startY - i * ordinatePixelGap * 2;
			if (i == DEFAULT_ORDINATE_COUNT - 1) {
				mMaxOrdinateValue = mMinOrdinateValue + mOrdinateValueGap * i;
				topLineHeight = height;
			}

			if (mIsThumbnailMode) {
				continue;
			}

			Path path = new Path();
			path.moveTo(startX, height);
			path.lineTo(viewWidth, height);
			mAbscissaLinePathList.add(path);

			Rect rect = new Rect();
			String text = "";
			if (i == 0 && mMinOrdinateValue - mOrdinateValueGap >= 0) {
				text = String.valueOf(mMinOrdinateValue - mOrdinateValueGap);
			}
			if (i != 0) {
				text = String.valueOf(mMinOrdinateValue + mOrdinateValueGap * (i - 1));
			}
			mPaint.getTextBounds(text, 0, text.length(), rect);

			Point point = new Point();
			point.x = startX - rect.width() - 10;
			point.y = height + rect.height() / 2.0f;
			Dial dial = new Dial();
			dial.text = text;
			dial.point = point;
			mAbscissaDialList.add(dial);
		}

		//计算每条纵线的位置和横坐标刻度的位置
		float leftWidth = viewWidth - startX;
		int abscissaPixelGap = (int) Math.ceil(leftWidth / (DEFAULT_ABSCISSA_COUNT * 2));
		mOrdinateDialList.clear();
		mOrdinateLinePathList.clear();
		mAbscissaMap.clear();
		for (int i = 0; i < DEFAULT_ABSCISSA_COUNT; i++) {
			float width = startX + abscissaPixelGap + i * 2 * abscissaPixelGap;
			if (!mIsThumbnailMode) {
				Path path = new Path();
				path.moveTo(width, startY);
				path.lineTo(width, topLineHeight);
				mOrdinateLinePathList.add(path);
			}
			if (mRawDataList != null && i < mRawDataList.size()) {
				Rect rect = new Rect();
				String text = mRawDataList.get(i).date;
				mPaint.getTextBounds(text, 0, text.length(), rect);
				mAbscissaMap.put(text, width);
				if (!mIsThumbnailMode) {
					Point point = new Point();
					point.x = width - rect.width() / 2.0f;
					point.y = startY + rect.height() + rect.height() / 2;
					Dial dial = new Dial();
					dial.text = text;
					dial.point = point;
					mOrdinateDialList.add(dial);
				}
			}
		}

		//计算出每个点的位置
		mPointList.clear();
		for (Data data : mRawDataList) {
			Point point = new Point();
			point.x = mAbscissaMap.get(data.date);
			point.y = leftHeight - (((leftHeight - ordinatePixelGap) / (mMaxOrdinateValue - mMinOrdinateValue)) * (data.value - mMinOrdinateValue)) - ordinatePixelGap * 2;
			point.value = String.valueOf(data.value);
			mPointList.add(point);
		}

		//计算出色块的path
		mColorBlockPathList.clear();
		for (int i = 0; i < DEFAULT_ABSCISSA_COUNT; i++) {
			if (i + 1 >= mPointList.size()) {
				break;
			}
			Path path = new Path();
			path.moveTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * i, startY);
			path.lineTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * (i + 1), startY);
			path.lineTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * (i + 1), mPointList.get(i + 1).y);
			path.lineTo(startX + abscissaPixelGap + abscissaPixelGap * 2 * i, mPointList.get(i).y);
			path.close();
			mColorBlockPathList.add(path);
		}

		//计算出折线的path
		mPolylinePath.reset();
		for (int i = 0; i < mPointList.size(); i++) {
			Point point = mPointList.get(i);
			if (i == 0) {
				mPolylinePath.moveTo(point.x, point.y);
			} else {
				mPolylinePath.lineTo(point.x, point.y);
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!mIsThumbnailMode) {
			//画纵坐标
			mPaint.setColor(mTextColor);
			mPaint.setStrokeWidth(1);
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			for (int i = 0; i < mAbscissaLinePathList.size(); i++) {
				if (i == 0) {
					mPaint.setPathEffect(null);
				} else {
					mPaint.setPathEffect(mDashPathEffect);
				}
				canvas.drawPath(mAbscissaLinePathList.get(i), mPaint);

				Dial dial = mAbscissaDialList.get(i);
				mPaint.setPathEffect(null);
				canvas.drawText(dial.text, dial.point.x, dial.point.y, mPaint);
			}

			//画横坐标
			for (int i = 0; i < mOrdinateLinePathList.size(); i++) {
				mPaint.setPathEffect(mDashPathEffect);
				canvas.drawPath(mOrdinateLinePathList.get(i), mPaint);
				if (mRawDataList != null && i < mOrdinateDialList.size()) {
					Dial dial = mOrdinateDialList.get(i);
					mPaint.setPathEffect(null);
					canvas.drawText(dial.text, dial.point.x, dial.point.y, mPaint);
				}
			}
		}

		//画色块
		mPaint.setColor(mBaseColor);
		mPaint.setStrokeWidth(0);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (int i = 0; i < mColorBlockPathList.size(); i++) {
			if (i % 2 == 0) {
				mPaint.setAlpha(30);
			} else {
				mPaint.setAlpha(60);
			}
			canvas.drawPath(mColorBlockPathList.get(i), mPaint);
		}

		//画折线
		mPaint.setColor(mBaseColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setPathEffect(null);
		canvas.drawPath(mPolylinePath, mPaint);

		//画点
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (Point point : mPointList) {
			mPaint.setColor(Color.WHITE);
			canvas.drawCircle(point.x, point.y, 7, mPaint);
			mPaint.setColor(mBaseColor);
			canvas.drawCircle(point.x, point.y, 3, mPaint);
		}
	}

	public void setThumbnailMode(boolean isThumbnailMode) {
		mIsThumbnailMode = isThumbnailMode;
		requestLayout();
	}

	public void setColor(int colorTone) {
		mBaseColor = colorTone;
		invalidate();
	}

	public void setData(List<Data> dataList) {
		mRawDataList = dataList;
		//计算出最大值和最小值
		mMinOrdinateValue = Integer.MAX_VALUE;
		mMaxOrdinateValue = Integer.MIN_VALUE;
		for (Data data : mRawDataList) {
			mMinOrdinateValue = data.value < mMinOrdinateValue ? data.value : mMinOrdinateValue;
			mMaxOrdinateValue = data.value > mMaxOrdinateValue ? data.value : mMaxOrdinateValue;
		}

		//纵坐标"数值"上的间距
		mOrdinateValueGap = (int) Math.ceil((mMaxOrdinateValue - mMinOrdinateValue) / Float.valueOf(String.valueOf(DEFAULT_ORDINATE_COUNT - 2)));
		mOrdinateValueGap = mOrdinateValueGap == 0 ? 1 : mOrdinateValueGap;

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
			hintView.show(point.value);
			hintView.post(new LayoutHintViewRunnable(hintView, point));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mIsThumbnailMode || event.getAction() != MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(event);
		}

		removeView(mHintView);

		float touchX = event.getX();
		float touchY = event.getY();

		for (final Point point : mPointList) {
			if (touchY < point.y - DEFAULT_TOUCH_RANGE || touchY > point.y + DEFAULT_TOUCH_RANGE
					|| touchX < point.x - DEFAULT_TOUCH_RANGE || touchX > point.x + DEFAULT_TOUCH_RANGE) {
				continue;
			}

			addView(mHintView);
			mHintView.setVisibility(INVISIBLE);
			mHintView.show(point.value);

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

			if (point.y >= hintViewHeight + 10) {
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

			LayoutParams layoutParams = new LayoutParams(hintViewWidth, hintViewHeight);
			layoutParams.leftMargin = left;
			layoutParams.topMargin = top;
			view.setLayoutParams(layoutParams);
			view.setVisibility(VISIBLE);
		}
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
		private String value;
	}

	//刻度
	private class Dial {
		private String text;
		private Point point;
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
		mRawDataList.add(new Data("Mar 01", 3111));
		mRawDataList.add(new Data("Mar 02", 3115));
		mRawDataList.add(new Data("Mar 03", 3278));
		mRawDataList.add(new Data("Mar 04", 3376));
		mRawDataList.add(new Data("Mar 05", 3489));
		mRawDataList.add(new Data("Mar 06", 3789));
		mRawDataList.add(new Data("Mar 07", 3788));
		return super.isInEditMode();
	}
}
