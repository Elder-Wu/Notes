package com.wuzhanglao.niubi.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.bean.TextBean;
import com.wuzhanglao.niubi.fragment.AnimFragment;
import com.wuzhanglao.niubi.fragment.ApproveListFragment;
import com.wuzhanglao.niubi.fragment.BannerFragment;
import com.wuzhanglao.niubi.fragment.BezierFragment;
import com.wuzhanglao.niubi.fragment.BottomBarFragment;
import com.wuzhanglao.niubi.fragment.CountDownFragment;
import com.wuzhanglao.niubi.fragment.FloatViewFragment;
import com.wuzhanglao.niubi.fragment.GuaGuaKaFragment;
import com.wuzhanglao.niubi.fragment.HighlightFragment;
import com.wuzhanglao.niubi.fragment.IosBottomDialogFragment;
import com.wuzhanglao.niubi.fragment.LoadImageFragment;
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
        List<TextBean> data = new ArrayList();
        data.add(new TextBean("TextView高亮显示", "点击英文单词可以高亮显示，并且显示翻译结果"));
        data.add(new TextBean("加载网络图片", "使用Glide对一组url进行加载，下拉刷新，下拉加载更多"));
        data.add(new TextBean("仿ios底部弹出对话框", "可以随意设定标题和选项按钮"));
        data.add(new TextBean("Activity动画特效", "待完善..."));
        data.add(new TextBean("京东头条控件", "模仿京东头条，上下无限滚动"));
        data.add(new TextBean("显示未读消息数控件", "类似QQ、微信底部tab标签，可以显示未读消息的数量"));
        data.add(new TextBean("广告倒计时控件", "修复了Timer类会内存溢出的问题"));
        data.add(new TextBean("网络请求", "Retrofit+RxJava+okhttp实现异步的网络请求"));
        data.add(new TextBean("点赞列表", "从左到右排列一组头像"));
        data.add(new TextBean("可以拖动的布局", "类似iPhone AssistiveTouch效果"));
        data.add(new TextBean("刮刮卡", "自定义view实现"));
        data.add(new TextBean("广告栏无限轮播", "加入了动画效果"));
        data.add(new TextBean("贝塞尔曲线", "TypedValue和nterpolator的高级使用"));
        data.add(new TextBean("RxBus案例", "轻松解决组件与组件之间的消息传递"));

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
    public void onTextClick(TextBean bean) {
        RxBus.send(new Random().nextInt());
        if (UIUtils.isDoubleClick()) {
            return;
        }
        switch (bean.getTitle()) {
            case "显示未读消息数控件":
                openFragment(new BottomBarFragment(), bean.getTitle());
                break;
            case "仿ios底部弹出对话框":
                openFragment(new IosBottomDialogFragment(), bean.getTitle());
                break;
            case "Activity动画特效":
                openFragment(new AnimFragment(), bean.getTitle());
                break;
            case "京东头条控件":
                openFragment(new TBHeadlineFragment(), bean.getTitle());
                break;
            case "广告倒计时控件":
                openFragment(new CountDownFragment(), bean.getTitle());
                break;
            case "网络请求":
                openFragment(new NetworkFragment(), bean.getTitle());
                break;
            case "点赞列表":
                openFragment(new ApproveListFragment(), bean.getTitle());
                break;
            case "可以拖动的布局":
                openFragment(new FloatViewFragment(), bean.getTitle());
                break;
            case "刮刮卡":
                openFragment(new GuaGuaKaFragment(), bean.getTitle());
                break;
            case "广告栏无限轮播":
                openFragment(new BannerFragment(), bean.getTitle());
                break;
            case "贝塞尔曲线":
                openFragment(new BezierFragment(), bean.getTitle());
                break;
            case "RxBus案例":
                startActivity(new Intent(context, TestRxActivity1.class));
                break;
            case "TextView高亮显示":
                openFragment(new HighlightFragment(), bean.getTitle());
                break;
            case "加载网络图片":
                openFragment(new LoadImageFragment(), bean.getTitle());
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
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void hideBackButton() {
        toolbar_back_tv.setVisibility(View.GONE);
        toolbar_profile_iv.setVisibility(View.VISIBLE);
        toolbar_title_tv.setText(getString(R.string.main_title));
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        }
    }
}
