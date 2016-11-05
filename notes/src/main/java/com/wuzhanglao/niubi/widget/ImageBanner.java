package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by wuming on 2016/11/3.
 */

public class ImageBanner extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private LinearLayout ll_dots;

    private ArrayList<View> images = new ArrayList<>();
    private ArrayList<Dot> dots = new ArrayList<>();

    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int position_in_data = position % images.size();
            View image = images.get(position_in_data);
            container.addView(image);
            return image;
        }
    };

    public ImageBanner(Context context) {
        this(context, null);
    }

    public ImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager);

        ll_dots = new LinearLayout(context);
        ll_dots.setOrientation(LinearLayout.HORIZONTAL);
        ll_dots.setGravity(CENTER_IN_PARENT);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        addView(ll_dots, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void addImage(View view) {
        images.add(view);
//        mViewPager.setCurrentItem(images.size() % 1024);

        Dot dot = new Dot(getContext());
        dots.add(dot);
        ll_dots.addView(dot);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int position_in_data = position % images.size();
        for (Dot dot : dots) {
            dot.setDotColor(Color.GRAY);
        }
        dots.get(position_in_data).setDotColor(Color.RED);
        requestLayout();
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private Paint dotPaint;

    private class Dot extends View {

        private Canvas dotCanvas;
        private int dotColor = Color.GRAY;

        public Dot(Context context) {
            super(context);
        }

        public Dot(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (dotPaint == null) {
                dotPaint = new Paint();
                dotPaint.setAntiAlias(true);
                dotPaint.setDither(true);
                dotPaint.setStyle(Paint.Style.FILL);
            }
            dotPaint.setColor(dotColor);
            if (dotCanvas == null) {
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                dotCanvas = new Canvas(bitmap);
                dotCanvas.drawCircle(width / 2, height / 2, width / 2, dotPaint);
                canvas.drawBitmap(bitmap, 0, 0, dotPaint);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(40, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(40, MeasureSpec.EXACTLY);
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

        public void setDotColor(int color) {
            dotColor = color;
            invalidate();
        }
    }
}
