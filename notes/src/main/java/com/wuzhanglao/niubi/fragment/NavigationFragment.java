package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.utils.AppUtils;

/**
 * Created by wuming on 2016/12/2.
 */

public class NavigationFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);
        rootView.findViewById(R.id.fragment_navi_checkupdate).setOnClickListener(this);
        rootView.findViewById(R.id.navi_fragment_contact).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_fragment_contact:
                new AlertDialog.Builder(getContext())
                        .setTitle("个人主页")
                        .setMessage("www.wuzhanglao.com")
                        .setPositiveButton("确定", null).create().show();
                break;
            case R.id.fragment_navi_checkupdate:
                AppUtils.showToast("该功能正在完善中...");
                break;
        }
    }
}
