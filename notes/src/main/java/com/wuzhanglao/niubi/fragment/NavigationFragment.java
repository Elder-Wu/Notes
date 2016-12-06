package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.utils.UIUtils;

/**
 * Created by wuming on 2016/12/2.
 */

public class NavigationFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public int setResId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.fragment_navi_checkupdate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_navi_checkupdate:
                UIUtils.showToast("该功能正在完善中...");
                break;
        }
    }
}
