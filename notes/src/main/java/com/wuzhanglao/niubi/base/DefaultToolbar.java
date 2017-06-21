package com.wuzhanglao.niubi.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;

/**
 * Created by ming.wu@shanbay.com on 2017/6/19.
 */

public class DefaultToolbar implements View.OnClickListener{
	private BaseActivity activity;
	private TextView title;
	private ImageView back;

	public DefaultToolbar(BaseActivity activity) {
		this.activity = activity;
		title = (TextView) activity.findViewById(R.id.default_toolbar_title);
		back = (ImageView) activity.findViewById(R.id.default_toolbar_back);

		back.setOnClickListener(this);
	}

	public void setTitle(CharSequence title) {
		this.title.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.default_toolbar_back:
				activity.onBackPressed();
				break;
		}
	}

	public void hideBackIcon() {
		back.setVisibility(View.GONE);
	}
}
