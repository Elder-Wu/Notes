package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.wuzhanglao.niubi.utils.UIUtils;
import com.wuzhanglao.niubi.utils.transformer.CubeOutTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 2016/11/3.
 */

public class Banner extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    private static final String TAG = Banner.class.getSimpleName();

    //指示器选中时和未选中时的颜色
    private static final int UNSELECTED_DOT_COLOR = Color.LTGRAY;
    private static final int SELECTED_DOT_COLOR = Color.DKGRAY;
    //指示器的圆点大小  单位：dp
    private static final int DEFAULT_DOT_SIZE = Math.round(UIUtils.dp2px(8));
    //每个小圆点之间的距离
    private static final int DEFAULT_DOT_SPAN = Math.round(UIUtils.dp2px(3));
    //页面切换时间
    private static final int SCROLL_DURATION = 500;
    //页面展示时间
    private static final int DISPLAY_TIME = 3000;

    private ViewPager mViewPager;
    private LinearLayout ll_dots;

    private ArrayList<View> views = new ArrayList<>();//视图列表
    private ArrayList<Dot> dots = new ArrayList<>();

    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
//            return views.size();
            if (views.size() == 1) {
                return 1;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // container.removeView((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int position_in_data = position % views.size();
            final View child = views.get(position_in_data);
            if (child.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) child.getParent();
                viewGroup.removeView(child);
            }
            container.addView(child);
            return child;
        }
    };

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        //动态添加ViewPager实例
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOnTouchListener(this);
        mViewPager.setPageTransformer(true, new CubeOutTransformer());
        setDefaultDuration();
        addView(mViewPager);

        //动态添加一个LinearLayout
        ll_dots = new LinearLayout(context);
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(Math.round(UIUtils.dp2px(5)),Math.round(UIUtils.dp2px(5)),Math.round(UIUtils.dp2px(5)),Math.round(UIUtils.dp2px(5)));
        addView(ll_dots, params);
    }

    /**
     * 利用反射原理来改变ViewPager页面切换时间
     * <p>
     * android动画差值器使用方法
     *
     * @link 博客地址：http://blog.csdn.net/sun_star1chen/article/details/12843741
     */
    private void setDefaultDuration() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            Scroller scroller = new Scroller(getContext(), new LinearInterpolator()) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, SCROLL_DURATION);
                }

                @Override
                public void startScroll(int startX, int startY, int dx, int dy) {
                    super.startScroll(startX, startY, dx, dy, SCROLL_DURATION);
                }
            };
            field.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 页面切换时的动画
     * <p>
     * #{ViewPagerTransforms}项目为我们提供了一些现成的PageTransformer来实现一些动画
     *
     * @link git地址：https://github.com/ToxicBakery/ViewPagerTransforms
     */
    private class MyPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(1);
            } else if (position <= 0) {
                //ViewPager正在滑动时，页面左边的View       -1～0
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);
            } else if (position <= 1) {
                //ViewPager正在滑动时，页面右边的View       0～1
                page.setAlpha(1 - position);
                page.setTranslationX(0);
                page.setScaleX((float) (1 - position * 0.8));
                page.setScaleY((float) (1 - position * 0.8));
            } else {
                page.setAlpha(1);
            }
        }
    }

    private LinearLayout.LayoutParams params;

    //初始化数据
    public void addContent(View view) {
        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(DEFAULT_DOT_SPAN, DEFAULT_DOT_SPAN, DEFAULT_DOT_SPAN, DEFAULT_DOT_SPAN);
        }
        Dot dot = new Dot(getContext());
        dots.add(dot);
        ll_dots.addView(dot, params);
        views.add(view);
        adapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(views.size() * 1024);
        refreshDots(0);
    }

    public void addContentList(List<View> viewList) {
        if (viewList == null || viewList.size() == 0) {
            return;
        }
        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
        }
        for (View view : viewList) {
            Dot dot = new Dot(getContext());
            dots.add(dot);
            ll_dots.addView(dot, params);
            views.add(view);
            adapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(views.size() * 1024);
            refreshDots(0);
        }
    }

    private void refreshDots(int position_in_data) {
        for (Dot dot : dots) {
            dot.setDotColor(UNSELECTED_DOT_COLOR);
        }
        dots.get(position_in_data).setDotColor(SELECTED_DOT_COLOR);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int position_in_data = position % views.size();
        refreshDots(position_in_data);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //定义Dot类，可以表示当前viewpager处于第几个页面
    private class Dot extends View {

        private Paint dotPaint;
        private int dotColor = UNSELECTED_DOT_COLOR;

        public Dot(Context context) {
            super(context);

            dotPaint = new Paint();
            dotPaint.setAntiAlias(true);
            dotPaint.setDither(true);
            dotPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            dotPaint.setColor(dotColor);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, dotPaint);
        }

        //这一步的作用是把dot的宽度和高度定死
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_DOT_SIZE, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_DOT_SIZE, MeasureSpec.EXACTLY);
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

        //修改dot的颜色，并且让整个dot重绘
        public void setDotColor(int color) {
            dotColor = color;
            invalidate();
        }
    }


    private Handler hander = new Handler();

    private Runnable runable = new Runnable() {
        @Override
        public void run() {
            //true表示平滑滚动
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            hander.postDelayed(runable, DISPLAY_TIME);
        }
    };

    public void startScroll() {
        hander.postDelayed(runable, DISPLAY_TIME);
    }

    public void stopScroll() {
        hander.removeCallbacks(runable);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopScroll();
                break;
            case MotionEvent.ACTION_UP:
                startScroll();
                break;
        }
        return false;
    }
}
