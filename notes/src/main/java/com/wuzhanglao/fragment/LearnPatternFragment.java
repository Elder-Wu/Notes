package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ming.wu@shanbay.com on 2017/4/23.
 */

public class LearnPatternFragment extends BaseFragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_learn_pattern,container,false);
		TextView textView = (TextView) rootView.findViewById(R.id.learn_pattern_result);
		Pattern pattern = Pattern.compile("\\*\\*[\\w\\-\\.\\s]+\\*\\*");
		Matcher matcher = pattern.matcher("word word is a **press**");
		String s = "word word is a **press**";
//		s.replaceAll("\"\\\\*\\\\*[\\\\w\\\\-\\\\.\\\\s]+\\\\*\\\\*\"")
		return rootView;
	}
}
