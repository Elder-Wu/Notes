package com.wuzhanglao.niubi.widget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by wuming on 2016/11/28.
 */

public class BiliBiliBarrage extends RelativeLayout {

    private Vector<TextView> subtitles = new Vector<>();

    public BiliBiliBarrage(Context context) {
        super(context);
    }

    public BiliBiliBarrage(Context context, AttributeSet attrs) {
        super(context, attrs);
        //父布局的onDraw()方法会被执行
        setWillNotDraw(false);
    }

    public void addText(String subtitle) {
        TextView text = new TextView(getContext());
        text.setText(subtitle);
        subtitles.add(text);
    }

    private final class MyAnimatorListener implements Animator.AnimatorListener {

        private View view;

        public MyAnimatorListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            removeView(view);
            subtitles.remove(view);
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
