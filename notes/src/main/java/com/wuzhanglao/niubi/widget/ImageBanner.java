package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuming on 2016/11/3.
 */

public class ImageBanner extends RelativeLayout {

    private ViewPager mViewPager;
    private LinearLayout ll_dots;

    private ArrayList<View> images = new ArrayList<>();

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
        public Object instantiateItem(ViewGroup container, int position) {
            int position_in_data = position % images.size();
            View image = images.get(position_in_data);
            ViewParent parent = image.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(image);
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
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(adapter);
        addView(mViewPager);

        ll_dots = new LinearLayout(context);
        ll_dots.setOrientation(LinearLayout.HORIZONTAL);
        ll_dots.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll_dots.setGravity(CENTER_IN_PARENT);
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
        mViewPager.setCurrentItem(images.size() % 1024);

        CircleImageView dot = new CircleImageView(getContext());
        dot.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
//        dot.setImageResource(new ColorDrawable(0xFF0000).getColor());
//        ll_dots.addView();
    }

    private class Dot extends View {

        public Dot(Context context) {
            super(context);
        }

        public Dot(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


    }
}
