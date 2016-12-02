package com.wuzhanglao.niubi.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.fragment.AnimFragment;
import com.wuzhanglao.niubi.fragment.ApproveListFragment;
import com.wuzhanglao.niubi.fragment.BannerFragment;
import com.wuzhanglao.niubi.fragment.BezierFragment;
import com.wuzhanglao.niubi.fragment.BottomBarFragment;
import com.wuzhanglao.niubi.fragment.CountDownFragment;
import com.wuzhanglao.niubi.fragment.FloatViewFragment;
import com.wuzhanglao.niubi.fragment.FragmentHighlight;
import com.wuzhanglao.niubi.fragment.GuaGuaKaFragment;
import com.wuzhanglao.niubi.fragment.IosBottomDialogFragment;
import com.wuzhanglao.niubi.fragment.NetworkFragment;
import com.wuzhanglao.niubi.fragment.TBHeadlineFragment;
import com.wuzhanglao.niubi.utils.RxBus;
import com.wuzhanglao.niubi.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ToolbarActivity implements TextHolderAdatpter.TextHolderClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private TextHolderAdatpter adapter;
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    //toolbar相关
    private TextView toolbar_title_tv;
    private TextView toolbar_back_tv;
    private ImageView toolbar_profile_iv;

    @Override
    protected void doOnNext(Object o) {
        //接收到全局广播之后在这里处理的相关的业务逻辑
    }

    @Override
    protected int setContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        toolbar_title_tv = (TextView) findViewById(R.id.activity_main_toolbar_title_tv);
        toolbar_back_tv = (TextView) findViewById(R.id.activity_main_toolbar_back_tv);
        toolbar_profile_iv = (ImageView) findViewById(R.id.activity_main_toolbar_profile_iv);

        toolbar_title_tv.setText(getString(R.string.main_title));
        toolbar_back_tv.setVisibility(View.GONE);
        toolbar_profile_iv.setVisibility(View.GONE);
        toolbar_back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UIUtils.isDoubleClick()) {
                    return;
                }
                onBackPressed();
            }
        });
        toolbar_profile_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UIUtils.isDoubleClick()) {
                    return;
                }
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        hideBackButton();
    }

    protected void initData() {
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
        data.add("贝塞尔曲线");
        data.add("RxBus案例");
        data.add("TextView高亮显示");

        adapter = new TextHolderAdatpter(context, data);
        adapter.setTextHolderClickListener(this);
    }

    protected void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onTextClick(int position) {
        RxBus.send(new Random().nextInt());
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
                openFragment(new TBHeadlineFragment(), adapter.getData(position));
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
                openFragment(new FloatViewFragment(), adapter.getData(position));
                break;
            case "刮刮卡":
                openFragment(new GuaGuaKaFragment(), adapter.getData(position));
                break;
            case "广告栏无限轮播":
                openFragment(new BannerFragment(), adapter.getData(position));
                break;
            case "贝塞尔曲线":
                openFragment(new BezierFragment(), adapter.getData(position));
                break;
            case "RxBus案例":
                startActivity(new Intent(context, TestRxActivity1.class));
                break;
            case "TextView高亮显示":
                openFragment(new FragmentHighlight(), "TextView高亮显示");
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
        hideBackButton();
    }

    public void showBackButton(String fragmentName) {
        toolbar_back_tv.setVisibility(View.VISIBLE);
        toolbar_profile_iv.setVisibility(View.GONE);
        toolbar_title_tv.setText(fragmentName);
    }

    public void hideBackButton() {
        toolbar_back_tv.setVisibility(View.GONE);
        toolbar_profile_iv.setVisibility(View.VISIBLE);
        toolbar_title_tv.setText(getString(R.string.main_title));
    }
}
