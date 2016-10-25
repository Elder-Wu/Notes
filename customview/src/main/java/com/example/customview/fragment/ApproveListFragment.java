package com.example.customview.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.customview.R;
import com.example.customview.presenter.ApproveListFragmentMvpPresenter;
import com.example.customview.utils.ToastUtils;
import com.example.customview.view.ApproveListFragmentMvpView;
import com.example.customview.widget.ApproveListLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 2016/10/23.
 */

public class ApproveListFragment extends BaseMvpFragment<ApproveListFragmentMvpView, ApproveListFragmentMvpPresenter>
        implements ApproveListFragmentMvpView, View.OnClickListener {

    private ApproveListLayout all;
    private List<Integer> approveList;

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
        initData();
        all = (ApproveListLayout) view.findViewById(R.id.approve_list_layout);
        all.updateApproveList(approveList);

        view.findViewById(R.id.apprive_list_mv1_approve).setOnClickListener(this);
        view.findViewById(R.id.apprive_list_mv1_unapprove).setOnClickListener(this);
        view.findViewById(R.id.apprive_list_mv2_approve).setOnClickListener(this);
        view.findViewById(R.id.apprive_list_mv2_unapprove).setOnClickListener(this);
        view.findViewById(R.id.apprive_list_mv3_approve).setOnClickListener(this);
        view.findViewById(R.id.apprive_list_mv3_unapprove).setOnClickListener(this);
    }

    private void initData() {
        approveList = new ArrayList<>();
        approveList.add(R.drawable.demo);
        approveList.add(R.drawable.demo);
        approveList.add(R.drawable.demo);
        approveList.add(R.drawable.demo);
        approveList.add(R.drawable.demo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apprive_list_mv1_approve:
                if(!approveList.contains(R.drawable.mv1)){
                    approveList.add(0,R.drawable.mv1);
                } else {
                    ToastUtils.show("该用户已经点过赞了");
                }
                break;
            case R.id.apprive_list_mv1_unapprove:
                approveList.remove(new Integer(R.drawable.mv1));
                break;
            case R.id.apprive_list_mv2_approve:
                if(!approveList.contains(R.drawable.mv2)){
                    approveList.add(0,R.drawable.mv2);
                } else {
                    ToastUtils.show("该用户已经点过赞了");
                }
                break;
            case R.id.apprive_list_mv2_unapprove:
                approveList.remove(new Integer(R.drawable.mv2));
                break;
            case R.id.apprive_list_mv3_approve:
                if(!approveList.contains(R.drawable.mv3)){
                    approveList.add(0,R.drawable.mv3);
                } else {
                    ToastUtils.show("该用户已经点过赞了");
                }
                break;
            case R.id.apprive_list_mv3_unapprove:
                approveList.remove(new Integer(R.drawable.mv3));
                break;
        }
        all.updateApproveList(approveList);
    }
}
