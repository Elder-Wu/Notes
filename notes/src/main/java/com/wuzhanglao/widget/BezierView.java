package com.wuzhanglao.niubi.widget;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.utils.UIUtils;
import com.wuzhanglao.niubi.utils.nine_old_android.ViewHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wuming on 2016/11/15.
 */

public class BezierView extends RelativeLayout {

    private static final String TAG = BezierView.class.getSimpleName();
    private static final int DEFAULT_PIC_SIZE = 40;
    private Paint paint;
    private Bitmap bitmap;
    private PointF startPointF;
    private Random random = new Random();
    private ArrayList<View> hearts = new ArrayList<View>();

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //改成false，就可以在布局文件中预览爱心
        setWillNotDraw(true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);

        if (getParent() != null) {
            ((ViewGroup) getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addHeartView();
                }
            });
        } else {
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addHeartView();
                }
            });
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF dest = new RectF(getWidth() / 2 - 20, getHeight() - 40, getWidth() / 2 + 20, getHeight());
        canvas.drawBitmap(bitmap, src, dest, paint);
    }

    private RelativeLayout.LayoutParams params;

    private void addHeartView() {
        if (params == null) {
            params = new RelativeLayout.LayoutParams(Math.round(UIUtils.dp2px(DEFAULT_PIC_SIZE)), Math.round(UIUtils.dp2px(DEFAULT_PIC_SIZE)));
            params.addRule(ALIGN_PARENT_BOTTOM);
            params.addRule(CENTER_HORIZONTAL);
            params.setMargins(10, 10, 10, 10);
        }
        final ImageView heart = new ImageView(getContext());
        heart.setImageResource(R.drawable.heart);
        heart.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(heart, params);
        hearts.add(heart);
        start(heart);
    }

    //动画差值器
    private Interpolator animatorInterpolator = new AccelerateDecelerateInterpolator();

    private void start(final View heart) {
        //计算出显示区域的大小
        final int show_area_height = getHeight();
        final int show_area_width = getWidth();
        //起始点
        if (startPointF == null) {
            startPointF = new PointF((show_area_width - UIUtils.dp2px(DEFAULT_PIC_SIZE)) / 2, show_area_height - UIUtils.dp2px(DEFAULT_PIC_SIZE));
        }
        //控制点1
        final PointF pointF1 = new PointF(show_area_width / 2 - (random.nextInt() % show_area_width) / 3, (show_area_height / 2) + (random.nextInt() % show_area_height) / 3);
        //控制点2
        final PointF pointF2 = new PointF(show_area_width / 2 + (random.nextInt() % show_area_width) / 3, (show_area_height / 2) + (random.nextInt() % show_area_height) / 3);
        //终点
        final PointF endPointF = new PointF((System.currentTimeMillis() % show_area_width), 0);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(pointF1, pointF2), startPointF, endPointF);
        valueAnimator.setDuration(4000);
        valueAnimator.setInterpolator(animatorInterpolator);
        valueAnimator.addListener(new AnimatorListenerAdapter(heart));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final PointF point = (PointF) animation.getAnimatedValue();
                heart.setX(point.x);
                heart.setY(point.y);
                //设置透明度
                ViewHelper.setAlpha(heart, (1 - animation.getAnimatedFraction()) * 2);
            }
        });
        valueAnimator.start();
    }

    /**
     * 三阶贝塞尔曲线计算公式
     * B(t)=(P0)(1-t)^3+3(P1)t(1-t)^2+3(P2)t^2(1-t)+(P3)t^3  (0≤t≤1)
     *
     * @link TypeEvaluator和Interpolator教程：http://www.cnblogs.com/wondertwo/p/5327586.html
     */
    private class BezierEvaluator implements TypeEvaluator<PointF> {
        private PointF pointF1;
        private PointF pointF2;

        public BezierEvaluator(PointF pointF1, PointF pointF2) {
            this.pointF1 = pointF1;
            this.pointF2 = pointF2;
        }

        @Override
        public PointF evaluate(float time, PointF startValue,
                               PointF endValue) {

            float timeLeft = 1.0f - time;
            PointF point = new PointF();//结果

            point.x = timeLeft * timeLeft * timeLeft * (startValue.x)
                    + 3 * timeLeft * timeLeft * time * (pointF1.x)
                    + 3 * timeLeft * time * time * (pointF2.x)
                    + time * time * time * (endValue.x);

            point.y = timeLeft * timeLeft * timeLeft * (startValue.y)
                    + 3 * timeLeft * timeLeft * time * (pointF1.y)
                    + 3 * timeLeft * time * time * (pointF2.y)
                    + time * time * time * (endValue.y);
            return point;
        }
    }

    private class AnimatorListenerAdapter implements Animator.AnimatorListener {

        private View view;

        public AnimatorListenerAdapter(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            removeView(view);
            hearts.remove(hearts.indexOf(view));
            view = null;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
