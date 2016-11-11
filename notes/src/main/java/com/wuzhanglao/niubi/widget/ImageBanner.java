package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by wuming on 2016/11/3.
 */

public class ImageBanner extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private static final int UNSELECTED_DOT_COLOR = Color.LTGRAY;
    private static final int SELECTED_DOT_COLOR = Color.DKGRAY;
    private static final int DEFAULT_DOT_SIZE = 30;

    private ViewPager mViewPager;
    private LinearLayout ll_dots;

    private ArrayList<View> images = new ArrayList<>();
    private ArrayList<Dot> dots = new ArrayList<>();

    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
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
            int position_in_data = position % images.size();
            View image = images.get(position_in_data);
            if (image.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) image.getParent();
                viewGroup.removeView(image);
            }
            container.addView(image);
            return image;
        }
    };

    public ImageBanner(Context context) {
        this(context, null);
    }

    public ImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        //动态添加ViewPager实例
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager);

        //动态添加一个LinearLayout
        ll_dots = new LinearLayout(context);
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(10, 10, 10, 10);
        addView(ll_dots, params);
    }

    private LinearLayout.LayoutParams params;

    //初始化数据
    public void addImage(View view) {
        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
        }
        Dot dot = new Dot(getContext());
        dots.add(dot);
        ll_dots.addView(dot, params);
        images.add(view);
        adapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(images.size() * 1024);
        refreshDots(0);
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
        int position_in_data = position % images.size();
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
}
