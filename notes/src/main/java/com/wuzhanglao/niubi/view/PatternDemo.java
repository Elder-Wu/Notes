package com.wuzhanglao.niubi.view;

import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.misc.DemoActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ming.wu@shanbay.com on 2017/6/19.
 */

public class PatternDemo implements DemoActivity.DemoView {

	private View mRoot;

	public PatternDemo(BaseActivity activity) {
		mRoot = activity.getLayoutInflater().inflate(R.layout.layout_demo_pattern, null);

		String regix ="";
		Pattern pattern = Pattern.compile("shanbay.native.app://webview/feedback/input-box\\?action=(.+)&");
		Matcher matcher = pattern.matcher("shanbay.native.app://webview/feedback/input-box?action=open&feedback_id=rlhkz");
		if (matcher.find()) {
			String group = matcher.group(0);
			matcher.group(1);
		}

	}

	@Override
	public View getView() {
		return mRoot;
	}

	@Override
	public String getTitle() {
		return "正则表达式匹配";
	}
}
