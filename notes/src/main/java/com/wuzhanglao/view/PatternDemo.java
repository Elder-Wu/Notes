package com.wuzhanglao.niubi.view;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

//		String regix ="";
//		Pattern pattern = Pattern.compile("shanbay.native.app://webview/feedback/input-box\\?action=(.+)&");
//		Matcher matcher = pattern.matcher("shanbay.native.app://webview/feedback/input-box?action=open&feedback_id=rlhkz");
//		if (matcher.find()) {
//			String group = matcher.group(0);
//			matcher.group(1);
//		}

		//(?i)

		SpannableString spannableString = new SpannableString("This Just to clear this up once and for all,this was not a mistake");
		String regex = "((?i)this)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher("This Just to clear this up once and for all,this was not a mistake");
		while (matcher.find()) {

			Log.d("123123123", "group->" + matcher.group() + " start->" + matcher.start() + " end->" + matcher.end());
			ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
			spannableString.setSpan(foregroundColorSpan, matcher.start(), matcher.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

		}

		TextView textView = (TextView) mRoot.findViewById(R.id.text);
		textView.setText(spannableString);

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
