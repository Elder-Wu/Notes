package com.wuzhanglao.niubi.home;

import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.base.mvp.BaseMvpPresenter;
import com.wuzhanglao.niubi.bean.TextBean;
import com.wuzhanglao.niubi.fragment.ApproveListFragment;
import com.wuzhanglao.niubi.fragment.BannerFragment;
import com.wuzhanglao.niubi.fragment.BezierFragment;
import com.wuzhanglao.niubi.fragment.BottomBarFragment;
import com.wuzhanglao.niubi.fragment.CountDownFragment;
import com.wuzhanglao.niubi.fragment.FloatViewFragment;
import com.wuzhanglao.niubi.fragment.GuaGuaKaFragment;
import com.wuzhanglao.niubi.fragment.LoadImageFragment;
import com.wuzhanglao.niubi.fragment.NetworkFragment;
import com.wuzhanglao.niubi.fragment.TBHeadlineFragment;
import com.wuzhanglao.niubi.fragment.TestMeasureLayoutFragment;
import com.wuzhanglao.niubi.fragment.TimerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeMvpPresenter extends BaseMvpPresenter<HomeMvpView,HomeMvpModel> implements HomeMvpView.Callback {

    private List<TextBean> mViewList = new ArrayList<>();

    @Override
    public void onAttach() {
        getView().setCallback(this);
    }

    private void onItemClick(int position) {
        String title = mViewList.get(position).getTitle();
        switch (title) {
            case "DemoActivity":
                break;
            case "显示未读消息数控件":
                getView().openFragment(new BottomBarFragment(), title);
                break;
            case "京东头条控件":
                getView().openFragment(new TBHeadlineFragment(), title);
                break;
            case "广告倒计时控件":
                getView().openFragment(new CountDownFragment(), title);
                break;
            case "网络请求":
                getView().openFragment(new NetworkFragment(), title);
                break;
            case "点赞列表":
                getView().openFragment(new ApproveListFragment(), title);
                break;
            case "可以拖动的布局":
                getView().openFragment(new FloatViewFragment(), title);
                break;
            case "刮刮卡":
                getView().openFragment(new GuaGuaKaFragment(), title);
                break;
            case "广告栏无限轮播":
                getView().openFragment(new BannerFragment(), title);
                break;
            case "贝塞尔曲线":
                getView().openFragment(new BezierFragment(), title);
                break;
            case "RxBus案例":
                break;
            case "TextView高亮显示":
//				openFragment(new TranslationFragment(), title);
                break;
            case "加载网络图片":
                getView().openFragment(new LoadImageFragment(), title);
                break;
            case "onMeasure和onLayout":
                getView().openFragment(new TestMeasureLayoutFragment(), title);
                break;
            case "TimerView":
                getView().openFragment(new TimerViewFragment(), title);
                break;
        }
    }

    protected void initData() {
        mViewList.add(new TextBean("PatternDemo", ""));
        mViewList.add(new TextBean("DemoActivity", "一个用来显示Demo的Activity"));
        mViewList.add(new TextBean("TimerView", "RecyclerView多类型布局"));
        mViewList.add(new TextBean("Glide transform", "..."));
        mViewList.add(new TextBean("TextView高亮显示", "点击英文单词可以高亮显示，并且显示翻译结果"));
        mViewList.add(new TextBean("加载网络图片", "使用Glide对一组url进行加载，下拉刷新，下拉加载更多"));
        mViewList.add(new TextBean("仿ios底部弹出对话框", "可以随意设定标题和选项按钮"));
        mViewList.add(new TextBean("Activity动画特效", "待完善..."));
        mViewList.add(new TextBean("京东头条控件", "模仿京东头条，上下无限滚动"));
        mViewList.add(new TextBean("显示未读消息数控件", "类似QQ、微信底部tab标签，可以显示未读消息的数量"));
        mViewList.add(new TextBean("广告倒计时控件", "修复了Timer类会内存溢出的问题"));
        mViewList.add(new TextBean("网络请求", "Retrofit+RxJava+okhttp实现异步的网络请求"));
        mViewList.add(new TextBean("点赞列表", "从左到右排列一组头像"));
        mViewList.add(new TextBean("可以拖动的布局", "类似iPhone AssistiveTouch效果"));
        mViewList.add(new TextBean("刮刮卡", "自定义view实现"));
        mViewList.add(new TextBean("广告栏无限轮播", "加入了动画效果"));
        mViewList.add(new TextBean("贝塞尔曲线", "TypedValue和nterpolator的高级使用"));
        mViewList.add(new TextBean("RxBus案例", "轻松解决组件与组件之间的消息传递"));
        mViewList.add(new TextBean("onMeasure和onLayout", "高级自定义View"));
    }

    public void init() {
        initData();
        getView().setDataList(mViewList, new TextHolderAdatpter.TextHolderClickListener() {
            @Override
            public void onTextClick(int position) {
                onItemClick(position);
            }
        });
    }
}