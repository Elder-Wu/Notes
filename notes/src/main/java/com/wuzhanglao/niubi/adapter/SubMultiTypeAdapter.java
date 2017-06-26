package com.wuzhanglao.niubi.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

/**
 * Created by ming.wu@shanbay.com on 2017/6/23.
 */

public class SubMultiTypeAdapter extends MultiTypeAdapter<SubMultiTypeAdapter.Callback> {

	public static final int Type_ITEM_A = 0x011;
	public static final int Type_ITEM_B = 0x022;
	public static final int Type_ITEM_C = 0x033;

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

	private class MultiTypeSubManager extends MultiTypeAdapter.Manager {

		@NonNull
		@Override
		public Class bindData() {
			return getClass();
		}

		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {

			return null;
		}
	}
}
