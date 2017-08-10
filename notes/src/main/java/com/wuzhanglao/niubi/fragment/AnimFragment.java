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
import com.wuzhanglao.niubi.widget.HighlightPhraseView;
import com.wuzhanglao.niubi.widget.IosBottomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 16/10/18.
 */

public class AnimFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_anim, container, false);
        rootView.findViewById(R.id.fragment_anim_btn1).setOnClickListener(this);

        final HighlightPhraseView highlightPhraseView = (HighlightPhraseView) rootView.findViewById(R.id.highlight_phrase_view);
        rootView.findViewById(R.id.matcher_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                list.add("a suite of");
                list.add("has many benefits");
                list.add("In most cases");
                list.add("have not broken existing behaviour");
                list.add("Starting off with");
                list.add("choice for");
                list.add("throughout the project");
                list.add("starting point to");
                list.add("move through the codebase");
                list.add("one more reason to do");

                highlightPhraseView.setDisplayedText("Having a suite of unit and integration tests has many benefits.In most cases they are there to provide confidence that changes have not broken existing behaviour.Starting off with the less complex data classes was the clear choice for me. They are being used throughout the project, yet their complexity is comparatively low. This makes them an ideal starting point to set off the journey into a new language.After migrating some of these using the Kotlin code converter, which is built into Android Studio, executing tests and making them pass, I worked my way up until eventually ending up migrating the tests themselves to Kotlin.Without tests, I would have been required to go through the touched features after each change, and manually verify them.By having this automated it was a lot quicker and easier to move through the codebase, migrating code as I went along.So, if you don’t have your app tested properly yet, there’s one more reason to do so right here", list);
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (UIUtils.isDoubleClick(300)) {
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
