package com.example.customview.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.utils.ToastUtils;
import com.example.customview.widget.IosBottomDialog;

/**
 * Created by wuming on 16/10/15.
 */

public class AnimActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView actionBarTitle;

    @Override
    protected void initActionBar() {
        View actionBar = findViewById(R.id.activity_anim_actionbar);
        actionBarTitle = (TextView) actionBar.findViewById(R.id.actionbar_default_title);
        actionBarTitle.setText("Activity动画特效");
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_anim;
    }

    @Override
    protected void initView() {
        findViewById(R.id.activity_anim_btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_anim_btn1:
                ToastUtils.show("开始执行动画了");
                View decorView = getWindow().getDecorView();
                startAnimation(decorView);
                break;
        }
    }

    public void startAnimation(final View view) {
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
        rotationX.setStartDelay(200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.8f).setDuration(3500),
                ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.8f).setDuration(3500),
                ObjectAnimator.ofFloat(view, "rotation", 0f, 190).setDuration(2000),
                rotationX,
                ObjectAnimator.ofFloat(view, "translationY", 0, -0.1f * getResources().getDisplayMetrics().heightPixels).setDuration(350)
        );
        animatorSet.start();

    }
}
