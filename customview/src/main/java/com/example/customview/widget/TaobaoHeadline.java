package com.example.customview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.customview.R;
import com.example.customview.bean.HeadlineBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 16/10/16.
 */

public class TaobaoHeadline extends RelativeLayout {

    private static final String TAG = TaobaoHeadline.class.getSimpleName();
    private HeadlineClickListener listener;

    private LayoutInflater inflater;
    private ViewFlipper viewFlipper;
    private List<View> data = new ArrayList<>();

    public TaobaoHeadline(Context context) {
        this(context, null);
    }

    public TaobaoHeadline(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.taobao_headline_layout, this, true);
        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.taobao_headline_viewflipper);
        for (View view : data) {
            viewFlipper.addView(view);
        }
        findViewById(R.id.taobao_headline_more_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMoreClick();
                }
            }
        });
        //进入动画
        viewFlipper.setInAnimation(getContext(), R.anim.headline_in);
        //退出动画
        viewFlipper.setOutAnimation(getContext(), R.anim.headline_out);
        //动画间隔
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    //配置滚动的数据
    public void setData(List<HeadlineBean> data) {
        convertData(data);
        initView();
    }

    //将HeadlineBean数据转换成View数据
    private void convertData(final List<HeadlineBean> list) {
        for (final HeadlineBean bean : list) {
            final HeadlineBean b = bean;
            final View view = inflater.inflate(R.layout.headline_holder, viewFlipper, false);
            final TextView headline_title = (TextView) view.findViewById(R.id.headline_title_tv);
            final TextView headline_content = (TextView) view.findViewById(R.id.headline_content_tv);
            headline_title.setText(bean.getTitle());
            headline_content.setText(bean.getContent());
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onHeadlineClick(b);
                    }
                }
            });
            data.add(view);
        }
    }

    public void setHeadlineClickListener(HeadlineClickListener listener) {
        this.listener = listener;
    }

    public interface HeadlineClickListener {
        void onHeadlineClick(HeadlineBean bean);

        void onMoreClick();
    }
}
