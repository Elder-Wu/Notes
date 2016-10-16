package com.example.customview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.customview.R;
import com.example.customview.adapter.HeadlineHolderAdapter;
import com.example.customview.bean.HeadlineBean;
import com.example.customview.utils.ToastUtils;
import com.example.customview.widget.TaobaoHeadline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 16/10/16.
 */

public class TaobaoHeadlineFragment extends BaseFragment {

    private TaobaoHeadline taobaoHeadline;

    @Override
    protected int setResId() {
        return R.layout.fragment_taobao_headline;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        List<HeadlineBean> data = new ArrayList<>();
        data.add(new HeadlineBean("热", "还是短发根深蒂固舒服的感受是对分身乏术是减肥损公肥私风格舒服的感受"));
        data.add(new HeadlineBean("荐", "还是短发根深蒂固舒服的感受是对分身乏术是减肥损公肥私风格舒服的感受"));
        data.add(new HeadlineBean("好", "还是短发根深蒂固舒服的感受是对分身乏术是减肥损公肥私风格舒服的感受"));
        data.add(new HeadlineBean("省", "还是短发根深蒂固舒服的感受是对分身乏术是减肥损公肥私风格舒服的感受"));
        taobaoHeadline = (TaobaoHeadline) view.findViewById(R.id.fragment_taobao_headline_headline);
        taobaoHeadline.setData(data);
        taobaoHeadline.setHeadlineClickListener(new HeadlineHolderAdapter.HeadlineClickListener() {
            @Override
            public void onHeadlineClick(List<HeadlineBean> data, int position_in_data) {
                ToastUtils.show(data.get(position_in_data).getTitle());
            }
        });
    }
}
