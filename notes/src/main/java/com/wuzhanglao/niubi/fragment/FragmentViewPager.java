package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.widget.ImageBanner;

/**
 * Created by wuming on 2016/11/10.
 */

public class FragmentViewPager extends BaseFragment {

    private ImageBanner banner;

    @Override
    public int setResId() {
        return R.layout.fragment_viewpager;
    }

@Override
public void initView(View view, @Nullable Bundle savedInstanceState) {
    banner = (ImageBanner) view.findViewById(R.id.fragment_viewpager_imagebanner);
    banner.addImage(getImageView(R.drawable.mv1));
    banner.addImage(getImageView(R.drawable.mv2));
    banner.addImage(getImageView(R.drawable.mv3));
    banner.addImage(getImageView(R.drawable.mv4));
}

private ImageView getImageView(int resId) {
    ImageView image = new ImageView(context);
    image.setImageResource(resId);
    return image;
}
}
