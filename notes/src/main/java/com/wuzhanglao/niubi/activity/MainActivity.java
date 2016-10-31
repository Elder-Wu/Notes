package com.wuzhanglao.niubi.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.notes.R;
import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.fragment.AnimFragment;
import com.wuzhanglao.niubi.fragment.ApproveListFragment;
import com.wuzhanglao.niubi.fragment.BottomBarFragment;
import com.wuzhanglao.niubi.fragment.CountDownFragment;
import com.wuzhanglao.niubi.fragment.DragFragment;
import com.wuzhanglao.niubi.fragment.IosBottomDialogFragment;
import com.wuzhanglao.niubi.fragment.NetworkFragment;
import com.wuzhanglao.niubi.fragment.TaobaoHeadlineFragment;
import com.wuzhanglao.niubi.utils.UIUtils;

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
        actionBarTitle.setText(getString(R.string.main_title));
        actionBarBack = (TextView) actionBar.findViewById(R.id.actionbar_main_back_tv);
        actionBarBack.setOnClickListener(this);
        hideBackButton();
    }

    protected void initView() {
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        TextView introduce = (TextView) findViewById(R.id.introduce_tv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            introduce.setText(Html.fromHtml(getString(R.string.introduce), 0));
        } else {
            introduce.setText(Html.fromHtml(getString(R.string.introduce)));
        }
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
        data.add("点赞列表");
        data.add("可以拖动的布局");
        adatpter = new TextHolderAdatpter(context, data);
        adatpter.setTextHolderClickListener(this);
    }

    @Override
    public void onTextClick(int position) {
        if (UIUtils.isDoubleClick()) {
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
                openFragment(new NetworkFragment());
                break;
            case "点赞列表":
                openFragment(new ApproveListFragment());
                break;
            case "可以拖动的布局":
                openFragment(new DragFragment());
                break;
        }
    }

    private void openFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
        showBackButton();
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
