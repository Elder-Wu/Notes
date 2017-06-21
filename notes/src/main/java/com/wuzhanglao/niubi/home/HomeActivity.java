package com.wuzhanglao.niubi.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.base.DefaultToolbar;
import com.wuzhanglao.niubi.utils.ToastUtil;
import com.wuzhanglao.niubi.utils.UIUtils;

public class HomeActivity extends BaseActivity {

	private HomePresenter mPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		DefaultToolbar toolbar = new DefaultToolbar(this);
		toolbar.setTitle("主界面");
		toolbar.hideBackIcon();

		mPresenter = new HomePresenter();
		mPresenter.setView(new HomeView(this));
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
