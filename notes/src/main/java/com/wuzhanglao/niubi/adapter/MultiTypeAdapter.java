package com.wuzhanglao.niubi.adapter;
/*
 * Created by ming.wu@shanbay.com on 2017/3/26.
 */

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MultiTypeAdapter extends RecyclerView.Adapter {

	private List<ItemData> mItemDataList = new ArrayList<>();
	private List<ItemView> mItemViewList = new ArrayList<>();

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//		return mItemViewList.get(viewType);
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 0;
	}
}
