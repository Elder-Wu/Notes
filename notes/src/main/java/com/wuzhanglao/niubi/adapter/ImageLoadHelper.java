package com.wuzhanglao.niubi.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wuzhanglao.niubi.utils.UIUtils;

/**
 * Created by wuming on 2016/12/3.
 */

public class ImageLoadHelper extends RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageHolderAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Handler handler = new Handler();
    private boolean isLoading = false;

    public ImageLoadHelper(Context context) {
        this.context = context;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public void setAdapter(ImageHolderAdapter adapter) {
        this.adapter = adapter;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void init() {
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (layoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
            if (!isLoading) {
                isLoading = true;
                UIUtils.showToast("开始加载更多...");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoading = false;
                    }
                }, 10000);
            }
        }
    }
}