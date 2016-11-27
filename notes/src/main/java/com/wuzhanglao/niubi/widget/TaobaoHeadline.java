package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.mvp.model.HeadlineBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 16/10/16.
 */

public class TaobaoHeadline extends RelativeLayout {

    private static final String TAG = TaobaoHeadline.class.getSimpleName();
    private HeadlineClickListener listener;
    private LayoutInflater inflater;
    private ViewSwitcher viewSwitcher;
    private List<View> data = new ArrayList<>();
    private List<HeadlineBean> beanList;
    private RelativeLayout subView1, subView2;

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
        viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.taobao_headline_viewswitcher);
        if(subView1==null){
            subView1 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.headline_holder,this,true);
        }
        if(subView2==null){
            subView2 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.headline_holder,this,true);
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
        viewSwitcher.setInAnimation(getContext(), R.anim.headline_in);
        //退出动画
        viewSwitcher.setOutAnimation(getContext(), R.anim.headline_out);
        postDelayed(runnable, 2000);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewSwitcher.showNext();
            postDelayed(runnable, 2000);
        }
    };

    //配置滚动的数据
    public void setData(List<HeadlineBean> data) {
        this.beanList = data;
//        convertData(data);
        initView();
    }

    //将HeadlineBean数据转换成View数据
    private void convertData(final List<HeadlineBean> list) {
        for (final HeadlineBean bean : list) {
            final HeadlineBean b = bean;
            final View view = inflater.inflate(R.layout.headline_holder, viewSwitcher, false);
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
