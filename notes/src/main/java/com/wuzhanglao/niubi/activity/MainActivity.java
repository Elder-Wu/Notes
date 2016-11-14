package com.wuzhanglao.niubi.activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.fragment.AnimFragment;
import com.wuzhanglao.niubi.fragment.ApproveListFragment;
import com.wuzhanglao.niubi.fragment.BottomBarFragment;
import com.wuzhanglao.niubi.fragment.CountDownFragment;
import com.wuzhanglao.niubi.fragment.DragFragment;
import com.wuzhanglao.niubi.fragment.DrawBoardFragment;
import com.wuzhanglao.niubi.fragment.FragmentViewPager;
import com.wuzhanglao.niubi.fragment.IosBottomDialogFragment;
import com.wuzhanglao.niubi.fragment.NetworkFragment;
import com.wuzhanglao.niubi.fragment.TaobaoHeadlineFragment;
import com.wuzhanglao.niubi.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ToolbarActivity implements TextHolderAdatpter.TextHolderClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private TextHolderAdatpter adapter;

    private FragmentManager fragmentManager;

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    protected void initView() {
        initData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        TextView introduce = (TextView) findViewById(R.id.introduce_tv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            introduce.setText(Html.fromHtml(getString(R.string.introduce), 0));
        } else {
            introduce.setText(Html.fromHtml(getString(R.string.introduce)));
        }

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
        data.add("刮刮卡");
        data.add("广告栏无限轮播");

        adapter = new TextHolderAdatpter(context, data);
        adapter.setTextHolderClickListener(this);
    }

    @Override
    public void onTextClick(int position) {
        if (UIUtils.isDoubleClick()) {
            return;
        }
        switch (adapter.getData(position)) {
            case "显示未读消息数控件":
                openFragment(new BottomBarFragment(), adapter.getData(position));
                break;
            case "仿ios底部弹出对话框":
                openFragment(new IosBottomDialogFragment(), adapter.getData(position));
                break;
            case "Activity动画特效":
                openFragment(new AnimFragment(), adapter.getData(position));
                break;
            case "淘宝头条控件":
                openFragment(new TaobaoHeadlineFragment(), adapter.getData(position));
                break;
            case "广告倒计时控件":
                openFragment(new CountDownFragment(), adapter.getData(position));
                break;
            case "网络请求":
                openFragment(new NetworkFragment(), adapter.getData(position));
                break;
            case "点赞列表":
                openFragment(new ApproveListFragment(), adapter.getData(position));
                break;
            case "可以拖动的布局":
                openFragment(new DragFragment(), adapter.getData(position));
                break;
            case "刮刮卡":
                openFragment(new DrawBoardFragment(), adapter.getData(position));
                break;
            case "广告栏无限轮播":
                openFragment(new FragmentViewPager(), adapter.getData(position));
                break;
        }
    }

    private void openFragment(Fragment fragment, String fragmentName) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
        showBackButton(fragmentName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideBackButton(R.string.main_title);
    }

    public void showBackButton(String fragmentName) {
        setToolbarBackVisible(true);
        setToolbarTitle(fragmentName);
    }

    public void hideBackButton(int stringRes) {
        setTitle(getString(R.string.main_title));
        setToolbarBackVisible(false);
    }

    @Override
    public void onClick(View v) {

    }
}
