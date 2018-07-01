package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.holder.ImageHolder;

/*
 * Created by wuming on 2016/12/3.
 */

public class ImageHolderAdapter extends BaseAdapter<ImageHolder, String> {

    public ImageHolderAdapter(Context context) {
        super(context);
    }

    @Override
    protected ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(mInflater.inflate(R.layout.image_holder, parent, false));
    }

    @Override
    protected void onBindHolder(ImageHolder holder, int position_in_data) {
        final String url = getItem(position_in_data);
        if (position_in_data == getItemCount() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
    }
}
