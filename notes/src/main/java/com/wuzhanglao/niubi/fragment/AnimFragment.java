package com.wuzhanglao.niubi.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.utils.UIUtils;
import com.wuzhanglao.niubi.widget.IosBottomDialog;

/**
 * Created by wuming on 16/10/18.
 */

public class AnimFragment extends BaseFragment implements View.OnClickListener {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_anim, container, false);
		rootView.findViewById(R.id.fragment_anim_btn1).setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (UIUtils.isDoubleClick()) {
			return;
		}
		switch (v.getId()) {
			case R.id.fragment_anim_btn1:
				IosBottomDialog.Builder builder = new IosBottomDialog.Builder(getContext());
				builder.setTitle("标题", Color.RED)
						.addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
							@Override
							public void onOptionClick() {
								AppUtils.showToast("操作1");
							}
						})
						.addOption("操作2", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
							@Override
							public void onOptionClick() {
								AppUtils.showToast("操作2");
							}
						})
						.addOption("操作3", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
							@Override
							public void onOptionClick() {
								AppUtils.showToast("操作3");
							}
						})
						.setDialogDismissListener(new IosBottomDialog.IosBottomDialogDismissListener() {
							@Override
							public void onDismiss() {
								hideAnim(getActivity().getWindow().getDecorView());
							}
						})
						.create()
						.show();
				showAnim(getActivity().getWindow().getDecorView());
				break;
		}
	}

	private void hideAnim(View view) {
		ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
		rotationX.setStartDelay(200);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(view, View.SCALE_X, 0.8f, 1.0f).setDuration(350),
				ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.8f, 1.0f).setDuration(350),
				ObjectAnimator.ofFloat(view, View.ROTATION_X, 0f, 10).setDuration(200),
				rotationX,
				ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -0.1f * getResources().getDisplayMetrics().heightPixels, 0).setDuration(350)
		);
		animatorSet.start();
	}

	private void showAnim(View view) {
		ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, View.ROTATION_X, 10, 0f).setDuration(150);
		rotationX.setStartDelay(200);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(view, View.SCALE_X, 1.0f, 0.8f).setDuration(350),
				ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.0f, 0.8f).setDuration(350),
				ObjectAnimator.ofFloat(view, View.ROTATION_X, 0f, 10).setDuration(200),
				rotationX,
				ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, -0.1f * getResources().getDisplayMetrics().heightPixels).setDuration(350)
		);
		animatorSet.start();
	}
}
