package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.mvp.presenter.NetworkFragmentPresenter;
import com.wuzhanglao.niubi.mvp.view.NetworkFragmentView;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.utils.UIUtils;

/**
 * Created by wuming on 16/10/19.
 */

public class NetworkFragment extends BaseMvpFragment<NetworkFragmentView, NetworkFragmentPresenter>
        implements View.OnClickListener, NetworkFragmentView {

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
                presenter.request("北京");
                break;
        }
    }

    @Override
    public NetworkFragmentPresenter initPresenter() {
        return new NetworkFragmentPresenter();
    }


    @Override
    public void requestSuccess(Object obj) {
        AppUtils.showToast("请求成功");
    }

    @Override
    public void requestFailed(String msg) {
        AppUtils.showToast("请求失败");
    }
}
