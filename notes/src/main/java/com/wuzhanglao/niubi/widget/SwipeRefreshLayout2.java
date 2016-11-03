///*
// * Copyright (C) 2013 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.wuzhanglao.niubi.widget;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.support.annotation.ColorInt;
//import android.support.annotation.ColorRes;
//import android.support.annotation.Nullable;
//import android.support.annotation.VisibleForTesting;
//import android.support.v4.view.MotionEventCompat;
//import android.support.v4.view.NestedScrollingChild;
//import android.support.v4.view.NestedScrollingChildHelper;
//import android.support.v4.view.NestedScrollingParent;
//import android.support.v4.view.NestedScrollingParentHelper;
//import android.support.v4.view.ViewCompat;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.view.animation.DecelerateInterpolator;
//import android.view.animation.Transformation;
//import android.widget.AbsListView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.wuzhanglao.niubi.R;
//
//public class SwipeRefreshLayout2 extends ViewGroup implements NestedScrollingParent,
//        NestedScrollingChild {
//    @VisibleForTesting
//    static final int CIRCLE_DIAMETER = 40;
//    @VisibleForTesting
//    static final int CIRCLE_DIAMETER_LARGE = 56;
//
//    private static final String LOG_TAG = SwipeRefreshLayout2.class.getSimpleName();
//
//    private static final int MAX_ALPHA = 255;
//    private static final int STARTING_PROGRESS_ALPHA = (int) (.3f * MAX_ALPHA);
//
//    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
//    private static final int INVALID_POINTER = -1;
//    private static final float DRAG_RATE = .5f;
//
//    // Max amount of circle that can be filled by progress during swipe gesture,
//    // where 1.0 is a full circle
//    private static final float MAX_PROGRESS_ANGLE = .8f;
//
//    private static final int SCALE_DOWN_DURATION = 150;
//
//    private static final int ALPHA_ANIMATION_DURATION = 300;
//
//    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
//
//    private static final int ANIMATE_TO_START_DURATION = 200;
//
//    // Default background for the progress spinner
//    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
//    // Default offset in dips from the top of the view to where the progress spinner should stop
//    private static final int DEFAULT_CIRCLE_TARGET = 64;
//
//    private View mTarget; // the target of the gesture
//    OnRefreshListener mListener;
//    boolean mRefreshing = false;
//    private int mTouchSlop;
//    private float mTotalDragDistance = -1;
//
//    // If nested scrolling is enabled, the total amount that needed to be
//    // consumed by this as the nested scrolling parent is used in place of the
//    // overscroll determined by MOVE events in the onTouch handler
//    private float mTotalUnconsumed;
//    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
//    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
//    private final int[] mParentScrollConsumed = new int[2];
//    private final int[] mParentOffsetInWindow = new int[2];
//    private boolean mNestedScrollInProgress;
//
//    private int mMediumAnimationDuration;
//    int mCurrentTargetOffsetTop;
//
//    private float mInitialMotionY;
//    private float mInitialDownY;
//    private boolean mIsBeingDragged;
//    private int mActivePointerId = INVALID_POINTER;
//    // Whether this item is scaled up rather than clipped
//    boolean mScale;
//
//    // Target is returning to its start offset because it was cancelled or a
//    // refresh was triggered.
//    private boolean mReturningToStart;
//    private final DecelerateInterpolator mDecelerateInterpolator;
//    private static final int[] LAYOUT_ATTRS = new int[]{
//            android.R.attr.enabled
//    };
//
//    private int headerViewIndex = -1;
//
//    protected int mFrom;
//
//    float mStartingScale;
//
//    protected int mOriginalOffsetTop;
//
//    private Animation mScaleAnimation;
//
//    private Animation mScaleDownAnimation;
//
//    private Animation mAlphaStartAnimation;
//
//    private Animation mAlphaMaxAnimation;
//
//    private Animation mScaleDownToStartAnimation;
//
//    float mSpinnerFinalOffset;
//
//    boolean mNotify;
//
//    private int mCircleDiameter;
//
//    // Whether the client has set a custom starting position;
//    boolean mUsingCustomStart;
//
//    private OnChildScrollUpCallback mChildScrollUpCallback;
//
//    private View targetView;
//
//    //刷新头部
//    private View refreshHeaderView;
//    private ImageView refreshHeaderIcon;
//    private TextView refreshHeaderText;
//
//
////    private Animation.AnimationListener mRefreshListener = new Animation.AnimationListener() {
////        @Override
////        public void onAnimationStart(Animation animation) {
////        }
////
////        @Override
////        public void onAnimationRepeat(Animation animation) {
////        }
////
////        @Override
////        public void onAnimationEnd(Animation animation) {
////            if (mRefreshing) {
////                // Make sure the progress view is fully visible
////                mProgress.setAlpha(MAX_ALPHA);
////                mProgress.start();
////                if (mNotify) {
////                    if (mListener != null) {
////                        mListener.onRefresh();
////                    }
////                }
////                mCurrentTargetOffsetTop = mCircleView.getTop();
////            } else {
////                reset();
////            }
////        }
////    };
//
//    void reset() {
//        scrollTo(0, 0);
//        mCurrentTargetOffsetTop = targetView.getTop();
//    }
//
//    @Override
//    public void setEnabled(boolean enabled) {
//        super.setEnabled(enabled);
//        if (!enabled) {
//            reset();
//        }
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        reset();
//    }
//
//    public void setProgressViewOffset(boolean scale, int start, int end) {
//        mScale = scale;
//        mOriginalOffsetTop = start;
//        mSpinnerFinalOffset = end;
//        mUsingCustomStart = true;
//        reset();
//        mRefreshing = false;
//    }
//
//    /**
//     * The refresh indicator resting position is always positioned near the top
//     * of the refreshing content. This position is a consistent location, but
//     * can be adjusted in either direction based on whether or not there is a
//     * toolbar or actionbar present.
//     *
//     * @param scale Set to true if there is no view at a higher z-order than where the progress
//     *              spinner is set to appear. Setting it to true will cause indicator to be scaled
//     *              up rather than clipped.
//     * @param end   The offset in pixels from the top of this view at which the
//     *              progress spinner should come to rest after a successful swipe
//     *              gesture.
//     */
//    public void setProgressViewEndTarget(boolean scale, int end) {
//        mSpinnerFinalOffset = end;
//        mScale = scale;
//        refreshHeaderView.invalidate();
//    }
//
//    public SwipeRefreshLayout2(Context context) {
//        this(context, null);
//    }
//
//    public SwipeRefreshLayout2(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//
//        mMediumAnimationDuration = getResources().getInteger(
//                android.R.integer.config_mediumAnimTime);
//
//        setWillNotDraw(false);
//
//        createHeaderView();
//
//        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
//        // the absolute offset has to take into account that the circle starts at an offset
//        mTotalDragDistance = mSpinnerFinalOffset;
//        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
//
//        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
//        setNestedScrollingEnabled(true);
//
//        mOriginalOffsetTop = mCurrentTargetOffsetTop = -mCircleDiameter;
//        moveToStart(1.0f);
//
//        final TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
//        setEnabled(a.getBoolean(0, true));
//        a.recycle();
//    }
//
//    @Override
//    protected int getChildDrawingOrder(int childCount, int i) {
//        if (headerViewIndex < 0) {
//            return i;
//        } else if (i == childCount - 1) {
//            // Draw the selected child last
//            return headerViewIndex;
//        } else if (i >= headerViewIndex) {
//            // Move the children after the selected child earlier one
//            return i + 1;
//        } else {
//            // Keep the children before the selected child the same
//            return i;
//        }
//    }
//
//    private void createHeaderView() {
//        refreshHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.refresh_layout_header, this, true);
//        refreshHeaderIcon = (ImageView) refreshHeaderView.findViewById(R.id.refresh_header_icon);
//        refreshHeaderText = (TextView) refreshHeaderView.findViewById(R.id.refresh_header_prompt_tv);
//    }
//
//    /**
//     * Set the listener to be notified when a refresh is triggered via the swipe
//     * gesture.
//     */
//    public void setOnRefreshListener(OnRefreshListener listener) {
//        mListener = listener;
//    }
//
//    public void setRefreshing(boolean refreshing) {
//        if (refreshing && mRefreshing != refreshing) {
//            // scale and show
//            mRefreshing = refreshing;
//            int endTarget = 0;
//            if (!mUsingCustomStart) {
//                endTarget = (int) (mSpinnerFinalOffset + mOriginalOffsetTop);
//            } else {
//                endTarget = (int) mSpinnerFinalOffset;
//            }
//            setTargetOffsetTopAndBottom(endTarget - mCurrentTargetOffsetTop,
//                    true /* requires update */);
//            mNotify = false;
//            startScaleUpAnimation(mRefreshListener);
//        } else {
//            setRefreshing(refreshing, false /* notify */);
//        }
//    }
//
//    private void startScaleUpAnimation(AnimationListener listener) {
////        mCircleView.setVisibility(View.VISIBLE);
////        if (android.os.Build.VERSION.SDK_INT >= 11) {
////            // Pre API 11, alpha is used in place of scale up to show the
////            // progress circle appearing.
////            // Don't adjust the alpha during appearance otherwise.
////            mProgress.setAlpha(MAX_ALPHA);
////        }
////        mScaleAnimation = new Animation() {
////            @Override
////            public void applyTransformation(float interpolatedTime, Transformation t) {
////                setAnimationProgress(interpolatedTime);
////            }
////        };
////        mScaleAnimation.setDuration(mMediumAnimationDuration);
////        if (listener != null) {
////            mCircleView.setAnimationListener(listener);
////        }
////        mCircleView.clearAnimation();
////        mCircleView.startAnimation(mScaleAnimation);
//    }
//
//    /**
//     * Pre API 11, this does an alpha animation.
//     *
//     * @param progress
//     */
//    void setAnimationProgress(float progress) {
////        if (isAlphaUsedForScale()) {
////            setColorViewAlpha((int) (progress * MAX_ALPHA));
////        } else {
////            ViewCompat.setScaleX(mCircleView, progress);
////            ViewCompat.setScaleY(mCircleView, progress);
////        }
//    }
//
//    private void setRefreshing(boolean refreshing, final boolean notify) {
//        if (mRefreshing != refreshing) {
//            mNotify = notify;
//            ensureTarget();
//            mRefreshing = refreshing;
//            if (mRefreshing) {
//                animateOffsetToCorrectPosition(mCurrentTargetOffsetTop, null);
//            } else {
//                startScaleDownAnimation(mRefreshListener);
//            }
//        }
//    }
//
//    void startScaleDownAnimation(Animation.AnimationListener listener) {
//        mScaleDownAnimation = new Animation() {
//            @Override
//            public void applyTransformation(float interpolatedTime, Transformation t) {
//                setAnimationProgress(1 - interpolatedTime);
//            }
//        };
//        mScaleDownAnimation.setDuration(SCALE_DOWN_DURATION);
//        mCircleView.setAnimationListener(listener);
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(mScaleDownAnimation);
//    }
//
//    private void startProgressAlphaStartAnimation() {
//        mAlphaStartAnimation = startAlphaAnimation(mProgress.getAlpha(), STARTING_PROGRESS_ALPHA);
//    }
//
//    private void startProgressAlphaMaxAnimation() {
//        mAlphaMaxAnimation = startAlphaAnimation(mProgress.getAlpha(), MAX_ALPHA);
//    }
//
//    private Animation startAlphaAnimation(final int startingAlpha, final int endingAlpha) {
//        // Pre API 11, alpha is used in place of scale. Don't also use it to
//        // show the trigger point.
//        if (mScale && isAlphaUsedForScale()) {
//            return null;
//        }
//        Animation alpha = new Animation() {
//            @Override
//            public void applyTransformation(float interpolatedTime, Transformation t) {
//                mProgress.setAlpha(
//                        (int) (startingAlpha + ((endingAlpha - startingAlpha) * interpolatedTime)));
//            }
//        };
//        alpha.setDuration(ALPHA_ANIMATION_DURATION);
//        // Clear out the previous animation listeners.
//        mCircleView.setAnimationListener(null);
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(alpha);
//        return alpha;
//    }
//
//    /**
//     * @deprecated Use {@link #setProgressBackgroundColorSchemeResource(int)}
//     */
//    @Deprecated
//    public void setProgressBackgroundColor(int colorRes) {
//        setProgressBackgroundColorSchemeResource(colorRes);
//    }
//
//    /**
//     * Set the background color of the progress spinner disc.
//     *
//     * @param colorRes Resource id of the color.
//     */
//    public void setProgressBackgroundColorSchemeResource(@ColorRes int colorRes) {
//        setProgressBackgroundColorSchemeColor(getResources().getColor(colorRes));
//    }
//
//    /**
//     * Set the background color of the progress spinner disc.
//     *
//     * @param color
//     */
//    public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
//        mCircleView.setBackgroundColor(color);
//        mProgress.setBackgroundColor(color);
//    }
//
//    /**
//     * @deprecated Use {@link #setColorSchemeResources(int...)}
//     */
//    @Deprecated
//    public void setColorScheme(@ColorInt int... colors) {
//        setColorSchemeResources(colors);
//    }
//
//    /**
//     * Set the color resources used in the progress animation from color resources.
//     * The first color will also be the color of the bar that grows in response
//     * to a user swipe gesture.
//     *
//     * @param colorResIds
//     */
//    public void setColorSchemeResources(@ColorRes int... colorResIds) {
//        final Resources res = getResources();
//        int[] colorRes = new int[colorResIds.length];
//        for (int i = 0; i < colorResIds.length; i++) {
//            colorRes[i] = res.getColor(colorResIds[i]);
//        }
//        setColorSchemeColors(colorRes);
//    }
//
//    /**
//     * Set the colors used in the progress animation. The first
//     * color will also be the color of the bar that grows in response to a user
//     * swipe gesture.
//     *
//     * @param colors
//     */
//    public void setColorSchemeColors(@ColorInt int... colors) {
//        ensureTarget();
//        mProgress.setColorSchemeColors(colors);
//    }
//
//    /**
//     * @return Whether the SwipeRefreshWidget is actively showing refresh
//     * progress.
//     */
//    public boolean isRefreshing() {
//        return mRefreshing;
//    }
//
//    private void ensureTarget() {
//        // Don't bother getting the parent height if the parent hasn't been laid
//        // out yet.
//        if (mTarget == null) {
//            for (int i = 0; i < getChildCount(); i++) {
//                View child = getChildAt(i);
//                if (!child.equals(refreshHeaderView)) {
//                    mTarget = child;
//                    break;
//                }
//            }
//        }
//    }
//
//    /**
//     * Set the distance to trigger a sync in dips
//     *
//     * @param distance
//     */
//    public void setDistanceToTriggerSync(int distance) {
//        mTotalDragDistance = distance;
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        final int width = getMeasuredWidth();
//        final int height = getMeasuredHeight();
//        if (getChildCount() < 2) {
//            return;
//        }
//        if (mTarget == null) {
//            ensureTarget();
//        }
//        if (mTarget == null) {
//            return;
//        }
//        final View child = mTarget;
//        final int childLeft = getPaddingLeft();
//        final int childTop = getPaddingTop();
//        final int childWidth = width - getPaddingLeft() - getPaddingRight();
//        final int childHeight = height - getPaddingTop() - getPaddingBottom();
//        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
//        int headerWidth = refreshHeaderView.getMeasuredWidth();
//        int headerHeight = refreshHeaderView.getMeasuredHeight();
//        refreshHeaderView.layout(childLeft, childTop - headerHeight, childLeft
//                + childWidth, childTop);
//    }
//
//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (mTarget == null) {
//            ensureTarget();
//        }
//        if (mTarget == null) {
//            return;
//        }
//        mTarget.measure(MeasureSpec.makeMeasureSpec(
//                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
//                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
//                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
//        headerViewIndex = -1;
//        // Get the index of the headerView.
//        for (int index = 0; index < getChildCount(); index++) {
//            if (getChildAt(index) == refreshHeaderView) {
//                headerViewIndex = index;
//                break;
//            }
//        }
//    }
//
//    /**
//     * Get the diameter of the progress circle that is displayed as part of the
//     * swipe to refresh layout.
//     *
//     * @return Diameter in pixels of the progress circle view.
//     */
//    public int getProgressCircleDiameter() {
//        return mCircleDiameter;
//    }
//
//    public boolean canChildScrollUp() {
//        if (mChildScrollUpCallback != null) {
//            return mChildScrollUpCallback.canChildScrollUp(this, mTarget);
//        }
//        if (android.os.Build.VERSION.SDK_INT < 14) {
//            if (mTarget instanceof AbsListView) {
//                final AbsListView absListView = (AbsListView) mTarget;
//                return absListView.getChildCount() > 0
//                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
//                        .getTop() < absListView.getPaddingTop());
//            } else {
//                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
//            }
//        } else {
//            return ViewCompat.canScrollVertically(mTarget, -1);
//        }
//    }
//
//    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback callback) {
//        mChildScrollUpCallback = callback;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        ensureTarget();
//
//        final int action = MotionEventCompat.getActionMasked(ev);
//        int pointerIndex;
//
//        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
//            mReturningToStart = false;
//        }
//
//        if (!isEnabled() || mReturningToStart || canChildScrollUp()
//                || mRefreshing || mNestedScrollInProgress) {
//            // Fail fast if we're not in a state where a swipe is possible
//            return false;
//        }
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                setTargetOffsetTopAndBottom(mOriginalOffsetTop - refreshHeaderView.getTop(), true);
//                mActivePointerId = ev.getPointerId(0);
//                mIsBeingDragged = false;
//
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    return false;
//                }
//                mInitialDownY = ev.getY(pointerIndex);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                if (mActivePointerId == INVALID_POINTER) {
//                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
//                    return false;
//                }
//
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    return false;
//                }
//                final float y = ev.getY(pointerIndex);
//                startDragging(y);
//                break;
//
//            case MotionEventCompat.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
//                break;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                mIsBeingDragged = false;
//                mActivePointerId = INVALID_POINTER;
//                break;
//        }
//
//        return mIsBeingDragged;
//    }
//
//    @Override
//    public void requestDisallowInterceptTouchEvent(boolean b) {
//        // if this is a List < L or another view that doesn't support nested
//        // scrolling, ignore this request so that the vertical scroll event
//        // isn't stolen
//        if ((android.os.Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
//                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
//            // Nope.
//        } else {
//            super.requestDisallowInterceptTouchEvent(b);
//        }
//    }
//
//    // NestedScrollingParent
//
//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return isEnabled() && !mReturningToStart && !mRefreshing
//                && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//    }
//
//    @Override
//    public void onNestedScrollAccepted(View child, View target, int axes) {
//        // Reset the counter of how much leftover scroll needs to be consumed.
//        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
//        // Dispatch up to the nested parent
//        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
//        mTotalUnconsumed = 0;
//        mNestedScrollInProgress = true;
//    }
//
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        // If we are in the middle of consuming, a scroll, then we want to move the spinner back up
//        // before allowing the list to scroll
//        if (dy > 0 && mTotalUnconsumed > 0) {
//            if (dy > mTotalUnconsumed) {
//                consumed[1] = dy - (int) mTotalUnconsumed;
//                mTotalUnconsumed = 0;
//            } else {
//                mTotalUnconsumed -= dy;
//                consumed[1] = dy;
//            }
//            moveSpinner(mTotalUnconsumed);
//        }
//
//        // If a client layout is using a custom start position for the circle
//        // view, they mean to hide it again before scrolling the child view
//        // If we get back to mTotalUnconsumed == 0 and there is more to go, hide
//        // the circle so it isn't exposed if its blocking content is moved
////        if (mUsingCustomStart && dy > 0 && mTotalUnconsumed == 0
////                && Math.abs(dy - consumed[1]) > 0) {
////            mCircleView.setVisibility(View.GONE);
////        }
//
//        // Now let our nested parent consume the leftovers
//        final int[] parentConsumed = mParentScrollConsumed;
//        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
//            consumed[0] += parentConsumed[0];
//            consumed[1] += parentConsumed[1];
//        }
//    }
//
//    @Override
//    public int getNestedScrollAxes() {
//        return mNestedScrollingParentHelper.getNestedScrollAxes();
//    }
//
//    @Override
//    public void onStopNestedScroll(View target) {
//        mNestedScrollingParentHelper.onStopNestedScroll(target);
//        mNestedScrollInProgress = false;
//        // Finish the spinner for nested scrolling if we ever consumed any
//        // unconsumed nested scroll
//        if (mTotalUnconsumed > 0) {
//            finishSpinner(mTotalUnconsumed);
//            mTotalUnconsumed = 0;
//        }
//        // Dispatch up our nested parent
//        stopNestedScroll();
//    }
//
//    @Override
//    public void onNestedScroll(final View target, final int dxConsumed, final int dyConsumed,
//                               final int dxUnconsumed, final int dyUnconsumed) {
//        // Dispatch up to the nested parent first
//        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
//                mParentOffsetInWindow);
//
//        // This is a bit of a hack. Nested scrolling works from the bottom up, and as we are
//        // sometimes between two nested scrolling views, we need a way to be able to know when any
//        // nested scrolling parent has stopped handling events. We do that by using the
//        // 'offset in window 'functionality to see if we have been moved from the event.
//        // This is a decent indication of whether we should take over the event stream or not.
//        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
//        if (dy < 0 && !canChildScrollUp()) {
//            mTotalUnconsumed += Math.abs(dy);
//            moveSpinner(mTotalUnconsumed);
//        }
//    }
//
//    // NestedScrollingChild
//
//    @Override
//    public void setNestedScrollingEnabled(boolean enabled) {
//        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
//    }
//
//    @Override
//    public boolean isNestedScrollingEnabled() {
//        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
//    }
//
//    @Override
//    public boolean startNestedScroll(int axes) {
//        return mNestedScrollingChildHelper.startNestedScroll(axes);
//    }
//
//    @Override
//    public void stopNestedScroll() {
//        mNestedScrollingChildHelper.stopNestedScroll();
//    }
//
//    @Override
//    public boolean hasNestedScrollingParent() {
//        return mNestedScrollingChildHelper.hasNestedScrollingParent();
//    }
//
//    @Override
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
//                                        int dyUnconsumed, int[] offsetInWindow) {
//        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
//                dxUnconsumed, dyUnconsumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
//        return mNestedScrollingChildHelper.dispatchNestedPreScroll(
//                dx, dy, consumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX,
//                                    float velocityY) {
//        return dispatchNestedPreFling(velocityX, velocityY);
//    }
//
//    @Override
//    public boolean onNestedFling(View target, float velocityX, float velocityY,
//                                 boolean consumed) {
//        return dispatchNestedFling(velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
//        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
//        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
//    }
//
//    private boolean isAnimationRunning(Animation animation) {
//        return animation != null && animation.hasStarted() && !animation.hasEnded();
//    }
//
//    private void moveSpinner(float overscrollTop) {
//        float originalDragPercent = overscrollTop / mTotalDragDistance;
//
//        float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
//        float adjustedPercent = (float) Math.max(dragPercent - .4, 0) * 5 / 3;
//        float extraOS = Math.abs(overscrollTop) - mTotalDragDistance;
//        float slingshotDist = mUsingCustomStart ? mSpinnerFinalOffset - mOriginalOffsetTop
//                : mSpinnerFinalOffset;
//        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2)
//                / slingshotDist);
//        float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
//                (tensionSlingshotPercent / 4), 2)) * 2f;
//        float extraMove = (slingshotDist) * tensionPercent * 2;
//
//        int targetY = mOriginalOffsetTop + (int) ((slingshotDist * dragPercent) + extraMove);
//
//
//        setTargetOffsetTopAndBottom(targetY - mCurrentTargetOffsetTop, true /* requires update */);
//    }
//
//    private void finishSpinner(float overscrollTop) {
//        if (overscrollTop > mTotalDragDistance) {
//            setRefreshing(true, true /* notify */);
//        } else {
//            // cancel refresh
//            mRefreshing = false;
////            mProgress.setStartEndTrim(0f, 0f);
//            Animation.AnimationListener listener = null;
//            if (!mScale) {
//                listener = new Animation.AnimationListener() {
//
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        if (!mScale) {
//                            startScaleDownAnimation(null);
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//                    }
//
//                };
//            }
//            animateOffsetToStartPosition(mCurrentTargetOffsetTop, listener);
////            mProgress.showArrow(false);
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        final int action = MotionEventCompat.getActionMasked(ev);
//        int pointerIndex = -1;
//
//        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
//            mReturningToStart = false;
//        }
//
//        if (!isEnabled() || mReturningToStart || canChildScrollUp()
//                || mRefreshing || mNestedScrollInProgress) {
//            return false;
//        }
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mActivePointerId = ev.getPointerId(0);
//                mIsBeingDragged = false;
//                break;
//
//            case MotionEvent.ACTION_MOVE: {
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
//                    return false;
//                }
//
//                final float y = ev.getY(pointerIndex);
//                startDragging(y);
//
//                if (mIsBeingDragged) {
//                    final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
//                    if (overscrollTop > 0) {
//                        moveSpinner(overscrollTop);
//                    } else {
//                        return false;
//                    }
//                }
//                break;
//            }
//            case MotionEventCompat.ACTION_POINTER_DOWN: {
//                pointerIndex = MotionEventCompat.getActionIndex(ev);
//                if (pointerIndex < 0) {
//                    Log.e(LOG_TAG,
//                            "Got ACTION_POINTER_DOWN event but have an invalid action index.");
//                    return false;
//                }
//                mActivePointerId = ev.getPointerId(pointerIndex);
//                break;
//            }
//
//            case MotionEventCompat.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
//                break;
//
//            case MotionEvent.ACTION_UP: {
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
//                    return false;
//                }
//
//                if (mIsBeingDragged) {
//                    final float y = ev.getY(pointerIndex);
//                    final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
//                    mIsBeingDragged = false;
//                    finishSpinner(overscrollTop);
//                }
//                mActivePointerId = INVALID_POINTER;
//                return false;
//            }
//            case MotionEvent.ACTION_CANCEL:
//                return false;
//        }
//
//        return true;
//    }
//
//    private void startDragging(float y) {
//        final float yDiff = y - mInitialDownY;
//        if (yDiff > mTouchSlop && !mIsBeingDragged) {
//            mInitialMotionY = mInitialDownY + mTouchSlop;
//            mIsBeingDragged = true;
//        }
//    }
//
//    private void animateOffsetToCorrectPosition(int from, AnimationListener listener) {
//        mFrom = from;
//        mAnimateToCorrectPosition.reset();
//        mAnimateToCorrectPosition.setDuration(ANIMATE_TO_TRIGGER_DURATION);
//        mAnimateToCorrectPosition.setInterpolator(mDecelerateInterpolator);
//        if (listener != null) {
//            mCircleView.setAnimationListener(listener);
//        }
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(mAnimateToCorrectPosition);
//    }
//
//    private void animateOffsetToStartPosition(int from, AnimationListener listener) {
//        if (mScale) {
//            // Scale the item back down
//            startScaleDownReturnToStartAnimation(from, listener);
//        } else {
//            mFrom = from;
//            mAnimateToStartPosition.reset();
//            mAnimateToStartPosition.setDuration(ANIMATE_TO_START_DURATION);
//            mAnimateToStartPosition.setInterpolator(mDecelerateInterpolator);
//            if (listener != null) {
//                mCircleView.setAnimationListener(listener);
//            }
//            mCircleView.clearAnimation();
//            mCircleView.startAnimation(mAnimateToStartPosition);
//        }
//    }
//
//    private final Animation mAnimateToCorrectPosition = new Animation() {
//        @Override
//        public void applyTransformation(float interpolatedTime, Transformation t) {
//            int targetTop = 0;
//            int endTarget = 0;
//            if (!mUsingCustomStart) {
//                endTarget = (int) (mSpinnerFinalOffset - Math.abs(mOriginalOffsetTop));
//            } else {
//                endTarget = (int) mSpinnerFinalOffset;
//            }
//            targetTop = (mFrom + (int) ((endTarget - mFrom) * interpolatedTime));
//            int offset = targetTop - refreshHeaderView.getTop();
//            setTargetOffsetTopAndBottom(offset, false /* requires update */);
//            mProgress.setArrowScale(1 - interpolatedTime);
//        }
//    };
//
//    void moveToStart(float interpolatedTime) {
//        int targetTop = 0;
//        targetTop = (mFrom + (int) ((mOriginalOffsetTop - mFrom) * interpolatedTime));
//        int offset = targetTop - refreshHeaderView.getTop();
//        setTargetOffsetTopAndBottom(offset, false /* requires update */);
//    }
//
//    private final Animation mAnimateToStartPosition = new Animation() {
//        @Override
//        public void applyTransformation(float interpolatedTime, Transformation t) {
//            moveToStart(interpolatedTime);
//        }
//    };
//
//    private void startScaleDownReturnToStartAnimation(int from,
//                                                      Animation.AnimationListener listener) {
//        mFrom = from;
//        mScaleDownToStartAnimation = new Animation() {
//            @Override
//            public void applyTransformation(float interpolatedTime, Transformation t) {
//                float targetScale = (mStartingScale + (-mStartingScale * interpolatedTime));
//                setAnimationProgress(targetScale);
//                moveToStart(interpolatedTime);
//            }
//        };
//        mScaleDownToStartAnimation.setDuration(SCALE_DOWN_DURATION);
//        mCircleView.setAnimationListener(listener);
//    }
//
//    mCircleView.clearAnimation();
//    mCircleView.startAnimation(mScaleDownToStartAnimation);
//}
//
//    void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
//        scrollTo(0, offset);
//        mCurrentTargetOffsetTop = targetView.getTop();
//        if (requiresUpdate && android.os.Build.VERSION.SDK_INT < 11) {
//            invalidate();
//        }
//    }
//
//    private void onSecondaryPointerUp(MotionEvent ev) {
//        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
//        final int pointerId = ev.getPointerId(pointerIndex);
//        if (pointerId == mActivePointerId) {
//            // This was our active pointer going up. Choose a new
//            // active pointer and adjust accordingly.
//            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//            mActivePointerId = ev.getPointerId(newPointerIndex);
//        }
//    }
//
///**
// * Classes that wish to be notified when the swipe gesture correctly
// * triggers a refresh should implement this interface.
// */
//public interface OnRefreshListener {
//    /**
//     * Called when a swipe gesture triggers a refresh.
//     */
//    void onRefresh();
//}
//
//public interface OnChildScrollUpCallback {
//    boolean canChildScrollUp(SwipeRefreshLayout2 parent, @Nullable View child);
//}
//}
