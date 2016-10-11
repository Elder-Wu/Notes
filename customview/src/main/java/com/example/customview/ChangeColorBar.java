package com.example.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created on 2016/10/11.
 */

public class ChangeColorBar extends RelativeLayout {

    private Context context;

    public ChangeColorBar(Context context) {
        this(context, null);
    }

    public ChangeColorBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.change_color_bar,this,true);
        init();
    }

    private void init() {
    }
}
