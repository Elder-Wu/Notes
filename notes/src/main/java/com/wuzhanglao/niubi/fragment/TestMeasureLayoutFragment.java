package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.widget.TestMeasureLayout;

/**
 * Created by wuming on 2017/1/16.
 */

public class TestMeasureLayoutFragment extends BaseFragment {

    private TestMeasureLayout mTestMeasureLayout;

    @Override
    public int setResId() {
        return R.layout.fragment_test_measure_layout;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mTestMeasureLayout = (TestMeasureLayout) view.findViewById(R.id.test_measure_layout);
    }
}
