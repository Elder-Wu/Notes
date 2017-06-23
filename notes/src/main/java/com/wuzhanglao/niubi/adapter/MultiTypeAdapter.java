package com.wuzhanglao.niubi.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ming.wu@shanbay.com on 2017/4/27.
 */
public abstract class MultiTypeAdapter<T extends MultiTypeAdapter.Callback> extends RecyclerView.Adapter<MultiTypeAdapter.ViewHolder> {

	private T mCallback;
	private List<Data> mDataList = new ArrayList<>();
	private Map<Integer, Manager> mManagerMap = new HashMap<>();

	private Activity mActivity;
	private RequestManager mRequestManager;

	public MultiTypeAdapter(Activity activity) {
		mActivity = activity;
		mRequestManager = Glide.with(activity);
	}

	public void setDataList(List<Data> dataList) {
		mDataList.clear();
		mDataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public Data getItem(int position) {
		if (position < 0 || position >= mDataList.size()) {
			return null;
		}
		return mDataList.get(position);
	}

	public void register(Class<? extends Manager> clazz) {
		try {
			Manager manager = clazz.newInstance();
			manager.setCallback(mCallback);
			manager.setRequestManager(mRequestManager);
			manager.setActivity(mActivity);
			manager.setInflater(mActivity.getLayoutInflater());
			mManagerMap.put(manager.bindData().getName().hashCode(), manager);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void registerItemType(int type);

	@Override
	public int getItemViewType(int position) {
		return mDataList.get(position).getClass().getName().hashCode();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Manager manager = getManager(viewType);
		if (manager == null) {
			return createNullViewHolder(parent.getContext());
		}
		return manager.createViewHolder(parent);
	}

	private NullViewHolder createNullViewHolder(Context context) {
		View view = new View(context);
		return new NullViewHolder(view);
	}

	private Manager getManager(int type) {
		return mManagerMap.get(type);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (holder instanceof NullViewHolder) {
			return;
		}

		int type = getItemViewType(position);
		Manager manager = getManager(type);
		if (manager == null || getItem(position) == null) {
			return;
		}
		manager.bindViewHolder(holder, getItem(position), position);
	}

	@Override
	public int getItemCount() {
		return mDataList.size();
	}

	public void setCallback(T callback) {
		mCallback = callback;
		for (Manager manager : mManagerMap.values()) {
			manager.setCallback(mCallback);
		}
	}

	public void reset() {
		mDataList.clear();
		Set<Map.Entry<Integer, Manager>> set = mManagerMap.entrySet();
		for (Map.Entry<Integer, Manager> entry : set) {
			entry.getValue().reset();
		}
	}

	public static class EmptyViewHolder extends ViewHolder {
		public EmptyViewHolder(View itemView) {
			super(itemView);
		}
	}

	private static class NullViewHolder extends EmptyViewHolder {
		private NullViewHolder(View itemView) {
			super(itemView);
		}
	}

	public interface Callback {

	}

	public abstract static class Manager<D extends Data, V extends ViewHolder, T extends Callback> {

		private Context mContext;
		private T mCallback;
		private Activity mActivity;
		private LayoutInflater mInflater;
		private RequestManager mRequestManager;

		public Manager() {
		}

		private void setCallback(T callback) {
			mCallback = callback;
		}

		private void setActivity(Activity activity) {
			mActivity = activity;
		}

		private void setInflater(LayoutInflater inflater) {
			mInflater = inflater;
		}

		public void setRequestManager(RequestManager requestManager) {
			mRequestManager = requestManager;
		}

		public final V createViewHolder(@NonNull ViewGroup parent) {
			mContext = parent.getContext();
			mInflater = LayoutInflater.from(mContext);
			return onCreateViewHolder(parent);
		}

		public final void bindViewHolder(V holder, D data, int position) {
			holder.setData(data);
			holder.render(data);
		}

		/**
		 * 将数据模型和View视图绑定起来
		 */
		@NonNull
		public abstract Class<D> bindData();

		public abstract V onCreateViewHolder(@NonNull ViewGroup parent);

		public Activity getActivity() {
			return mActivity;
		}

		public T getCallback() {
			return mCallback;
		}

		public LayoutInflater getLayoutInflater() {
			return mInflater;
		}

		public RequestManager getRequestManager() {
			return mRequestManager;
		}

		public void reset() {
		}
	}

	public abstract static class ViewHolder<T extends Data> extends RecyclerView.ViewHolder {

		private T data;

		public ViewHolder(final View itemView) {
			super(itemView);
		}

		protected void render(T data) {

		}

		protected final void setData(T data) {
			this.data = data;
		}

		public T getData() {
			return data;
		}
	}

	public static class Data {

	}
}