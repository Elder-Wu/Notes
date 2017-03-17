package com.wuzhanglao.niubi.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;

/*
 * Created by ming.wu@shanbay.com on 2017/3/13.
 */

public class GlideFragment extends BaseFragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_glide, container, false);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
		Glide.with(getContext())
				.load("http://pic.qiantucdn.com/58pic/18/31/50/70T58PIC4Xi_1024.jpg")
//				.load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2197390997,3461607957&fm=23&gp=0.jpg")
//				.load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2530539761,4266142259&fm=23&gp=0.jpg")
//				.transform(new RoundTransform(getContext(), 10f))
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.skipMemoryCache(true)
				.crossFade()
				.into(imageView);

		final ImageView imageView2 = (ImageView) rootView.findViewById(R.id.image2);
		imageView2.post(new Runnable() {
			@Override
			public void run() {
				Glide.with(getContext())
						.load("http://pic.qiantucdn.com/58pic/18/31/50/70T58PIC4Xi_1024.jpg")
//				.load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2197390997,3461607957&fm=23&gp=0.jpg")
//				.load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2530539761,4266142259&fm=23&gp=0.jpg")
						.transform(new RoundTransform(getContext(), imageView2.getMeasuredWidth(), 10))
						.diskCacheStrategy(DiskCacheStrategy.NONE)
						.skipMemoryCache(true)
						.crossFade()
						.into(imageView2);
			}
		});

		return rootView;
	}

	private class RoundTransform extends BitmapTransformation {

		private float radius = 0f;
		private float rawWidth = 0;

		public RoundTransform(Context context, float rawWidth, float radius) {
			super(context);
			this.radius = radius;
			this.rawWidth = rawWidth;
		}

		private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
			if (source == null)
				return null;

			Matrix matrix = new Matrix();
			matrix.preScale(rawWidth / source.getWidth(), rawWidth / source.getWidth());
			source = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

			Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
			if (result == null) {
				result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
			}

			Canvas canvas = new Canvas(result);
			Paint paint = new Paint();
			paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
			paint.setAntiAlias(true);

			RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
			canvas.drawRoundRect(rectF, radius, radius, paint);
			return result;
		}

		@Override
		protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
			return roundCrop(pool, toTransform);
		}

		@Override
		public String getId() {
			return getClass().getName();
		}
	}
}