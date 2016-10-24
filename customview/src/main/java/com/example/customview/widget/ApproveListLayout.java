package com.example.customview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.customview.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 2016/10/23.
 */

public class ApproveListLayout extends RelativeLayout {

    private static final String TAG = ApproveListLayout.class.getSimpleName();

    //图片大小
    private static final int PIC_SIZE = 50;
    //图片数量
    private static final int PIC_COUNT = 6;
    //图片偏移百分比 0～1
    private static final float PIC_OFFSET = 0.3f;

    private Context context;
    private List<ImageView> headList;

    private int picSize = UIUtils.dp2px(PIC_SIZE);
    private int picCount = PIC_COUNT;
    private float picOffset = PIC_OFFSET;

    public ApproveListLayout(Context context) {
        this(context, null);
    }

    public ApproveListLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApproveListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ApproveListLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        int offset = picSize - (int) (picSize * picOffset);
        headList = new ArrayList<>(picCount);
        for (int i = 0; i < picCount; i++) {
            ImageView head = new ImageView(context);
            head.setId(head.hashCode() + i);
            head.setBackgroundColor(Color.GRAY);
            LayoutParams params = new LayoutParams(picSize, picSize);
            params.addRule(ALIGN_PARENT_RIGHT);
            params.setMargins(0, 0, i * (offset), 0);
            this.addView(head, params);
            headList.add(head);
        }
    }

    public void setPicSize(int picSize) {
        this.picSize = picSize;
        init();
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
        init();
    }

    public void setPicOffset(int offset) {
        picOffset = UIUtils.dp2px(offset);
    }

    public void updateApproveList(List<String> urlList) {
//        if (headList == null) {
//        }
//        requestLayout();
//        invalidate();
//        Log.e(TAG,"childCount->"+getChildCount());
    }
}
