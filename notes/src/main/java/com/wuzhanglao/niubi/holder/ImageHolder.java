package com.wuzhanglao.niubi.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;


/**
 * Created by wuming on 2016/12/3.
 */

public class ImageHolder extends RecyclerView.ViewHolder {

    public TextView title_tv;
    public TextView subtitle_tv;
    public ImageView image_iv;
    public View line;

    public ImageHolder(View itemView) {
        super(itemView);
        title_tv = (TextView) itemView.findViewById(R.id.image_hoder_title_tv);
        subtitle_tv = (TextView) itemView.findViewById(R.id.image_hoder_subtitle_tv);
        image_iv = (ImageView) itemView.findViewById(R.id.image_hoder_image_iv);
        line = itemView.findViewById(R.id.image_holder_line);
    }
}
