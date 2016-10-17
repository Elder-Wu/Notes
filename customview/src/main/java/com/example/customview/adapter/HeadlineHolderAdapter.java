package com.example.customview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.customview.R;
import com.example.customview.bean.HeadlineBean;
import com.example.customview.holder.HeadlineHolder;
import com.example.customview.widget.TaobaoHeadline;

import java.util.List;

/**
 * Created by wuming on 16/10/16.
 */

public class HeadlineHolderAdapter extends BaseAdapter<HeadlineHolder, HeadlineBean> {

    private static final String TAG = HeadlineHolderAdapter.class.getSimpleName();
//    private HeadlineClickListener listener;

    public HeadlineHolderAdapter(Context context, List<HeadlineBean> data) {
        super(context, data);
    }

    @Override
    protected HeadlineHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new HeadlineHolder(inflater.inflate(R.layout.headline_holder, parent, false));
    }

    @Override
    protected void onBindHolder(HeadlineHolder holder, final int position_in_data) {
//        final HeadlineBean bean = data.get(position_in_data%data.size());
//        holder.title.setText(bean.getTitle());
//        holder.content.setText(bean.getContent());
//        holder.content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onHeadlineClick(data, position_in_data);
//                }
//            }
//        });
    }

    public void setHeadlineClickListener(TaobaoHeadline.HeadlineClickListener listener) {
//        this.listener = listener;
    }

//    public interface HeadlineClickListener {
//        void onHeadlineClick(List<HeadlineBean> data, int position_in_data);
//
//        void onHeadlineClick(HeadlineBean b);
//    }

}
