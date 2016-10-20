package com.example.customview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.presenter.NetworkFragmentMvpPresenter;

/**
 * Created by wuming on 16/10/19.
 */

public class NetworkFragment extends BaseFragment implements View.OnClickListener {

    private TextView result;

    @Override
    public int setResId() {
        return R.layout.fragment_network;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        result = (TextView) view.findViewById(R.id.fragment_network_result_tv);
        view.findViewById(R.id.fragment_network_get_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_network_get_btn:
                new NetworkFragmentMvpPresenter().request("北京");
                break;
        }
    }
}
