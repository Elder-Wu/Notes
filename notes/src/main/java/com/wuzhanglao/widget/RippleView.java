package com.wuzhanglao.niubi.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wuming on 2017/10/27.
 */

public class RippleView extends View {

    private Paint mPaint = new Paint();

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        canvas.drawCircle(width / 2, width / 2, width / 2, mPaint);
    }

    public void startAnimator() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1, 2);
        animatorX.setDuration(500);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1, 2);
        animatorY.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.start();
    }
}