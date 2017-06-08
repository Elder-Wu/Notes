package com.wuzhanglao.niubi.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;

public class HomeActivity extends BaseActivity {

	private HomeView mView;
	private HomePresenter mPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		initTranslucentStatusBar();
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mView = new HomeView(this);
		mPresenter = new HomePresenter();
		mPresenter.setView(mView);
		mPresenter.setModel(new HomeModel());
		mPresenter.attach();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		mView.hideBackButton();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.detach();
	}
}
