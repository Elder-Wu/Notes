package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;

/**
 * Created by wuming on 2016/11/14.
 */

public class DragRefreshFragment extends BaseFragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		Logger.d("测试Logger.d(d)");
		Logger.t(1).d("测试Logger.d(d)");
		Logger.t(2).d("测试Logger.d(d)");
		Logger.t(3).d("测试Logger.d(d)");
		Logger.d("message", "测试Logger.d(message,d)");
		Logger.d("测试Logger.d(d)");


		Logger.e("测试Logger.e(e)");
		Logger.e("message", "测试Logger.e(message,e)");


		Logger.i("测试Logger.i(i)");
		Logger.i("message", "测试Logger.i(message,i)");

		Logger.json("{\"name\":\"tom\",\"sex\":\"男\",\"age\":\"24\"}");
		Logger.xml("<?xml version=\"1.0\"?>\n" +
				"   <EMPLIST>\n" +
				"     <EMP>\n" +
				"      <ENAME>MARY</ENAME>\n" +
				"     </EMP>\n" +
				"     <EMP>\n" +
				"      <ENAME>SCOTT</ENAME>\n" +
				"     </EMP>\n" +
				"   </EMPLIST>");


		return inflater.inflate(R.layout.fragment_drag_refresh, container, false);
	}
}
