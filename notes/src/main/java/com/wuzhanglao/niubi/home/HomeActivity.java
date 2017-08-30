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

	private HomeMvpPresenter mPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		DefaultToolbar toolbar = new DefaultToolbar(this);
		toolbar.setmTvTitle("主界面");
		toolbar.hideBackIcon();

		mPresenter = new HomeMvpPresenter();
		mPresenter.setView(new HomeMvpView(this));
		mPresenter.setModel(new HomeMvpModel());
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
