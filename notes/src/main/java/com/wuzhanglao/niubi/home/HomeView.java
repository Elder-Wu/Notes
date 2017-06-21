package com.wuzhanglao.niubi.home;
/*
 * date:2017/2/17
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.base.mvp.BaseMvpView;
import com.wuzhanglao.niubi.bean.TextBean;
import com.wuzhanglao.niubi.misc.DemoActivity;
import com.wuzhanglao.niubi.utils.NoteApplication;
import com.wuzhanglao.niubi.view.ExoPlayerDemo;
import com.wuzhanglao.niubi.view.PatternDemo;

import java.util.ArrayList;
import java.util.List;

public class HomeView extends BaseMvpView<HomeCallback> implements TextHolderAdatpter.TextHolderClickListener {

	private TextHolderAdatpter mAdapter;
	private DrawerLayout mDrawerLayout;

	private FragmentManager mFragmentManager;

	public HomeView(BaseActivity activity) {
		super(activity);
		initData();
		initView();
		mFragmentManager = activity.getSupportFragmentManager();

	}

	protected void initData() {
		List<TextBean> data = new ArrayList<>();
		data.add(new TextBean("PatternDemo",""));
		data.add(new TextBean("DemoActivity", "一个用来显示Demo的Activity"));
		data.add(new TextBean("TimerView", "RecyclerView多类型布局"));
		data.add(new TextBean("Glide transform", "..."));
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
		data.add(new TextBean("onMeasure和onLayout", "高级自定义View"));

		mAdapter = new TextHolderAdatpter(getActivity());
		mAdapter.setTextHolderClickListener(this);
		mAdapter.setDataList(data);
	}

	private void initView() {
		mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.activity_main_drawer);
		RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onTextClick(TextBean bean) {
		getCallback().onItemClick(bean.getTitle());
		if (TextUtils.equals(bean.getTitle(), "DemoActivity")) {
			DemoActivity.launch(getActivity(), new ExoPlayerDemo(getActivity()));
		} else if(TextUtils.equals(bean.getTitle(),"PatternDemo")){
			DemoActivity.launch(getActivity(), new PatternDemo(getActivity()));
		}
	}

	public void openFragment(Fragment fragment, String fragmentName) {
		mFragmentManager.beginTransaction()
				.replace(R.id.main_container, fragment)
				.addToBackStack(null)
				.commitAllowingStateLoss();
	}

	public void openRxBus() {
		getActivity().startActivity(new Intent(NoteApplication.getInstance(), TransparentStatusActivity.class));
	}
}
