package com.example.customview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.customview.R;
import com.example.customview.presenter.ApproveListFragmentMvpPresenter;
import com.example.customview.view.ApproveListFragmentMvpView;
import com.example.customview.widget.ApproveListLayout;

/**
 * Created by wuming on 2016/10/23.
 */

public class ApproveListFragment extends BaseMvpFragment<ApproveListFragmentMvpView, ApproveListFragmentMvpPresenter>
        implements ApproveListFragmentMvpView {

    private ApproveListLayout all;

    @Override
    public ApproveListFragmentMvpPresenter initPresenter() {
        return new ApproveListFragmentMvpPresenter();
    }

    @Override
    public int setResId() {
        return R.layout.fragment_approve_list;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        all = (ApproveListLayout) view.findViewById(R.id.approve_list_layout);
        all.updateApproveList(null);
    }
}
