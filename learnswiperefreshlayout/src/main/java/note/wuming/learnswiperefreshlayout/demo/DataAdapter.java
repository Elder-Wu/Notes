package note.wuming.learnswiperefreshlayout.demo;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import note.wuming.learnswiperefreshlayout.BaseAdapter;
import note.wuming.learnswiperefreshlayout.R;

/**
 * Created on 2016/9/29.
 */

public class DataAdapter extends BaseAdapter<DataHolder, String> {

    public DataAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected DataHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new DataHolder(layoutInflater.inflate(R.layout.recycler_view_item, null));
    }

    @Override
    protected void onBindHolder(DataHolder holder, int position_in_data) {
        holder.text.setText(data.get(position_in_data).toString());
        holder.text.setOnClickListener(new MyOnClickListener(position_in_data));
        holder.text.setOnLongClickListener(new MyOnLongClickListener(position_in_data));
    }

    private class MyOnLongClickListener implements View.OnLongClickListener {

        private int position_in_data;

        public MyOnLongClickListener(int position_in_data) {
            this.position_in_data = position_in_data;
        }

        @Override
        public boolean onLongClick(View v) {
            removeData(position_in_data);
            return true;
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        private int position_in_data;

        public MyOnClickListener(int position_in_data) {
            this.position_in_data = position_in_data;
        }

        @Override
        public void onClick(View v) {
            addData(data.get(position_in_data));
        }
    }
}
