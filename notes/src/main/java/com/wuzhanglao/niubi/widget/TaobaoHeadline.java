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

import java.util.List;

/**
 * Created by wuming on 16/10/16.
 */

public class TaobaoHeadline extends RelativeLayout {

    private static final String TAG = TaobaoHeadline.class.getSimpleName();
    private HeadlineClickListener listener;
    private ViewSwitcher viewSwitcher;
    private List<HeadlineBean> data;
    private RelativeLayout subView1, subView2;
    private int currentPosition = 0;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentPosition++;
            final ViewHolder holder = (ViewHolder) ((currentPosition % 2) == 0 ? subView1.getTag() : subView2.getTag());
            holder.title_tv.setText(data.get(currentPosition % data.size()).getTitle());
            holder.content_tv.setText(data.get(currentPosition % data.size()).getContent());
            viewSwitcher.setDisplayedChild(currentPosition % 2);
            postDelayed(runnable, 4000);
        }
    };
    private OnClickListener headlineItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onHeadlineClick(data.get(currentPosition % data.size()));
            }
        }
    };

    public TaobaoHeadline(Context context) {
        this(context, null);
    }

    public TaobaoHeadline(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.taobao_headline_layout, this, true);
        viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.taobao_headline_viewswitcher);
        if (subView1 == null) {
            subView1 = (RelativeLayout) viewSwitcher.findViewById(R.id.subView1);
            final ViewHolder holder = new ViewHolder();
            holder.title_tv = (TextView) subView1.findViewById(R.id.headline_title_tv);
            holder.content_tv = (TextView) subView1.findViewById(R.id.headline_content_tv);
            holder.title_tv.setText(data.get(0).getTitle());
            holder.content_tv.setText(data.get(0).getContent());
            subView1.setTag(holder);
            subView1.setOnClickListener(headlineItemClickListener);
        }
        if (subView2 == null) {
            subView2 = (RelativeLayout) viewSwitcher.findViewById(R.id.subView2);
            final ViewHolder holder = new ViewHolder();
            holder.title_tv = (TextView) subView2.findViewById(R.id.headline_title_tv);
            holder.content_tv = (TextView) subView2.findViewById(R.id.headline_content_tv);
            subView2.setTag(holder);
            subView2.setOnClickListener(headlineItemClickListener);
        }
        findViewById(R.id.taobao_headline_more_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMoreClick();
                }
            }
        });
        viewSwitcher.setDisplayedChild(0);
        //进入动画
        viewSwitcher.setInAnimation(getContext(), R.anim.headline_in);
        //退出动画
        viewSwitcher.setOutAnimation(getContext(), R.anim.headline_out);
        if (data.size() != 1) {
            post(runnable);
        }
    }

    //配置滚动的数据
    public void setData(List<HeadlineBean> data) {
        this.data = data;
        initView();
    }

    public void setHeadlineClickListener(HeadlineClickListener listener) {
        this.listener = listener;
    }

    public interface HeadlineClickListener {
        void onHeadlineClick(HeadlineBean bean);

        void onMoreClick();
    }

    private class ViewHolder {
        TextView title_tv;
        TextView content_tv;
    }
}
