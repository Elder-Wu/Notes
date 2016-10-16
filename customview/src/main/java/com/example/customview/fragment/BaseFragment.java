package com.example.customview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customview.activity.MainActivity;

/**
 * Created by wuming on 16/10/14.
 */

public abstract class BaseFragment extends Fragment {

    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = this.getContext();
        return inflater.inflate(setResId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setTitle("软件主界面");
    }

    protected abstract int setResId();

    protected abstract void initView(View view, @Nullable Bundle savedInstanceState);
}
