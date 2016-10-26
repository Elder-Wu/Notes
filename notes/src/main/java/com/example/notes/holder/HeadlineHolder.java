package com.example.notes.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.notes.R;

/**
 * Created by wuming on 16/10/16.
 */

public class HeadlineHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView content;

    public HeadlineHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.headline_title_tv);
        content = (TextView) itemView.findViewById(R.id.headline_content_tv);
    }
}
