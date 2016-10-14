package com.example.customview.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.customview.R;
import com.example.customview.adapter.TextHolderAdatpter;
import com.example.customview.utils.ToastUtils;
import com.example.customview.widget.BottomDialog;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements TextHolderAdatpter.TextHolderClickListener {

    private final String TAG = TestActivity.class.getSimpleName();
    private TextHolderAdatpter adatpter;

    private Context context;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
    }

    private void initView() {
        initData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adatpter);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    private void initData() {
        List<String> data = new ArrayList();
        data.add("Java");
        data.add("Python");
        data.add("Html");
        data.add("仿ios底部弹出对话框");
        adatpter = new TextHolderAdatpter(context, data);
        adatpter.setTextHolderClickListener(this);
    }

    @Override
    public void onTextClick(int position) {
        switch (adatpter.getData(position)) {
            case "仿ios底部弹出对话框":
                // fragmentTransaction.replace(R.id.main_container, new BottomDialogFragment()).commitAllowingStateLoss();
                BottomDialog.Builder builder = new BottomDialog.Builder(context);
                builder.setTitle("我的自定义标题");
                builder.setTitleSize(30);
                builder.setTitleColor(Color.RED);
                builder.setOptionTextSize(25);
                builder.setOptionTextColor(Color.DKGRAY);
                builder.addOption("操作1", new BottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show(context, "操作1");
                    }
                });
                builder.addOption("操作2", new BottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show(context, "操作2");
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                break;
        }
    }

}
