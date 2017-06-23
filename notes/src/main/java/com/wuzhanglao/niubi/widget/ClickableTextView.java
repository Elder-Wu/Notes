package com.wuzhanglao.niubi.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wuming on 2016/12/5.
 */

public class ClickableTextView extends TextView {

	private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#444444");
	private static final int DEFAULT_FOREGROUND_COLOR = Color.WHITE;
	private static final int DEFAULT_BACKGROUND_COLOR = Color.BLUE;

	private int defaultTextColor = DEFAULT_TEXT_COLOR;

	private HighLightTextClickListener listener;
	private SpannableString ss;

	public ClickableTextView(Context context) {
		this(context, null);
	}

	public ClickableTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		setMovementMethod(LinkMovementMethod.getInstance());
		setHighlightColor(Color.TRANSPARENT);
	}

	public void setText(String text) {
		if (!TextUtils.isEmpty(text)) {
			ss = getRenderedString(text);
			setText(ss);
		}
	}

	//获取渲染之后的String
	private SpannableString getRenderedString(String s) {
		final SpannableString spannableString = new SpannableString(s);
		int flagPosition = 0;
		StringBuilder subString = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			//遇到分隔符时，就进行高亮处理
			if (!isEnglishElement(c + "") || i == s.length() - 1) {
				String word = subString.toString();
				if (isEnglishElement(word)) {
					spannableString.setSpan(new MyClickableSpan(flagPosition, flagPosition + word.length()), flagPosition, flagPosition + word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				subString = new StringBuilder();
				flagPosition += word.length() + 1;
			} else {
				subString.append(c);
			}
		}
		return spannableString;
	}

	//利用正则表达式匹配英文字符
	private boolean isEnglishElement(String word) {
		final String regex = "^[A-Za-z]+$";
		return word.matches(regex);
	}

	private ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(DEFAULT_FOREGROUND_COLOR);
	private BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(DEFAULT_BACKGROUND_COLOR);

	private final class MyClickableSpan extends ClickableSpan {

		private final int start;
		private final int end;

		public MyClickableSpan(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void onClick(View widget) {
			if (listener != null) {
				listener.onHighlightTextClick(ss.subSequence(start, end).toString());
			}
			//清空高亮区域
			ss.removeSpan(foregroundColorSpan);
			ss.removeSpan(backgroundColorSpan);
			if (widget instanceof TextView) {
				//设置字体前景色
				ss.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				//设置字体背景色
				ss.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			((TextView) widget).setText(ss);
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			//默认的字体颜色
			ds.setColor(defaultTextColor);
			ds.setUnderlineText(false);
		}
	}

	public void setHighlightTextClickListener(HighLightTextClickListener listener) {
		this.listener = listener;
	}

	public interface HighLightTextClickListener {
		void onHighlightTextClick(String text);
	}

	public void setDefaultTextColor(int color) {
		defaultTextColor = color;
	}

	public void setDefaultForegroundColor(int color) {
		foregroundColorSpan = new ForegroundColorSpan(color);
	}

	public void setDefaultBackgroundColor(int color) {
		backgroundColorSpan = new BackgroundColorSpan(color);
	}
}
