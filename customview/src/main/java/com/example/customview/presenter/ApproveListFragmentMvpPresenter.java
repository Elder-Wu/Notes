package com.example.customview.presenter;

import com.example.customview.view.ApproveListFragmentMvpView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 2016/10/23.
 */

public class ApproveListFragmentMvpPresenter extends  BaseMvpPresenter<ApproveListFragmentMvpView> {
    public List<String> getHeadList() {
        List<String> urlList = new ArrayList<>();
        urlList.add("");
        return urlList;
    }
}
