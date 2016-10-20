package com.example.customview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuming on 16/10/14.
 */

public abstract class BaseFragment extends Fragment {

    public Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = this.getContext();
        return inflater.inflate(setResId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e("MainActivity", "BaseFragment onViewCreated");
        view.setClickable(true);
        initView(view, savedInstanceState);
    }

    public abstract int setResId();

    public abstract void initView(View view, @Nullable Bundle savedInstanceState);

}
