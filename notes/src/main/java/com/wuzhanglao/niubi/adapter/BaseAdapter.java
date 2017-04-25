package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.holder.EmptyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/9/28.
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter {

    private static final int HEADER = 0x1001;
    private static final int FOOTER = 0x1002;

    public LayoutInflater mInflater;
    public List<T> mDataList = new ArrayList<>();    //adapter中的数据是存放在List里面的

    private View mHeaderView = null;
    private View mFooterView = null;

    public BaseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setDataList(List<T> dataList) {
        if (dataList != null) {
            mDataList = dataList;
        }
    }

    public T getItem(int pos) {
        if (pos < 0 || pos >= mDataList.size()) {
            return null;
        }
        return mDataList.get(pos);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new EmptyViewHolder(mHeaderView);
        }
        if (viewType == FOOTER) {
            return new EmptyViewHolder(mFooterView);
        }
        //如果既不是头部也不是尾部，就让用户去判断到底该实例化哪个ViewHolder(界面)
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type != HEADER && type != FOOTER) {
            int position_in_data = position - (hasHeader() ? 1 : 0);
            T data = getItem(position_in_data);
            if(data!=null){
                onBindHolder((VH) holder, position_in_data);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderView != null) {
            return HEADER;
        }
        if (position == getItemCount() - 1 && mFooterView != null) {
            return FOOTER;
        }
        //如果既不是头部也不是尾部，那就让子类去判断，该position的view到底是什么类型
        return getViewType(position);
    }

    protected int getViewType(int position) {
        return 0;
    }

    protected abstract VH onCreateHolder(ViewGroup parent, int viewType);

    protected abstract void onBindHolder(VH holder, int position_in_data);

    public void addHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    public void removeHeaderView() {
        this.mHeaderView = null;
    }

    public void addFooterView(View footerView) {
        this.mFooterView = footerView;
    }

    public void removeFooterView() {
        this.mFooterView = null;
    }

    protected boolean hasHeader() {
        return mHeaderView != null;
    }

    protected boolean hasFooter() {
        return mFooterView != null;
    }
}
