package com.wuzhanglao.niubi.home;
/*
 * date:2017/2/17
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

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
import com.wuzhanglao.niubi.view.ExoPlayerDemo;
import com.wuzhanglao.niubi.view.PatternDemo;

import java.util.List;

public class HomeView extends BaseMvpView<HomeCallback> implements TextHolderAdatpter.TextHolderClickListener {

	private TextHolderAdatpter mAdapter;
	private DrawerLayout mDrawerLayout;

	private FragmentManager mFragmentManager;

	public HomeView(BaseActivity activity) {
		super(activity);
		mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.activity_main_drawer);
		RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(mAdapter);
		mFragmentManager = activity.getSupportFragmentManager();

	}

	@Override
	public void onTextClick(TextBean bean) {
		getCallback().onItemClick(bean.getTitle());
		if (TextUtils.equals(bean.getTitle(), "DemoActivity")) {
			DemoActivity.launch(getActivity(), new ExoPlayerDemo(getActivity()));
		} else if (TextUtils.equals(bean.getTitle(), "PatternDemo")) {
			DemoActivity.launch(getActivity(), new PatternDemo(getActivity()));
		}
	}

	public void openFragment(Fragment fragment, String fragmentName) {
		mFragmentManager.beginTransaction()
				.replace(R.id.main_container, fragment)
				.addToBackStack(null)
				.commitAllowingStateLoss();
	}

	public void setDataList(List<TextBean> dataList) {
		mAdapter = new TextHolderAdatpter(getActivity());
		mAdapter.setTextHolderClickListener(this);
		mAdapter.setDataList(dataList);
	}
}
