package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.holder.ImageHolder;
import com.wuzhanglao.niubi.utils.UIUtils;

import java.util.List;

/**
 * Created by wuming on 2016/12/3.
 */

public class ImageHolderAdapter extends BaseAdapter<ImageHolder, String> {

    public ImageHolderAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(inflater.inflate(R.layout.image_holder, parent, false));
    }

    @Override
    protected void onBindHolder(ImageHolder holder, int position_in_data) {
        final String url = data.get(position_in_data);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_img)
                .into(holder.image_iv);
        if (position_in_data == data.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
    }
}
