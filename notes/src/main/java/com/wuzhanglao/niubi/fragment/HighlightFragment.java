package com.wuzhanglao.niubi.fragment;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.mvp.model.ShanbayResp;
import com.wuzhanglao.niubi.mvp.presenter.HighlightFragmentMvpPresenter;
import com.wuzhanglao.niubi.mvp.view.HighlightFragmentMvpView;
import com.wuzhanglao.niubi.utils.UIUtils;
import com.wuzhanglao.niubi.widget.CommonDialog;

import java.io.IOException;

/**
 * Created by wuming on 2016/12/2.
 */

public class HighlightFragment extends BaseMvpFragment<HighlightFragmentMvpView, HighlightFragmentMvpPresenter>
        implements HighlightFragmentMvpView {

    private static final String TAG = HighlightFragment.class.getSimpleName();
    private static final String s = "Any contributions, large or small, #$% 174major 1884 features, bug fixes, additional language translations, unit/integration tests are welcomed and appreciated but will be thoroughly reviewed and discussed.";
    private SpannableString ss;
    private TextView englishText;
    private String currentAudio;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    //Dialog相关
    private TextView word_tv;
    private TextView phonetic_tv;
    private TextView translation_tv;
    private ImageView more_iv;
    private ViewSwitcher viewSwitcher;
    private CommonDialog translationDialog;

    @Override
    public int setResId() {
        return R.layout.fragment_highlight;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        initData();
        englishText = (TextView) view.findViewById(R.id.fragment_highlight_text);
        englishText.setMovementMethod(LinkMovementMethod.getInstance());
        englishText.setHighlightColor(Color.TRANSPARENT);
        englishText.setText(ss);
    }

    private void initData() {
        ss = getRenderedString(s);
        //给CommonDialog设置自己的布局
        translationDialog = new CommonDialog(context) {
            @Override
            protected View setCustomContentView() {
                View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_translation, new RelativeLayout(context));
                word_tv = (TextView) rootView.findViewById(R.id.dialog_translation_word_tv);
                phonetic_tv = (TextView) rootView.findViewById(R.id.dialog_translation_phonetic_tv);
                translation_tv = (TextView) rootView.findViewById(R.id.dialog_translation_translation_tv);
                viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.dialog_translation_viewswitcher);
                more_iv = (ImageView) rootView.findViewById(R.id.dialog_translation_more_iv);

                rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        Log.d(TAG, "执行");
                        if (translation_tv.getLayout().getLineCount() > 3) {
                            if(translation_tv.getMaxLines()>3){
                                more_iv.setVisibility(View.GONE);
                            } else {
                                more_iv.setVisibility(View.VISIBLE);
                            }
                        } else {
                            more_iv.setVisibility(View.GONE);
                        }
                    }
                });

                more_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        translation_tv.setMaxLines(Integer.MAX_VALUE);
                    }
                });
                word_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(currentAudio)) {
                            Log.d(TAG, "audio->" + currentAudio);
                            try {
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(currentAudio);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                return rootView;
            }
        };
    }

    //获取渲染之后的String
    private SpannableString getRenderedString(String s) {
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

    //利用正则表达式匹配英文字符
    private boolean isEnglishElement(String word) {
        final String regex = "^[A-Za-z]+$";
        return word.matches(regex);
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
            translation_tv.setMaxLines(3);
            viewSwitcher.setDisplayedChild(1);
            translationDialog.show();
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
            //默认的字体颜色
            ds.setColor(Color.parseColor("#444444"));
            ds.setUnderlineText(false);
        }
    }

    //获取翻译结果成功
    @Override
    public void getTranslationSuccess(Object result) {
        final ShanbayResp resp = (ShanbayResp) result;
        word_tv.setText(resp.getData().getContent());
        phonetic_tv.setText("/" + resp.getData().getPronunciation() + "/");
        translation_tv.setText(resp.getData().getCn_definition().getDefn());
        currentAudio = resp.getData().getUs_audio();
        viewSwitcher.setDisplayedChild(0);
    }

    //获取翻译结果失败
    @Override
    public void getTranslationFailed(Object result) {
        UIUtils.showToast(result.toString());
    }

    @Override
    public HighlightFragmentMvpPresenter initPresenter() {
        return new HighlightFragmentMvpPresenter();
    }
}
