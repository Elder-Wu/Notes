package com.wuzhanglao.niubi.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.wuzhanglao.niubi.R;

public class HomeActivity extends ToolbarActivity {

	private HomePresenter mPresenter;
	private HomeView mView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().setStatusBarColor(Color.YELLOW);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPresenter = new HomePresenter(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mView.hideBackButton();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.detach();
	}
}
