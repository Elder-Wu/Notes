package com.example.customview.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.adapter.TextHolderAdatpter;
import com.example.customview.fragment.BottomDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends ActionBarActivity implements TextHolderAdatpter.TextHolderClickListener {

    private final String TAG = TestActivity.class.getSimpleName();
    private TextHolderAdatpter adatpter;

    //ActionBar
    private TextView actionBarTitle;
    private FragmentManager fragmentManager;

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initActionBar() {
        View actionBar = findViewById(R.id.activity_main_actionbar);
        actionBarTitle = (TextView) actionBar.findViewById(R.id.actionbar_main_title);
    }

    protected void initView() {
        initData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adatpter);

        fragmentManager = getSupportFragmentManager();
    }

    private void initData() {
        List<String> data = new ArrayList();
        data.add("仿ios底部弹出对话框");
        adatpter = new TextHolderAdatpter(context, data);
        adatpter.setTextHolderClickListener(this);
    }

    @Override
    public void onTextClick(int position) {
        setTitle(adatpter.getData(position));
        switch (adatpter.getData(position)) {
            case "仿ios底部弹出对话框":
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new BottomDialogFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
                break;
        }
    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }

}
