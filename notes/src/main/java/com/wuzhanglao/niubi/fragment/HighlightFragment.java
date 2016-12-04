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

public class HighlightFragment extends BaseMvpFragment<HighlightFragmentMvpView, HighlightFragmentMvpPresenter>
        implements HighlightFragmentMvpView {

    private static final String TAG = HighlightFragment.class.getSimpleName();
    private static final String s = "    Any contributions, large or small, #$% 174major 1884 features, bug fixes, additional language translations, unit/integration tests are welcomed and appreciated but will be thoroughly reviewed and discussed.";
    private SpannableString ss;
    private TextView englishText;

    @Override
    public int setResId() {
        return R.layout.fragment_highlight;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        initData();
        englishText = (TextView) view.findViewById(R.id.fragment_highlight_text);
        englishText.setText(ss);
        englishText.setMovementMethod(LinkMovementMethod.getInstance());
        englishText.setHighlightColor(Color.TRANSPARENT);
    }

    private void initData() {
        ss = getRenderString(s);
    }

    //获取渲染之后的String
    private SpannableString getRenderString(String s) {
        final SpannableString spannableString = new SpannableString(s);
        int flagPosition = 0;
        StringBuilder subString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            //遇到分隔符时，就进行高亮处理
            if (!isEnglishElement(c + "") || i == s.length() - 1) {
                String word = subString.toString();
                if (isEnglishElement(word)) {
                    spannableString.setSpan(new MyClickableSpan(flagPosition, flagPosition + word.length()), flagPosition, flagPosition + word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                subString = new StringBuilder();
                flagPosition += word.length() + 1;
            } else {
                subString.append(c);
            }
        }
        return spannableString;
    }

    //正则表达式匹配英文字符
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
        UIUtils.showToast(resp.getData().getCn_definition().getDefn());

        resp.getData().getAudio();
    }

    @Override
    public void getTranslationFailed(Object result) {
        UIUtils.showToast(result.toString());
    }

    private final ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.WHITE);
    private final BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.BLUE);

    private final class MyClickableSpan extends ClickableSpan {

        private final int start;
        private final int end;

        public MyClickableSpan(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void onClick(View widget) {
            //调用Shanbay翻译接口
            presenter.getTranslation(ss.subSequence(start, end).toString());
            //清空高亮区域
            ss.removeSpan(foregroundColorSpan);
            ss.removeSpan(backgroundColorSpan);
            if (widget instanceof TextView) {
                //设置字体前景色
                ss.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //设置字体背景色
                ss.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            ((TextView) widget).setText(ss);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#444444"));
            ds.setUnderlineText(false);
        }
    }
}
