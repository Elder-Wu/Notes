package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.adapter.ImageHolderAdapter;
import com.wuzhanglao.niubi.adapter.ImageLoadHelper;
import com.wuzhanglao.niubi.mvp.presenter.LoadImageFragmentPresenter;
import com.wuzhanglao.niubi.mvp.view.LoadImageFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 2016/12/3.
 */

public class LoadImageFragment extends BaseMvpFragment<LoadImageFragmentView, LoadImageFragmentPresenter>
        implements LoadImageFragmentView {

    private static final String TAG = LoadImageFragment.class.getSimpleName();
    private ImageLoadHelper imageLoadHelper;
    private List<String> urlList;

    @Override
    public LoadImageFragmentPresenter initPresenter() {
        return new LoadImageFragmentPresenter();
    }

    @Override
    public int setResId() {
        return R.layout.fragment_loadimage;
    }

    private void initData() {
        urlList = new ArrayList<>();
        urlList.add("https://static.baydn.com/media/media_store/image/f1672263006c6e28bb9dee7652fa4cf6.jpg");
        urlList.add("https://static.baydn.com/media/media_store/image/8c997fae9ebb2b22ecc098a379cc2ca3.jpg");
        urlList.add("https://static.baydn.com/media/media_store/image/2a4616f067285b4bd59fe5401cd7106b.jpeg");
        urlList.add("https://static.baydn.com/media/media_store/image/b0e3ab329c8d8218d2af5c8dfdc21125.jpg");
        urlList.add("https://static.baydn.com/media/media_store/image/670abb28408a9a0fc3dd9666e5ca1584.jpeg");
        urlList.add("https://static.baydn.com/media/media_store/image/1e8d675468ab61f4e5bdebd4bcb5f007.jpeg");
        urlList.add("https://static.baydn.com/media/media_store/image/9b2f93cbfa104dae1e67f540ff14a4c2.jpg");
        urlList.add("https://static.baydn.com/media/media_store/image/f5e0631e00a09edbbf2eb21eb71b4d3c.jpeg");
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        initData();
        imageLoadHelper = new ImageLoadHelper(context);
        imageLoadHelper.setSwipeRefreshLayout((SwipeRefreshLayout) view.findViewById(R.id.fragment_loadimage_refreshlayout));
        imageLoadHelper.setRecyclerView((RecyclerView) view.findViewById(R.id.fragment_loadimage_rv));
        imageLoadHelper.setAdapter(new ImageHolderAdapter(context, urlList));
        imageLoadHelper.init();
    }

    @Override
    public void loadImageSuccess() {

    }

    @Override
    public void loadImageFailed() {

    }
}
