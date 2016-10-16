package com.example.customview.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.ViewFlipper;

import com.example.customview.R;
import com.example.customview.adapter.HeadlineHolderAdapter;
import com.example.customview.bean.HeadlineBean;
import com.example.customview.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 16/10/16.
 */

public class TaobaoHeadline extends RelativeLayout {

    private static final String TAG = TaobaoHeadline.class.getSimpleName();

    private HeadlineHolderAdapter adapter;
    private RecyclerView recyclerView;
    private List<HeadlineBean> data = new ArrayList<>();
    private int current_poisition = 0;

    public TaobaoHeadline(Context context) {
        this(context, null);
    }

    public TaobaoHeadline(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.taobao_headline_layout, this, true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.taobao_headline_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HeadlineHolderAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);
    }

    public void setData(List<HeadlineBean> data) {
        this.data = data;
        initView();
        start();

    }

    public void setHeadlineClickListener(HeadlineHolderAdapter.HeadlineClickListener listener) {
        adapter.setHeadlineClickListener(listener);
    }

    private void start() {
        CountDownTimer timer = new CountDownTimer(Integer.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (current_poisition < data.size()) {
                    recyclerView.smoothScrollBy(0, UIUtils.dp2px(R.dimen.headline_height));
                    current_poisition++;
                } else {
                    current_poisition = 0;
                }
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

}
