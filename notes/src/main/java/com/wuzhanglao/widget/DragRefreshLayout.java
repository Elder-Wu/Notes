package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;

/**
 * Created on 2016/10/27.
 */

public class DragRefreshLayout extends RelativeLayout {

    private static final String TAG = DragRefreshLayout.class.getSimpleName();
    private static final String refresh_state = "refresh_state";
    private Paint paint;
    private View targetView;
    //刷新头部
    private View headerView;
    private ImageView headerIcon;
    private TextView headerText;

    private boolean isRefreshing = false;
    private Scroller scroller;

    public DragRefreshLayout(Context context) {
        this(context, null);
    }

    public DragRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(getContext());
        addHeaderView();

        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20);
    }

    private boolean canRefresh() {
        //可滚动的view已经到达边界
        if (targetView.canScrollVertically(-1)) {
            return false;
        }
        //当前控件不处于刷新状态
        if (isRefreshing) {
            return false;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTarget();
        if (targetView == null) {
            return;
        }
        //measure child
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() - targetView.getPaddingLeft() - targetView.getPaddingRight(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - targetView.getPaddingTop() - targetView.getPaddingBottom(), MeasureSpec.EXACTLY);
        targetView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    private void ensureTarget() {
        if (targetView != null) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != headerView) {
                targetView = child;
                return;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, r, t, b);
        headerView.layout(0, -headerView.getMeasuredHeight(), headerView.getMeasuredWidth(), 0);
        int y = headerView.getMeasuredHeight();
        targetView.layout(0 - targetView.getPaddingLeft(), headerView.getBottom(), 0 - targetView.getPaddingLeft() + targetView.getMeasuredWidth(), headerView.getBottom() + targetView.getMeasuredHeight());
    }

    private void addHeaderView() {
        //加载headerView，并初始化控件
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.refresh_layout_header, this, false);
        headerIcon = (ImageView) headerView.findViewById(R.id.refresh_header_icon);
        headerText = (TextView) headerView.findViewById(R.id.refresh_header_prompt_tv);
        addView(headerView);
        //通过headerView.layout()方法，将headaerView摆放到布局的顶部，此时的headerView是看不见的
//        ViewCompat.offsetLeftAndRight(this, 100);
//        invalidate();
    }

    private Path path;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        path = new Path();
        path.moveTo(0, 0);
        path.cubicTo(getWidth() - 100, 500, 100, 500, getWidth(), 0);
        canvas.drawPath(path, paint);
    }

    private float pointerCurrentY;
    private float pointerStartY;

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //相对坐标
                pointerStartY = ev.getY();
                pointerCurrentY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //(1)判断手指是向下滑动的，不是向上滑动
                //(2)判断targetView是否到达顶部
                //(3)scrollY必须大于0
                pointerCurrentY = ev.getY();
//                if ((pointerCurrentY - pointerStartY) > 0 && !ViewCompat.canScrollVertically(targetView, -1)) {
                return true;
//                }
//                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                pointerCurrentY = event.getY();
                float dy = pointerCurrentY - pointerStartY;
                dy = Math.max(0, dy);
                if (dy == 0) {
                    return true;
                }
//                dy = (getScaleY() + dy) > 0 ? dy : -getScrollY();
                //noinspection ResourceType
                scrollTo(0, (int) -dy / 2);
                path = new Path();
                path.moveTo(0, 0);
                path.cubicTo(getWidth(), getScrollY(), 0, getScaleY(), getWidth(), 0);
                break;
            case MotionEvent.ACTION_UP:
                //判断是否可以进行刷新
                if (event.getY() > 100) {
                    //可以刷新

                } else {
                    //不可以刷新，回到初始位置
                }
                //// TODO: 2016/11/2 将整个View平滑地移动到初始位置
                scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    //重绘
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }
}
