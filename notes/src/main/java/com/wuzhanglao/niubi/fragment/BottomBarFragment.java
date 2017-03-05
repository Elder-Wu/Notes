package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.widget.BottomBar;

/**
 * Created by wuming on 16/10/18.
 */

public class BottomBarFragment extends BaseFragment implements View.OnClickListener {

    private BottomBar bottomBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_bar, container, false);
        bottomBar = (BottomBar) rootView.findViewById(R.id.fragment_bottom_bar);
        rootView.findViewById(R.id.fragment_bottombar_add1_btn).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_bottombar_add10_btn).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_bottombar_delete1_btn).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_bottombar_deleteall_btn).setOnClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_bottombar_add1_btn:
                bottomBar.add();
                break;
            case R.id.fragment_bottombar_add10_btn:
                try {
                    bottomBar.add(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.fragment_bottombar_delete1_btn:
                bottomBar.delete();
                break;
            case R.id.fragment_bottombar_deleteall_btn:
                bottomBar.deleteAll();
                break;
        }
    }
}
