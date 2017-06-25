package com.wuzhanglao.niubi.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.DefaultToolbar;
import com.wuzhanglao.niubi.utils.ToastUtil;
import com.wuzhanglao.niubi.utils.UIUtils;

public class HomeActivity extends TransparentStatusActivity {

	private HomeView mView;
	private HomePresenter mPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		DefaultToolbar toolbar = new DefaultToolbar(this);
		toolbar.setmTvTitle("主界面");
		toolbar.hideBackIcon();

		mView = new HomeView(this);
		mPresenter = new HomePresenter();
		mPresenter.setView(mView);
		mPresenter.attach();
		mPresenter.init();
	}

	@Override
	public void onBackPressed() {
		if (!UIUtils.isDoubleClick(2000)) {
			ToastUtil.showInfo("双击退出");
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.detach();
	}

	public static void launch(Activity activity) {
		Intent intent = new Intent(activity, HomeActivity.class);
		activity.startActivity(intent);
	}
}
