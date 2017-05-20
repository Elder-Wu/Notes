package com.wuzhanglao.niubi.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

/*
 * Created by ming.wu@shanbay.com on 2017/4/9.
 */

public class IndicatorDialog extends Dialog implements DialogInterface.OnDismissListener {

	private Activity mActivity;

	public IndicatorDialog(@NonNull Activity activity) {
		super(activity);

		Window window = this.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);

		setContentView(null);
		setOnDismissListener(this);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		mActivity.finish();
	}
}
