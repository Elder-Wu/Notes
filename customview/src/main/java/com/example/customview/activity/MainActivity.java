package com.example.customview.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.adapter.TextHolderAdatpter;
import com.example.customview.fragment.AnimFragment;
import com.example.customview.fragment.BottomBarFragment;
import com.example.customview.fragment.CountDownFragment;
import com.example.customview.fragment.IosBottomDialogFragment;
import com.example.customview.fragment.NetworkFragment;
import com.example.customview.fragment.TaobaoHeadlineFragment;
import com.example.customview.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements TextHolderAdatpter.TextHolderClickListener, View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private TextHolderAdatpter adatpter;

    //ActionBar
    private TextView actionBarTitle;
    private TextView actionBarBack;
    private FragmentManager fragmentManager;

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initActionBar() {
        View actionBar = findViewById(R.id.activity_main_actionbar);
        actionBarTitle = (TextView) actionBar.findViewById(R.id.actionbar_main_title);
        actionBarTitle.setText("软件主界面");
        actionBarBack = (TextView) actionBar.findViewById(R.id.actionbar_main_back_tv);
        actionBarBack.setOnClickListener(this);
        hideBackButton();
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
        data.add("Activity动画特效");
        data.add("淘宝头条控件");
        data.add("显示未读消息数控件");
        data.add("广告倒计时控件");
        data.add("网络请求");
        adatpter = new TextHolderAdatpter(context, data);
        adatpter.setTextHolderClickListener(this);
    }

    @Override
    public void onTextClick(int position) {
        if(UIUtils.isDoubleClick()){
            return;
        }
        Intent intent;
        setTitle(adatpter.getData(position));
        switch (adatpter.getData(position)) {
            case "显示未读消息数控件":
                openFragment(new BottomBarFragment());
                break;
            case "仿ios底部弹出对话框":
                openFragment(new IosBottomDialogFragment());
                break;
            case "Activity动画特效":
                openFragment(new AnimFragment());
                break;
            case "淘宝头条控件":
                openFragment(new TaobaoHeadlineFragment());
                break;
            case "广告倒计时控件":
                openFragment(new CountDownFragment());
                break;
            case "网络请求":
                Log.d(TAG,"openFragment start");
                openFragment(new NetworkFragment());
                break;
        }
    }

    private void openFragment(Fragment fragment) {
        showBackButton();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }

    public void showBackButton() {
        actionBarBack.setVisibility(View.VISIBLE);
    }

    public void hideBackButton() {
        actionBarBack.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_main_back_tv:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle(getString(R.string.main_title));
        hideBackButton();
    }
}
