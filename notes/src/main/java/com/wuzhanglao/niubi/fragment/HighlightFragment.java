package com.wuzhanglao.niubi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.mvp.model.ShanbayResp;
import com.wuzhanglao.niubi.mvp.presenter.HighlightFragmentPresenter;
import com.wuzhanglao.niubi.mvp.view.HighlightFragmentView;
import com.wuzhanglao.niubi.utils.UIUtils;
import com.wuzhanglao.niubi.widget.ClickableTextView;
import com.wuzhanglao.niubi.widget.TranslationDialog;

/**
 * Created by wuming on 2016/12/2.
 */

public class HighlightFragment extends BaseMvpFragment<HighlightFragmentView, HighlightFragmentPresenter>
        implements HighlightFragmentView {

    private static final String TAG = HighlightFragment.class.getSimpleName();
    private static final String s = "Any contributions, large or small, #$% 174major 1884 features, bug fixes, additional language translations, unit/integration tests are welcomed and appreciated but will be thoroughly reviewed and discussed.";
    private ClickableTextView englishText;
    private TranslationDialog translationDialog;

    @Override
    public int setResId() {
        return R.layout.fragment_highlight;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        englishText = (ClickableTextView) view.findViewById(R.id.fragment_highlight_text);
        englishText.setText(s);
        englishText.setHighlightTextClickListener(new ClickableTextView.HighLightTextClickListener() {
            @Override
            public void onHighlightTextClick(String text) {
                translationDialog.showLoading();
                presenter.getTranslation(text);
            }
        });
        translationDialog = new TranslationDialog(context);
    }

    //获取翻译结果成功
    @Override
    public void getTranslationSuccess(Object result) {
        translationDialog.showTranslation((ShanbayResp) result);
    }

    //获取翻译结果失败
    @Override
    public void getTranslationFailed(Object result) {
        UIUtils.showToast("获取翻译失败");
        translationDialog.dismiss();
    }

    @Override
    public HighlightFragmentPresenter initPresenter() {
        return new HighlightFragmentPresenter();
    }


}
