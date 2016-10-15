package com.example.customview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.customview.R;

/**
 * 仿QQ，微信底部icon，可以显示未读消息数角标
 * Created on 2016/10/11.
 */

public class BottomBar extends RelativeLayout {

    private Context context;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.bottom_bar_view, this, true);
        init();
    }

    private void init() {
    }
}
