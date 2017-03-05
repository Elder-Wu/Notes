package com.wuzhanglao.niubi.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by wuming on 16/10/14.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //解决Fragment覆盖的时候会点到其他Fragment的问题
        view.setClickable(true);
        //给Fragment统一设置一个默认的背景，这样就不会透明了
        view.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }
}
