package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.bean.TextBean;
import com.wuzhanglao.niubi.holder.TextHolder;

/**
 * Created by wuming on 16/10/13.
 */

public class TextHolderAdatpter extends BaseAdapter<TextHolder, TextBean> {

    private TextHolderClickListener mListener;

    public TextHolderAdatpter(Context context) {
        super(context);
    }

    @Override
    protected TextHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new TextHolder(mInflater.inflate(R.layout.text_holder, parent, false));
    }

    @Override
    protected void onBindHolder(TextHolder holder, int position) {
        holder.num_tv.setText(position + 1 < 10 ? "0" + (position + 1) : "" + (position + 1));
        holder.title_tv.setText(getItem(position).getTitle());
        holder.subtitle_tv.setText(getItem(position).getSubTitle());
        if (position < 2) {
            holder.num_tv.setTextColor(Color.WHITE);
            holder.num_tv.setBackgroundColor(Color.parseColor("#FF1111"));
        } else {
            holder.num_tv.setTextColor(Color.DKGRAY);
            holder.num_tv.setBackgroundColor(Color.WHITE);
        }
        if (position + 1 == getItemCount()) {
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
            if (mListener != null) {
                mListener.onTextClick(getItem(position));
            }
        }
    }

    public void setTextHolderClickListener(TextHolderClickListener listener) {
        this.mListener = listener;
    }

    public interface TextHolderClickListener {
        void onTextClick(TextBean bean);
    }
}
