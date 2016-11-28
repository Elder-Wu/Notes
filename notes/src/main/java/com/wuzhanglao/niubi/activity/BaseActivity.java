package com.wuzhanglao.niubi.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.wuzhanglao.niubi.utils.RxBus;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wuming on 16/10/13.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    public Context context;
    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //设置contentView
        beforeSetContentView();
        setContentView(setContentResId());
        afterSetContentView();
        //初始化RxBus
        initRxBus(setOnNext());
        //初始化数据
        initData();
        //初始化View
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(context);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindRxBus();
    }

    protected void beforeSetContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明底部导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void afterSetContentView() {
    }

    private Action1<Object> setOnNext() {
        return new Action1<Object>() {

            @Override
            public void call(Object o) {
                doOnNext(o);
            }
        };
    }

    protected abstract void doOnNext(Object o);

    private void initRxBus(final Action1<Object> onNext) {
        if (onNext != null) {
            subscription = RxBus.toObserverable()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext);
        }
    }

    private void unbindRxBus() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    protected abstract int setContentResId();

    protected abstract void initView();

    protected void initData() {
    }

}
