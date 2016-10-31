package com.example.notes.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created on 2016/10/27.
 */

public class DragRefreshLayout extends RelativeLayout {

    private static final String refresh_state = "refresh_state";

    private ViewDragHelper mViewDragHelper;
    private int originX;
    private int originY;
    private int totalDeltaY;
    private View targetView;
    private boolean isFirstStart = false;
    private boolean isRefresh = false;

    public DragRefreshLayout(Context context) {
        this(context, null);
    }

    public DragRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (isRefresh) {
                    return false;
                } else {
                    totalDeltaY = 0;
                    return true;
                }
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                totalDeltaY += dy / 3;
                return totalDeltaY;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //判断是否满足刷新条件
                if(canRefresh()){

                } else {
                    mViewDragHelper.settleCapturedViewAt(originX, originY);
                    invalidate();
                }
            }
        });
    }

    private boolean canRefresh() {
        return true;
    }

    @Override
    public void computeScroll() {
        mViewDragHelper.continueSettling(true);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(getChildAt(0).getMeasuredWidth(), getChildAt(0).getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //判断是否是第一次启动
        if (!isFirstStart) {
            synchronized (this) {
                if (!isFirstStart) {
                    isFirstStart = true;
                    targetView = getChildAt(0);
                    //记录下初始位置
                    originX = targetView.getLeft();
                    originY = targetView.getTop();
                }
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
