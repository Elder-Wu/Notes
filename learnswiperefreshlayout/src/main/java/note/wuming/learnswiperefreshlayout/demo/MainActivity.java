package note.wuming.learnswiperefreshlayout.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import note.wuming.learnswiperefreshlayout.R;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView recycler_view;
    private DataAdapter adapter;
    private View header_view;
    private View footer_view;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
         initView();
    }

    private void initView() {
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recycler_view = (RecyclerView) findViewById(R.id.recycle_view);
        header_view = LayoutInflater.from(this).inflate(R.layout.recycle_view_header, null);
        footer_view = LayoutInflater.from(this).inflate(R.layout.recycle_view_footer, null);
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        data.add("E");
        adapter = new DataAdapter(this, data);
        adapter.addHeaderView(header_view);
        adapter.addFooterView(footer_view);


        swipe_refresh_layout.setOnRefreshListener(this);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(false);
            }
        }, 1000);
    }
}
