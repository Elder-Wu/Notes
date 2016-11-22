package com.wuzhanglao.niubi.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
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
        setContentView(setContentView());
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
    }

    /**
     * 可以达到沉浸式状态栏的效果
     */
    protected void afterSetContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明底部导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //如果不进行这步操作，整个View树就会往上偏移一点点   具体的原因，看一下setFitsSystemWindows()方法就好
            final ViewGroup contentLayout = (ViewGroup) findViewById(android.R.id.content);
            final View contentChild = contentLayout.getChildAt(0);
            contentChild.setFitsSystemWindows(true);
        }
        // create our manager instance after the content view is set
        final SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set a custom tint color for all system bars
        tintManager.setTintColor(setSystemBarColor());
    }

    protected abstract Action1<Object> setOnNext();

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

    protected abstract int setSystemBarColor();

    protected abstract int setContentView();

    protected abstract void initView();

    protected void initData() {
    }

}
