package com.wuzhanglao.niubi.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;

/**
 * Created by ming.wu@shanbay.com on 2017/6/19.
 */

public class DefaultToolbar implements View.OnClickListener {
	private BaseActivity mActivity;
	private TextView mTvTitle;
	private ImageView mIvBack;
	private TextView mTvRightText;
	private ImageView mIvRightIcon;

	public DefaultToolbar(BaseActivity activity) {
		mActivity = activity;
		mTvTitle = (TextView) activity.findViewById(R.id.default_toolbar_title);
		mIvBack = (ImageView) activity.findViewById(R.id.default_toolbar_back);
		mTvRightText = (TextView) activity.findViewById(R.id.default_toolbar_right_text);
		mIvRightIcon = (ImageView) activity.findViewById(R.id.default_toolbar_right_icon);

		mIvBack.setOnClickListener(this);
	}

	public void setmTvTitle(CharSequence mTvTitle) {
		this.mTvTitle.setText(mTvTitle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.default_toolbar_back:
				mActivity.onBackPressed();
				break;
		}
	}

	public void hideBackIcon() {
		mIvBack.setVisibility(View.GONE);
	}

	public void showRightIcon(View.OnClickListener listener) {
		mIvRightIcon.setOnClickListener(listener);
		mIvRightIcon.setVisibility(View.VISIBLE);
		mTvRightText.setVisibility(View.GONE);
	}

	public void showRightText(String text, View.OnClickListener listener) {
		mIvRightIcon.setVisibility(View.GONE);
		mTvRightText.setText(text);
		mTvRightText.setOnClickListener(listener);
		mTvRightText.setVisibility(View.VISIBLE);
	}
}
