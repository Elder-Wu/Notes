package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.widget.Banner;

/**
 * Created by wuming on 2016/11/10.
 */

public class BannerFragment extends BaseFragment {

    private Banner banner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);
        banner = (Banner) rootView.findViewById(R.id.fragment_viewpager_imagebanner);
        banner.addContent(getImageView(R.drawable.img1));
        banner.addContent(getImageView(R.drawable.img2));
        banner.addContent(getImageView(R.drawable.img4));
        banner.startScroll();
        return rootView;
    }

    private ImageView getImageView(int resId) {
        ImageView image = new ImageView(getContext());
        image.setImageResource(resId);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(100, 100);
        image.setLayoutParams(params);
        return image;
    }
}
