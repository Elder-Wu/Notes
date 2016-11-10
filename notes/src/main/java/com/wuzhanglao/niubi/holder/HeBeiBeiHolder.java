package com.wuzhanglao.niubi.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;

/**
 * Created by wuming on 2016/11/9.
 */

public class HeBeiBeiHolder extends RecyclerView.ViewHolder {

    public TextView text;
    public View text_line;

    public HeBeiBeiHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.desc_tv);
        text_line = itemView.findViewById(R.id.text_holder_line);
    }
}
