package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.widget.BottomBar;

/**
 * Created by wuming on 16/10/18.
 */

public class BottomBarFragment extends BaseFragment implements View.OnClickListener {

    private BottomBar bottomBar;

    @Override
    public int setResId() {
        return R.layout.fragment_bottom_bar;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        bottomBar = (BottomBar) view.findViewById(R.id.fragment_bottom_bar);
        view.findViewById(R.id.fragment_bottombar_add1_btn).setOnClickListener(this);
        view.findViewById(R.id.fragment_bottombar_add10_btn).setOnClickListener(this);
        view.findViewById(R.id.fragment_bottombar_delete1_btn).setOnClickListener(this);
        view.findViewById(R.id.fragment_bottombar_deleteall_btn).setOnClickListener(this);
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
