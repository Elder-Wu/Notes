package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.holder.TextHolder;

import java.util.List;

/**
 * Created by wuming on 16/10/13.
 */

public class TextHolderAdatpter extends BaseAdapter<TextHolder, String> {

    private static final String TAG = TextHolderAdatpter.class.getSimpleName();
    private TextHolderClickListener listener;

    public TextHolderAdatpter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected TextHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new TextHolder(inflater.inflate(R.layout.text_holder, parent, false));
    }

    @Override
    protected void onBindHolder(TextHolder holder, int position) {
        holder.desc.setText(data.get(position));
        holder.desc.setOnClickListener(new OnTextClick(position));
        if (position + 1 == data.size()) {
            holder.line.setVisibility(View.GONE);
        }
    }

    private class OnTextClick implements View.OnClickListener {

        private int position;

        public OnTextClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onTextClick(position);
            }
        }
    }

    public void setTextHolderClickListener(TextHolderClickListener listener) {
        this.listener = listener;
    }

    public interface TextHolderClickListener {
        void onTextClick(int position);
    }

}
