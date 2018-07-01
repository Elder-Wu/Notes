package com.wuzhanglao.niubi.mvp.presenter;


import com.wuzhanglao.niubi.mvp.view.ApproveListFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 2016/10/23.
 */

public class ApproveListFragmentPresenter extends  BaseMvpPresenter<ApproveListFragmentView> {
    public List<String> getHeadList() {
        List<String> urlList = new ArrayList<>();
        urlList.add("");
        return urlList;
    }
}
