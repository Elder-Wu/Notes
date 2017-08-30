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

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.adapter.TextHolderAdatpter;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.base.mvp.BaseMvpView;
import com.wuzhanglao.niubi.bean.TextBean;
import com.wuzhanglao.niubi.misc.DemoActivity;
import com.wuzhanglao.niubi.view.ExoPlayerDemo;

import java.util.List;

public class HomeMvpView extends BaseMvpView<HomeMvpView.Callback> {

	private TextHolderAdatpter mAdapter;
	private DrawerLayout mDrawerLayout;

	private FragmentManager mFragmentManager;

	public HomeMvpView(BaseActivity activity) {
		super(activity);
		mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.activity_main_drawer);
		mFragmentManager = activity.getSupportFragmentManager();
	}

	public void openFragment(Fragment fragment, String fragmentName) {
		mFragmentManager.beginTransaction()
				.replace(R.id.main_container, fragment)
				.addToBackStack(null)
				.commitAllowingStateLoss();
	}

	public void setDataList(List<TextBean> dataList, TextHolderAdatpter.TextHolderClickListener listener) {
		mAdapter = new TextHolderAdatpter(getActivity());
		mAdapter.setTextHolderClickListener(listener);
		mAdapter.setDataList(dataList);

		RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(mAdapter);
	}

	public void startDemoActivity() {
		DemoActivity.launch(getActivity(), new ExoPlayerDemo(getActivity()));
	}

	public interface Callback {
	}
}
