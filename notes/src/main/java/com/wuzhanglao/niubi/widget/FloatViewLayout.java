package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.wuzhanglao.niubi.R;

/**
 * Created by wuming on 2016/10/26.
 */

public class FloatViewLayout extends RelativeLayout {

    private static final String TAG = FloatViewLayout.class.getSimpleName();
    private static final float DEFAULT_OFFSET = 0f;

    private ViewDragHelper viewDragHelper;
    private float offset;
    private View targetView;
    private boolean isFirstStart = false;

    public FloatViewLayout(Context context) {
        this(context, null);
    }

    public FloatViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FloatViewLayout);
        offset = ta.getFloat(R.styleable.FloatViewLayout_offset, DEFAULT_OFFSET);
        ta.recycle();
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.d(TAG, child.getClass().getSimpleName());
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(View child, float xvel, float yvel) {
                int top;
                int parentCenterX = getWidth() / 2;
                int childCenterX = (child.getRight() + child.getLeft()) / 2;
                if (child.getTop() < 0) {
                    top = 0;
                } else if (child.getBottom() > getBottom()) {
                    top = getBottom() - child.getHeight();
                } else {
                    top = child.getTop();
                }
                if (childCenterX < parentCenterX) {
                    viewDragHelper.settleCapturedViewAt((int) (0 - child.getWidth() * offset), top);
                } else {
                    viewDragHelper.settleCapturedViewAt((int) (getWidth() - child.getWidth() + child.getWidth() * offset), top);
                }
                invalidate();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getHeight();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isFirstStart) {
            synchronized (this) {
                if (!isFirstStart) {
                    isFirstStart = true;
                    targetView = getChildAt(0);
                    replaceTargetView();
                }
            }
        }
    }

    private void replaceTargetView() {
        int left, top;
        int parentCenterX = getWidth() / 2;
        int childCenterX = (targetView.getRight() + targetView.getLeft()) / 2;
        if (targetView.getTop() < 0) {
            top = 0;
        } else if (targetView.getBottom() > getBottom()) {
            top = getBottom() - targetView.getHeight();
        } else {
            top = targetView.getTop();
        }
        if (childCenterX < parentCenterX) {
            left = (int) (0 - targetView.getWidth() * offset);
        } else {
            left = (int) (getWidth() - targetView.getWidth() + targetView.getWidth() * offset);
        }
        targetView.layout(left, top, left + targetView.getWidth(), top + targetView.getHeight());
    }

    @Override
    public void computeScroll() {
        viewDragHelper.continueSettling(true);
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}
