package com.wuzhanglao.niubi.adapter;

import android.app.Activity;

/**
 * Created by ming.wu@shanbay.com on 2017/6/23.
 */

public class SubMultiTypeAdapter extends MultiTypeAdapter<SubMultiTypeAdapter.Callback> {

	public static final int Type_ITEM_A = 1;

	public SubMultiTypeAdapter(Activity activity) {
		super(activity);
	}

	/**
	 * register sub item type here
	 *
	 * @param type
	 */
	@Override
	protected void registerItemType(int type) {

	}

	public interface Callback extends MultiTypeAdapter.Callback {
		void onAction();
	}
}
