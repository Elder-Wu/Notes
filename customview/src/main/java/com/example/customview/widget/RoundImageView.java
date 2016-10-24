package com.example.customview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.customview.R;

/**
 * Created by wuming on 2016/10/24.
 */

public class RoundImageView extends ImageView {

    private Paint bitmapPaint;
    private Paint borderPaint;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        bitmapPaint = new Paint();
        bitmapPaint.setDither(true);
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setFilterBitmap(true);
        Bitmap bitmap = getBackgroundBitmap();
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.headline);
        }
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapPaint.setShader(shader);

        borderPaint = new Paint();
        borderPaint.setDither(true);
        borderPaint.setAntiAlias(true);
        borderPaint.setFilterBitmap(true);
        borderPaint.setColor(Color.GREEN);
    }

    public Bitmap getBackgroundBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getBackground();
        if (bitmapDrawable == null) {
            return null;
        }
        Bitmap bitmap = bitmapDrawable.getBitmap();
        if (bitmap == null) {
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        c.drawCircle(getWidth() / 2, getHeight() / 2, radius, borderPaint);
        canvas.
    }
}
