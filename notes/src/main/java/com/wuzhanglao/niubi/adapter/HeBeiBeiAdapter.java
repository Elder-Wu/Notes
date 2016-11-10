package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.holder.HeBeiBeiHolder;

import java.util.List;

/**
 * Created by wuming on 2016/11/9.
 */

public class HeBeiBeiAdapter extends BaseAdapter<HeBeiBeiHolder, String> {

    public HeBeiBeiAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected HeBeiBeiHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new HeBeiBeiHolder(inflater.inflate(R.layout.text_holder, parent, false));
    }

    @Override
    protected void onBindHolder(HeBeiBeiHolder holder, int position_in_data) {
        holder.text.setText(getData(position_in_data).toString());
        if (position_in_data == getItemCount() - 1) {
            holder.text_line.setVisibility(View.GONE);
        } else {
            holder.text_line.setVisibility(View.VISIBLE);
        }
    }
}
