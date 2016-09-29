package note.wuming.learnswiperefreshlayout.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import note.wuming.learnswiperefreshlayout.R;

/**
 * Created on 2016/9/29.
 */

public class DataHolder extends RecyclerView.ViewHolder {

    TextView text;

    public DataHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }
}
