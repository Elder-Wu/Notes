package note.wuming.learnswiperefreshlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import note.wuming.learnswiperefreshlayout.demo.EmptyViewHolder;

/**
 * Created on 2016/9/28.
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T extends Object> extends RecyclerView.Adapter {

    private static final String TAG = "BaseAdapter";
    private static final int HEADER = 0x1001;
    private static final int FOOTER = 0x1002;

    public List<T> data;    //adapter中的数据是存放在List里面的
    public Context context;
    public LayoutInflater layoutInflater;

    private View headerView = null;
    private View footerView = null;

    public BaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
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
            onBindHolder((VH) holder, position - (hasHeader() ? 1 : 0));
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

    /**
     * 删除指定位置的数据
     *
     * @param position_in_data data中的position
     */
    public void removeData(int position_in_data) {
        if (position_in_data < 0 || position_in_data >= data.size()) {
            Log.e(TAG, "数组越界 data.size()=" + data.size() + " position=" + position_in_data);
        } else {
            Log.d(TAG, "remove_position_in_data:" + position_in_data);
            data.remove(position_in_data);
            notifyDataSetChanged();
        }
    }

    /**
     * 在data的尾部添加一条数据
     *
     * @param value
     */
    public void addData(T value) {
        data.add(value);
        int position_in_list = data.size() + (hasHeader() ? 0 : -1);
        Log.d(TAG, "add_position_in_list:" + position_in_list);
        notifyItemInserted(position_in_list);
    }

//    //更改某个位置的数据
//    public void changeData(int position,) {
//        data.set(position);
//        notifyItemChanged(position);
//    }

    protected int getViewType(int position) {
        return 0;
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
