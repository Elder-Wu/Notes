package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.holder.EmptyViewHolder;

import java.util.List;

/**
 * Created on 2016/9/28.
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T extends Object> extends RecyclerView.Adapter {

    private static final String TAG = BaseAdapter.class.getSimpleName();
    private static final int HEADER = 0x1001;
    private static final int FOOTER = 0x1002;

    public List<T> data;    //adapter中的数据是存放在List里面的
    public Context context;
    public LayoutInflater inflater;

    private View headerView = null;
    private View footerView = null;

    public BaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new EmptyViewHolder(headerView);
        }
        if (viewType == FOOTER) {
            return new EmptyViewHolder(footerView);
        }
        //如果既不是头部也不是尾部，就让用户去判断到底该实例化哪个ViewHolder(界面)
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type != HEADER && type != FOOTER) {
            int position_in_data = position - (hasHeader() ? 1 : 0);
            onBindHolder((VH) holder, position_in_data);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headerView != null) {
            return HEADER;
        }
        if (position == getItemCount() - 1 && footerView != null) {
            return FOOTER;
        }
        //如果既不是头部也不是尾部，那就让子类去判断，该position的view到底是什么类型
        return getViewType(position);
    }

    @Override
    public int getItemCount() {
        return data.size() + (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0);
    }

    protected int getViewType(int position) {
        return 0;
    }

    public T getData(int position_in_data) {
        return data.get(position_in_data);
    }

    public List<T> getData() {
        return data;
    }

    protected abstract VH onCreateHolder(ViewGroup parent, int viewType);

    protected abstract void onBindHolder(VH holder, int position_in_data);

    public void addHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public void removeHeaderView() {
        this.headerView = null;
    }

    public void addFooterView(View footerView) {
        this.footerView = footerView;
    }

    public void removeFooterView() {
        this.footerView = null;
    }

    protected boolean hasHeader() {
        return headerView == null ? false : true;
    }

    protected boolean hasFooter() {
        return footerView == null ? false : true;
    }
}
