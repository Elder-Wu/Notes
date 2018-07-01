package com.wuzhanglao.niubi.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;


/*
 * Created by ming.wu@shanbay.com on 2017/2/23.
 *
 * 可自动判断TextViewd的显示行数，并进行折叠展开操作
 */

public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

	private static final int EXPAND_TEXTVIEW_ID = 0x111;
	private static final int COLLAPSE_TEXTVIEW_ID = 0x222;

	/**
	 * 默认属性值
	 */
	private static final int DEFAULT_TEXT_SIZE = 14; //单位:dp
	private static final int DEFAULT_MAX_LINE_COUNT = 5;
	private static final int DEFAULT_CONTENT_COLOR = -1;
	private static final int DEFAULT_EXPAND_LABEL_COLOR = -1;
	private static final int DEFAULT_COLLAPSE_LABEL_COLOR = -1;
	private static final float DEFAULT_SPACING_MULTIPLIER = 1.0f;
	private static final boolean DEFAULT_ANIMATION = false;
	private static final String DEFAULT_EXPAND_LABEL = "更多";
	private static final String DEFAULT_COLLAPSE_LABEL = "收起";

	private int mContentSize;
	private int mContentColor;
	private int mMaxLineCount;
	private int mExpandLabelColor;
	private int mCollapseLabelColor;
	private float mSpacingMultiplier;
	private boolean mHasAnimation;
	private CharSequence mContent;
	private CharSequence mExpandLabel;
	private CharSequence mCollapseLabel;

	private TextView mTvExpand;
	private TextView mTvCollapse;
	private HandleLineCountTextView mTvContent;

	private int mCurContentHeight = 0;
	private boolean mIsExpand = false;
	private boolean mAnimationIsRunning = false;

	private Callback mCallback;

	public ExpandableTextView(Context context) {
		this(context, null);
	}

	public ExpandableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.ExpandableTextView);
		mContent = typedArray.getText(R.styleable.ExpandableTextView_android_text);
		mContent = TextUtils.isEmpty(mContent) ? "" : mContent;
		mMaxLineCount = typedArray.getInt(R.styleable.ExpandableTextView_maxLineCount, DEFAULT_MAX_LINE_COUNT);
		mContentColor = typedArray.getColor(R.styleable.ExpandableTextView_android_textColor, DEFAULT_CONTENT_COLOR);
		int defaultTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE, getResources().getDisplayMetrics());
		mContentSize = typedArray.getDimensionPixelSize(R.styleable.ExpandableTextView_android_textSize, defaultTextSize);
		mSpacingMultiplier = typedArray.getFloat(R.styleable.ExpandableTextView_android_lineSpacingMultiplier, DEFAULT_SPACING_MULTIPLIER);
		mExpandLabel = typedArray.getText(R.styleable.ExpandableTextView_expandLabel);
		mExpandLabel = TextUtils.isEmpty(mExpandLabel) ? DEFAULT_EXPAND_LABEL : mExpandLabel;
		mCollapseLabel = typedArray.getText(R.styleable.ExpandableTextView_collapseLabel);
		mCollapseLabel = TextUtils.isEmpty(mCollapseLabel) ? DEFAULT_COLLAPSE_LABEL : mCollapseLabel;
		mExpandLabelColor = typedArray.getColor(R.styleable.ExpandableTextView_expandLabelColor, DEFAULT_EXPAND_LABEL_COLOR);
		mCollapseLabelColor = typedArray.getColor(R.styleable.ExpandableTextView_collapseLabelColor, DEFAULT_COLLAPSE_LABEL_COLOR);
		mHasAnimation = typedArray.getBoolean(R.styleable.ExpandableTextView_hasAnimation, DEFAULT_ANIMATION);
		typedArray.recycle();

		mTvContent = new HandleLineCountTextView(context);
		mTvContent.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		mTvContent.setLineSpacing(0, mSpacingMultiplier);
		if (mContentColor != DEFAULT_CONTENT_COLOR) {
			mTvContent.setTextColor(mContentColor);
		}
		mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mContentSize, getResources().getDisplayMetrics()));
		mTvContent.setText(mContent);

		LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.END;

		int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
		int bottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
		int defaultColor = Color.BLUE;

		mTvExpand = new TextView(context);
		mTvExpand.setText(mExpandLabel);
		mTvExpand.setTag(EXPAND_TEXTVIEW_ID);
		mTvExpand.setOnClickListener(this);
		mTvExpand.setLayoutParams(params);
		mTvExpand.setPadding(left, top, 0, bottom);
		mTvExpand.setTextColor(mExpandLabelColor != DEFAULT_EXPAND_LABEL_COLOR ? mExpandLabelColor : defaultColor);

		mTvCollapse = new TextView(context);
		mTvCollapse.setText(mCollapseLabel);
		mTvCollapse.setTag(COLLAPSE_TEXTVIEW_ID);
		mTvCollapse.setOnClickListener(this);
		mTvCollapse.setLayoutParams(params);
		mTvCollapse.setPadding(left, top, 0, bottom);
		mTvCollapse.setTextColor(mCollapseLabelColor != DEFAULT_COLLAPSE_LABEL_COLOR ? mCollapseLabelColor : defaultColor);

		addView(mTvContent);
		addView(mTvExpand);
		addView(mTvCollapse);

		setOrientation(VERTICAL);
	}

	@Override
	public void setOrientation(int orientation) {
		if (orientation == HORIZONTAL) {
			throw new RuntimeException("orientation must be VERTICAL");
		}
		super.setOrientation(VERTICAL);
	}

	public void setText(CharSequence text, boolean isExpand) {
		mIsExpand = isExpand;
		mTvContent.setText(text);
	}

	public void setTextColor(int color) {
		mTvContent.setTextColor(color);
	}

	//单位: dp
	public void setTextSize(float size) {
		size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());
		mTvContent.setTextSize(size);
	}

	public void setExpandState(boolean isExpand) {
		mIsExpand = isExpand;
		mTvContent.requestLayout();
		mTvContent.postInvalidateOnAnimation();
	}

	public boolean getExpandState() {
		return mIsExpand;
	}

	public void setAnimation(boolean hasAnimation) {
		mHasAnimation = hasAnimation;
	}

	public void setMaxLineCount(int maxLineCount) {
		if (maxLineCount < 1) {
			throw new RuntimeException("MaxLineCount must bigger than 0");
		}
		mMaxLineCount = maxLineCount;
		mTvContent.requestLayout();
		mTvContent.postInvalidateOnAnimation();
	}

	public void setExpandLabelColor(int color) {
		mExpandLabelColor = color;
		mTvExpand.setTextColor(mExpandLabelColor);
	}

	public void setCollapseLabelColor(int color) {
		mCollapseLabelColor = color;
		mTvCollapse.setTextColor(mCollapseLabelColor);
	}

	public void setExpandLabel(CharSequence label) {
		mExpandLabel = label;
		mTvExpand.setText(mExpandLabel);
	}

	public void setCollapseLabel(CharSequence label) {
		mCollapseLabel = label;
		mTvCollapse.setText(mCollapseLabel);
	}

	public void setTypeface(Typeface typeface) {
		mTvContent.setTypeface(typeface);
	}

	private void startCollapseAnimator() {
		int begin = mTvContent.getHeight();
		int end = mTvContent.getLineHeight() * mMaxLineCount;
		ValueAnimator valueAnimator = ValueAnimator.ofObject(new IntEvaluator(), begin, end);
		valueAnimator.setDuration(mTvContent.getLineCount() * (mHasAnimation ? 20 : 1));
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		valueAnimator.addListener(mAnimatorListener);
		valueAnimator.addUpdateListener(mAnimatorUpdateListener);
		valueAnimator.start();
	}

	private void startExpandAnimator() {
		int begin = mTvContent.getHeight();
		int end = mTvContent.getLineHeight() * mTvContent.getLineCount();
		ValueAnimator valueAnimator = ValueAnimator.ofObject(new IntEvaluator(), begin, end);
		valueAnimator.setDuration(mTvContent.getLineCount() * (mHasAnimation ? 20 : 1));
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		valueAnimator.addListener(mAnimatorListener);
		valueAnimator.addUpdateListener(mAnimatorUpdateListener);
		valueAnimator.start();
	}

	private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			mCurContentHeight = (int) animation.getAnimatedValue();
			mTvContent.requestLayout();
			mTvContent.postInvalidateOnAnimation();
		}
	};

	private AnimatorListenerAdapter mAnimatorListener = new AnimatorListenerAdapter() {
		@Override
		public void onAnimationStart(Animator animation) {
			mAnimationIsRunning = true;
		}

		@Override
		public void onAnimationEnd(Animator animation) {
			mAnimationIsRunning = false;
		}
	};

	@Override
	public void onClick(View v) {
		int id = (int) v.getTag();
		if (id == EXPAND_TEXTVIEW_ID) {
			if (mCallback != null) {
				mCallback.onTextExpanded();
			}

			mIsExpand = true;
			startExpandAnimator();
		} else if (id == COLLAPSE_TEXTVIEW_ID) {
			if (mCallback != null) {
				mCallback.onTextCollapsed();
			}

			mIsExpand = false;
			startCollapseAnimator();
		}
	}

	private class HandleLineCountTextView extends android.support.v7.widget.AppCompatTextView {

		public HandleLineCountTextView(Context context) {
			super(context);
		}

		@Override
		public void setTextIsSelectable(boolean selectable) {
			super.setTextIsSelectable(true);
		}

		@Override
		public void setMaxLines(int maxlines) {
			super.setMaxLines(Integer.MAX_VALUE);
		}

		@Override
		public void setEllipsize(TextUtils.TruncateAt where) {
			super.setEllipsize(null);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);

			if (mAnimationIsRunning) {
				heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) Math.ceil(mCurContentHeight), MeasureSpec.EXACTLY);
				setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
				return;
			}

			//未达到折叠/展开文本的条件
			if (getLineCount() <= mMaxLineCount) {
				mTvExpand.setVisibility(View.GONE);
				mTvCollapse.setVisibility(View.GONE);
				return;
			}

			//需要展开
			if (mIsExpand) {
				mTvExpand.setVisibility(View.GONE);
				mTvCollapse.setVisibility(View.VISIBLE);
				return;
			}

			//需要折叠
			mTvExpand.setVisibility(View.VISIBLE);
			mTvCollapse.setVisibility(View.GONE);

			int height = (int) Math.ceil(getLineHeight() * mMaxLineCount);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
			setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public void setCallback(Callback callback) {
		mCallback = callback;
	}

	public interface Callback {
		void onTextExpanded();

		void onTextCollapsed();
	}
}
