package com.example.customview.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.customview.R;
import com.example.customview.utils.ToastUtils;
import com.example.customview.widget.IosBottomDialog;

/**
 * Created by wuming on 16/10/14.
 */

public class IosBottomDialogFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = IosBottomDialogFragment.class.getSimpleName();

    @Override
    public int setResId() {
        return R.layout.fragment_bottom_dialog;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.fragment_ios_bottom_dialog_btn1).setOnClickListener(this);
        view.findViewById(R.id.fragment_ios_bottom_dialog_btn2).setOnClickListener(this);
        view.findViewById(R.id.fragment_ios_bottom_dialog_btn3).setOnClickListener(this);
        view.findViewById(R.id.fragment_ios_bottom_dialog_btn4).setOnClickListener(this);
        view.findViewById(R.id.fragment_ios_bottom_dialog_btn5).setOnClickListener(this);
        view.findViewById(R.id.fragment_ios_bottom_dialog_btn6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final IosBottomDialog.Builder builder = new IosBottomDialog.Builder(context);
        switch (v.getId()) {
            case R.id.fragment_ios_bottom_dialog_btn1:
                //无标题，只有操作
                builder.addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作1");
                    }
                }).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn2:
                //无操作，只有标题
                builder.setTitle("标题", Color.RED).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn3:
                //标题＋1个操作
                builder.setTitle("标题", Color.RED)
                        .addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                            @Override
                            public void onOptionClick() {
                                ToastUtils.show("操作1");
                            }
                        }).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn4:
                //标题＋2个操作
                builder.setTitle("标题", Color.RED)
                        .addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                            @Override
                            public void onOptionClick() {
                                ToastUtils.show("操作1");
                            }
                        })
                        .addOption("操作2", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                            @Override
                            public void onOptionClick() {
                                ToastUtils.show("操作2");
                            }
                        }).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn5:
                //无标题，3个操作
                builder.addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作1");
                    }
                }).addOption("操作2", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作2");
                    }
                }).addOption("操作3", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作3");
                    }
                }).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn6:
                //无标题，4个操作
                builder.addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作1");
                    }
                }).addOption("操作2", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作2");
                    }
                }).addOption("操作3", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作3");
                    }
                }).addOption("操作4", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        ToastUtils.show("操作4");
                    }
                }).create().show();
                break;
        }
    }
}
