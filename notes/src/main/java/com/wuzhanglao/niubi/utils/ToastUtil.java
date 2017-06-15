package com.wuzhanglao.niubi.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

/**
 * create by wuzhanglao at 2017/6/10
 */

public class ToastUtil {

    public static void showInfo(Context context,String msg) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
//            Toasty.Config.getInstance().setInfoColor(Color.parseColor("#CAE1FF")).apply();
//            Toasty.info(NoteApplication.getInstance(), msg, 2).show();

            Toast toast = new Toast(context);
            toast.setView(new ToastView(context, msg));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private static class ToastView extends View {

        private String msg;
        private Rect mTextBounds = new Rect();
        private RectF mRectF = new RectF();
        private Paint mPaint = new Paint();

        public ToastView(Context context, String msg) {
            super(context);
            this.msg = msg;
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, NoteApplication.getInstance().getResources().getDisplayMetrics()));

            mPaint.getTextBounds(msg, 0, msg.length(), mTextBounds);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mTextBounds.width() + Math.round(UIUtils.dp2px(20)), MeasureSpec.EXACTLY);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mTextBounds.height() + Math.round(fontMetrics.top - fontMetrics.ascent) + UIUtils.dp2px(10).intValue(), MeasureSpec.EXACTLY);
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            mRectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }

        @Override
        protected void onDraw(Canvas canvas) {
            mPaint.setColor(Color.LTGRAY);
            canvas.drawRoundRect(mRectF, UIUtils.dp2px(6), UIUtils.dp2px(6), mPaint);
            mPaint.setColor(Color.WHITE);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            canvas.drawText(msg, getMeasuredWidth() / 2.0f, getMeasuredHeight(), mPaint);
        }
    }
}
