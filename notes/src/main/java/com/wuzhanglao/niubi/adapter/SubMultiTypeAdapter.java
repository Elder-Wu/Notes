package com.wuzhanglao.niubi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by ming.wu@shanbay.com on 2017/6/23.
 */

public class SubMultiTypeAdapter extends MultiTypeAdapter {

	public SubMultiTypeAdapter(Activity activity) {
		super(activity);
	}

	@Override
	protected void registerItemType(int type) {

	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		super.onBindViewHolder(holder, position);
	}

	public interface Callback {
		void onAction();
	}
}
