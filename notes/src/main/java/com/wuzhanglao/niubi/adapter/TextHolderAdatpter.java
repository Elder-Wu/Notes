package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.bean.TextBean;
import com.wuzhanglao.niubi.holder.TextHolder;

import java.util.List;

/**
 * Created by wuming on 16/10/13.
 */

public class TextHolderAdatpter extends BaseAdapter<TextHolder, TextBean> {

    private static final String TAG = TextHolderAdatpter.class.getSimpleName();
    private TextHolderClickListener listener;

    public TextHolderAdatpter(Context context, List<TextBean> data) {
        super(context, data);
    }

    @Override
    protected TextHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new TextHolder(inflater.inflate(R.layout.text_holder, parent, false));
    }

    @Override
    protected void onBindHolder(TextHolder holder, int position) {
        holder.num_tv.setText(position + 1 < 10 ? "0" + (position + 1) : "" + (position + 1));
        holder.title_tv.setText(data.get(position).getTitle());
        holder.subtitle_tv.setText(data.get(position).getSubTitle());
        if (position < 3) {
            holder.num_tv.setTextColor(Color.WHITE);
            holder.num_tv.setBackgroundColor(Color.parseColor("#FF1111"));
        } else {
            holder.num_tv.setTextColor(Color.DKGRAY);
            holder.num_tv.setBackgroundColor(Color.WHITE);
        }
        if (position + 1 == data.size()) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        ((ViewGroup) holder.num_tv.getParent()).setOnClickListener(new OnTextClick(position));
    }

    private class OnTextClick implements View.OnClickListener {

        private int position;

        public OnTextClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onTextClick(data.get(position));
            }
        }
    }

    public void setTextHolderClickListener(TextHolderClickListener listener) {
        this.listener = listener;
    }

    public interface TextHolderClickListener {
        void onTextClick(TextBean bean);
    }
}
