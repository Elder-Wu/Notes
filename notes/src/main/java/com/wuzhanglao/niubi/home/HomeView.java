package com.wuzhanglao.niubi.home;
/*
 * date:2017/2/17
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.base.mvp.BaseMvpView;
import com.wuzhanglao.niubi.bean.TextBean;
import com.wuzhanglao.niubi.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeView extends BaseMvpView<HomeViewCallback> implements TextHolderAdatpter.TextHolderClickListener {

	private TextHolderAdatpter mAdapter;
	private DrawerLayout mDrawerLayout;
	//toolbar相关
	private TextView toolbar_title_tv;
	private TextView toolbar_back_tv;
	private ImageView toolbar_profile_iv;

	public HomeView(View rootView) {
		super(rootView);

		toolbar_title_tv = (TextView) rootView.findViewById(R.id.activity_main_toolbar_title_tv);
		toolbar_back_tv = (TextView) rootView.findViewById(R.id.activity_main_toolbar_back_tv);
		toolbar_profile_iv = (ImageView) rootView.findViewById(R.id.activity_main_toolbar_profile_iv);

		toolbar_title_tv.setText(getContext().getString(R.string.main_title));
		toolbar_back_tv.setVisibility(View.GONE);
		toolbar_profile_iv.setVisibility(View.GONE);
		toolbar_back_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getCallback() != null) {
					getCallback().onBackBtnClick();
				}
			}
		});
		toolbar_profile_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (UIUtils.isDoubleClick()) {
					return;
				}
				mDrawerLayout.openDrawer(GravityCompat.START);
			}
		});
		hideBackButton();
		initData();
		initView();












		Logger.d("测试Logger.d(d)");
		Logger.d("message", "测试Logger.d(message,d)");

		Logger.e("测试Logger.e(e)");
		Logger.e("message", "测试Logger.e(message,e)");


		Logger.t(1).d("测试Logger.d(d)");
		Logger.t(2).d("测试Logger.d(d)");
		Logger.t(3).d("测试Logger.d(d)");

		Logger.init().hideThreadInfo().methodCount(0);

		Logger.json("{\"name\":\"tom\",\"sex\":\"男\",\"age\":\"24\"}");
		Logger.xml("<?xml version=\"1.0\"?>\n" +
				"   <EMPLIST>\n" +
				"     <EMP>\n" +
				"      <ENAME>MARY</ENAME>\n" +
				"     </EMP>\n" +
				"     <EMP>\n" +
				"      <ENAME>SCOTT</ENAME>\n" +
				"     </EMP>\n" +
				"   </EMPLIST>");























	}

	public void showBackButton(String fragmentName) {
		toolbar_back_tv.setVisibility(View.VISIBLE);
		toolbar_profile_iv.setVisibility(View.GONE);
		toolbar_title_tv.setText(fragmentName);
		if (mDrawerLayout != null) {
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		}
	}

	public void hideBackButton() {
		toolbar_back_tv.setVisibility(View.GONE);
		toolbar_profile_iv.setVisibility(View.VISIBLE);
		toolbar_title_tv.setText(getContext().getString(R.string.main_title));
		if (mDrawerLayout != null) {
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
		}
	}

	protected void initData() {
		List<TextBean> data = new ArrayList<>();
		data.add(new TextBean("MultiItem", "RecyclerView多类型布局"));
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

		mAdapter = new TextHolderAdatpter(getContext());
		mAdapter.setTextHolderClickListener(this);
		mAdapter.setDataList(data);
	}

	private void initView() {
		mDrawerLayout = (DrawerLayout) getRootView().findViewById(R.id.activity_main_drawer);
		RecyclerView recyclerView = (RecyclerView) getRootView().findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onTextClick(TextBean bean) {
		getCallback().onItemClick(bean.getTitle());
	}
}
