package com.wuzhanglao.niubi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.mvp.model.ShanbayResp;
import com.wuzhanglao.niubi.mvp.presenter.HighlightFragmentMvpPresenter;
import com.wuzhanglao.niubi.mvp.view.HighlightFragmentMvpView;
import com.wuzhanglao.niubi.utils.UIUtils;

/**
 * Created by wuming on 2016/12/2.
 */

public class FragmentHighlight extends BaseMvpFragment<HighlightFragmentMvpView, HighlightFragmentMvpPresenter>
        implements HighlightFragmentMvpView {

    private static final String TAG = FragmentHighlight.class.getSimpleName();
    private static final String s = "    Any contributions, large or small, #$% 174major 1884 features, bug fixes, additional language translations, unit/integration tests are welcomed and appreciated but will be thoroughly reviewed and discussed.";
    private final ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.WHITE);
    private final BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.BLUE);
    private SpannableString ss;
    private TextView text;

    @Override
    public int setResId() {
        return R.layout.fragment_highlight;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        initData();
        text = (TextView) view.findViewById(R.id.fragment_highlight_text);
        text.setText(getRenderString(s));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initData() {
        ss = new SpannableString(s);
    }

    //获取渲染之后的Stirng
    private SpannableString getRenderString(String s) {
        int flagPosition = 0;
        StringBuilder subString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (!isEnglishElement(c + "") || i == s.length() - 1) {
                String word = subString.toString();
                if (isEnglishElement(word)) {
                    ss.setSpan(new MyClickableSpan(flagPosition, flagPosition + word.length()), flagPosition, flagPosition + word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                subString = new StringBuilder();
                flagPosition += word.length() + 1;
            } else {
                subString.append(c);
            }
        }
        return ss;
    }

    private boolean isEnglishElement(String word) {
        final String regex = "^[A-Za-z]+$";
        return word.matches(regex);
    }

    @Override
    public HighlightFragmentMvpPresenter initPresenter() {
        return new HighlightFragmentMvpPresenter();
    }

    @Override
    public void getTranslationSuccess(Object result) {
        final ShanbayResp resp = (ShanbayResp) result;
        UIUtils.showToast("获取翻译成功!" + resp.getData().getCn_definition());
    }

    @Override
    public void getTranslationFailed(Object result) {
        UIUtils.showToast("获取翻译失败!" + result.toString());
    }

    private final class MyClickableSpan extends ClickableSpan {

        private final int start;
        private final int end;

        public MyClickableSpan(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void onClick(View widget) {
            ss.removeSpan(foregroundColorSpan);
            ss.removeSpan(backgroundColorSpan);
            if (widget instanceof TextView) {
                //设置字体前景色
                ss.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //设置字体背景色
                ss.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((TextView) widget).setText(ss);
            }
            presenter.getTranslation(ss.subSequence(start, end).toString());
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#444444"));
            ds.setUnderlineText(false);
        }
    }
}
