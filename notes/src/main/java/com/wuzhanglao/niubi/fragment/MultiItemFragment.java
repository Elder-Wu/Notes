package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ming.wu@shanbay.com on 2017/4/27.
 */

public class MultiItemFragment extends BaseFragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_multi_item, container, false);

		List<String> dataList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			dataList.add(i+"号");
		}
		Observable.from(dataList)
				.buffer(5)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<List<String>>() {
					@Override
					public void call(List<String> strings) {
						Log.d("345345",strings.toString());
					}
				});

		return rootView;
	}
}
