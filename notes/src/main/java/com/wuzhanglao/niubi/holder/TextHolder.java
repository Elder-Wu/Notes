package com.wuzhanglao.niubi.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;

/**
 * Created by wuming on 16/10/13.
 */

public class TextHolder extends RecyclerView.ViewHolder {

    public TextView title_tv;
    public TextView subtitle_tv;
    public TextView num_tv;
    public View line;

    public TextHolder(View itemView) {
        super(itemView);
        num_tv = (TextView) itemView.findViewById(R.id.text_holder_num_tv);
        title_tv = (TextView) itemView.findViewById(R.id.text_holder_title_tv);
        subtitle_tv = (TextView) itemView.findViewById(R.id.text_holder_subtitle_tv);
        line = itemView.findViewById(R.id.text_holder_line);
    }
}
