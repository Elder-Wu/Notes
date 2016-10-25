package com.example.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.customview.R;

public class RoundImageView extends ImageView {

    private Paint paint;

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        canvas.drawBitmap(getRoundBitmap(b), 0, 0, null);
    }

    private Bitmap getRoundBitmap(Bitmap src) {
        //新建一张空白的Bitmap，长宽高跟布局文件中定义的长宽高一样
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //将Bitmap作为一张画布，在上面作画
        Canvas canvas = new Canvas(bitmap);
        //画一个圆形
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2, paint);
        //设置paint的重叠方式，SRC_IN参数:保留重叠部分
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //把源文件中的Bitmap缩放一下，大小跟画布一样，然后绘制到canvas上，这样就和之前的圆形重叠了，出现了一个圆形的图片
        final Rect src_rect = new Rect(0, 0, src.getWidth(), src.getHeight());
        final Rect dest_rect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(src, src_rect, dest_rect, paint);
        //返回圆形的Bitmap
        return bitmap;
    }

    @Override
    public boolean isInEditMode() {
        setBackgroundResource(R.drawable.demo);
        return true;
    }
}