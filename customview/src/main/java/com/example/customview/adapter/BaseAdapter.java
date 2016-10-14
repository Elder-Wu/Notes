package com.example.customview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wuming on 16/10/13.
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T extends Object> extends RecyclerView.Adapter {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> data;

    public BaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindHolder((VH) holder, position);
    }

    protected abstract VH onCreateHolder(ViewGroup parent, int viewType);

    protected abstract void onBindHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<T> getData() {
        return data;
    }

    public T getData(int position){
        return data.get(position);
    }
}
