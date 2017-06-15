package com.wuzhanglao.niubi.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.utils.ToastUtil;
import com.wuzhanglao.niubi.utils.UIUtils;

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
		if (!UIUtils.isDoubleClick(2000)) {
			ToastUtil.showInfo(this, "再按一次退出");
			return;
		}
		super.onBackPressed();
//		mView.hideBackButton();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.detach();
	}
}
