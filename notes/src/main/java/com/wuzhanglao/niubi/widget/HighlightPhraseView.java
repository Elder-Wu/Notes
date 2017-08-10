package com.wuzhanglao.niubi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ming.wu@shanbay.com on 2017/5/24.
 */

public class HighlightPhraseView extends View {

    private static final int DEFAULT_LINE_SPACE = 2; //dp
    private static final int DEFAULT_TEXT_SIZE = 14; //dp
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

    private String mRawString;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mHighlightColor = DEFAULT_HIGHLIGHT_COLOR;

    private List<String> mLineStringList = new ArrayList<>();
    private Map<Integer, String> mPhraseMap = new HashMap<>();
    private Map<String, Integer> mPhraseLineMap = new HashMap<>();

    private Paint mPaint = new Paint();
    private Rect mHighlightRect = new Rect();

    public HighlightPhraseView(Context context) {
        this(context, null);
    }

    public HighlightPhraseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(dp2px(DEFAULT_TEXT_SIZE));
    }

    public void setDefaultColor(int color) {
        mTextColor = color;
        invalidate();
    }

    public void setHighlightColor(int color) {
        mHighlightColor = color;
        invalidate();
    }

    public void setDisplayedText(String text, List<String> highlighted) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        mRawString = text;
        for (String phrase : highlighted) {
            mPhraseMap.put(mRawString.indexOf(phrase), phrase);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (TextUtils.isEmpty(mRawString)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int startIndex = 0;
        int endIndex = 0;
        boolean isPhrase;
        float curLineWidth;
        String curString;
        int width = MeasureSpec.getSize(widthMeasureSpec);

        mPhraseLineMap.clear();
        mLineStringList.clear();

        while (endIndex < mRawString.length()) {

            curLineWidth = mPaint.measureText(mRawString, startIndex, endIndex);

            String phrase = mPhraseMap.get(endIndex);
            if (phrase != null) {
                isPhrase = true;
                curString = phrase;
            } else if (!isEnglishChar(mRawString.charAt(endIndex))) {
                isPhrase = false;
                curString = String.valueOf(mRawString.charAt(endIndex));
            } else {
                isPhrase = false;
                int wordEndIndex = endIndex;
                while (wordEndIndex < mRawString.length()) {
                    if (!isEnglishChar(mRawString.charAt(wordEndIndex))) {
                        break;
                    }
                    wordEndIndex++;
                }
                curString = mRawString.substring(endIndex, wordEndIndex);
            }

            float curStringWidth = mPaint.measureText(curString);
            if (curLineWidth + curStringWidth <= width) {
                endIndex += curString.length();
            } else {
                mLineStringList.add(mRawString.substring(mRawString.charAt(startIndex) == ' ' ? startIndex + 1 : startIndex, endIndex));
                startIndex = endIndex;
                endIndex += curString.length();
            }

            if (endIndex == mRawString.length()) {
                mLineStringList.add(mRawString.substring(mRawString.charAt(startIndex) == ' ' ? startIndex + 1 : startIndex, endIndex));
            }

            if (isPhrase) {
                mPhraseLineMap.put(phrase, mLineStringList.size());
            }
        }

        for (String line : mLineStringList) {
            Log.d(getClass().getSimpleName(), line);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int curLine = 0;
        int curHeight = 0;
        int lineHeight = mPaint.getFontMetricsInt().descent - mPaint.getFontMetricsInt().ascent + dp2px(DEFAULT_LINE_SPACE * 3);

        for (String line : mLineStringList) {

            //draw highlight rect area
            if (mPhraseLineMap.containsValue(curLine)) {
                for (Map.Entry<String, Integer> entry : mPhraseLineMap.entrySet()) {
                    if (entry.getValue() != curLine) {
                        continue;
                    }

                    String phrase = entry.getKey();
                    int index = line.indexOf(phrase);
                    int left = (int) mPaint.measureText(line.substring(0, index));
                    int right = (int) (left + mPaint.measureText(line.substring(index, index + phrase.length())));
                    mHighlightRect.set(left, curHeight + dp2px(DEFAULT_LINE_SPACE), right, curHeight + lineHeight - dp2px(DEFAULT_LINE_SPACE));
                    mPaint.setColor(mHighlightColor);
                    canvas.drawRect(mHighlightRect, mPaint);
                }
            }

            //draw text
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int baseline = (curHeight + curHeight + lineHeight - fontMetrics.bottom - fontMetrics.top) / 2;
            mPaint.setColor(mTextColor);
            canvas.drawText(line, 0, baseline, mPaint);

            curLine++;
            curHeight += lineHeight;
        }
    }

    private int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private boolean isEnglishChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '\'') || (c == '-');
    }
}
