package com.example.notes.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.example.notes.R;
import com.example.notes.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;

/**
 * Created by wuming on 2016/10/23.
 */

public class ApproveListLayout extends HorizontalScrollView {

    private static final String TAG = ApproveListLayout.class.getSimpleName();

    //默认图片大小
    private static final int DEFAULT_PIC_SIZE = 50;
    //默认图片数量
    private static final int DEFAULT_PIC_COUNT = 6;
    //默认图片偏移百分比 0～1
    private static final float DEFAULT_PIC_OFFSET = 0.3f;

    private Context context;
    private List<CircleImageView> headList;

    private int picSize = UIUtils.dp2px(DEFAULT_PIC_SIZE);
    private int picCount = DEFAULT_PIC_COUNT;
    private float picOffset = DEFAULT_PIC_OFFSET;

    public ApproveListLayout(Context context) {
        this(context, null);
    }

    public ApproveListLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApproveListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化自定义属性
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.ApproveListLayout);
        picCount = ta.getInt(R.styleable.ApproveListLayout_pic_count, DEFAULT_PIC_COUNT);
        picSize = (int) ta.getDimension(R.styleable.ApproveListLayout_pic_size, picSize);
        picOffset = ta.getFloat(R.styleable.ApproveListLayout_pic_offset, DEFAULT_PIC_OFFSET);
        picOffset = picOffset > 1 ? 1 : picOffset;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ApproveListLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        //初始化自定义属性
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.ApproveListLayout);
        picCount = ta.getInt(R.styleable.ApproveListLayout_pic_count, DEFAULT_PIC_COUNT);
        picSize = (int) ta.getDimension(R.styleable.ApproveListLayout_pic_size, picSize);
        picOffset = ta.getFloat(R.styleable.ApproveListLayout_pic_offset, DEFAULT_PIC_OFFSET);
        picOffset = picOffset > 1 ? 1 : picOffset;
        ta.recycle();
        init();
    }

    private void init() {
        setHorizontalScrollBarEnabled(false);
        //定义一个RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(context);
        int offset = picSize - (int) (picSize * picOffset);
        headList = new ArrayList<>(picCount);
        //循环把CircleImageView塞到RelativiLayout中，根据偏移量来摆放位置
        for (int i = 0; i < picCount; i++) {
            CircleImageView head = new CircleImageView(context);
            head.setId(head.hashCode() + i);
            head.setBorderColor(Color.WHITE);
            head.setBorderWidth(UIUtils.dp2px(1));
            head.setImageResource(R.drawable.demo);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(picSize, picSize);
            params.addRule(ALIGN_PARENT_LEFT);
            params.setMargins((picCount - i - 1) * offset, 0, 0, 0);
            relativeLayout.addView(head, params);
            headList.add(head);
        }
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //这里是关键：把定义的RelativeLayout放到布局中，这样才能显示出来
        this.addView(relativeLayout);
    }

    public void setPicSize(int picSize) {
        this.picSize = picSize;
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }

    public void setPicOffset(int offset) {
        picOffset = UIUtils.dp2px(offset);
    }

    public void initLayout() {
        init();
    }

    //根据传进来的头像列表来更新头像
    public void updateApproveList(List<Integer> urlList) {
        if (urlList == null) {
            return;
        }
        hideAllHeads();
        int i = picCount - 1;
        for (int url : urlList) {
            headList.get(i).setImageResource(url);
            headList.get(i).setVisibility(View.VISIBLE);
            if (i == 0) {
                break;
            }
            --i;
        }
    }

    private void hideAllHeads() {
        for (CircleImageView head : headList) {
            head.setVisibility(View.GONE);
        }
    }
}
