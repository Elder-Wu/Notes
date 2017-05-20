package com.wuzhanglao.niubi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.widget.IosBottomDialog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wuming on 16/10/14.
 */

public class IosBottomDialogFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_dialog, container, false);
        rootView.findViewById(R.id.fragment_ios_bottom_dialog_btn1).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_ios_bottom_dialog_btn2).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_ios_bottom_dialog_btn3).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_ios_bottom_dialog_btn4).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_ios_bottom_dialog_btn5).setOnClickListener(this);
        rootView.findViewById(R.id.fragment_ios_bottom_dialog_btn6).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        final IosBottomDialog.Builder builder = new IosBottomDialog.Builder(getContext());
        switch (v.getId()) {
            case R.id.fragment_ios_bottom_dialog_btn1:
                //无标题，只有操作
                builder.addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作1");
                        Observable.interval(1, TimeUnit.SECONDS)
                                .take(((1 * 60 + 1) * 60 + 1) / 10)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long time) {
                                        Log.d("123123123","time->"+time.intValue());
                                    }
                                });
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
                                AppUtils.showToast("操作1");
                            }
                        }).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn4:
                //标题＋2个操作
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
                        }).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn5:
                //无标题，3个操作
                builder.addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作1");
                    }
                }).addOption("操作2", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作2");
                    }
                }).addOption("操作3", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作3");
                    }
                }).create().show();
                break;
            case R.id.fragment_ios_bottom_dialog_btn6:
                //无标题，4个操作
                builder.addOption("操作1", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作1");
                    }
                }).addOption("操作2", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作2");
                    }
                }).addOption("操作3", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作3");
                    }
                }).addOption("操作4", Color.DKGRAY, new IosBottomDialog.OnOptionClickListener() {
                    @Override
                    public void onOptionClick() {
                        AppUtils.showToast("操作4");
                    }
                }).create().show();
                break;
        }
    }
}
