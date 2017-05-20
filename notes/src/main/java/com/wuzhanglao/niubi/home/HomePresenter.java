package com.wuzhanglao.niubi.home;
/*
 * date:2017/2/17
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.base.mvp.BaseMvpPresenter;
import com.wuzhanglao.niubi.fragment.AnimFragment;
import com.wuzhanglao.niubi.fragment.ApproveListFragment;
import com.wuzhanglao.niubi.fragment.BannerFragment;
import com.wuzhanglao.niubi.fragment.BezierFragment;
import com.wuzhanglao.niubi.fragment.BottomBarFragment;
import com.wuzhanglao.niubi.fragment.CountDownFragment;
import com.wuzhanglao.niubi.fragment.FloatViewFragment;
import com.wuzhanglao.niubi.fragment.GlideFragment;
import com.wuzhanglao.niubi.fragment.GuaGuaKaFragment;
import com.wuzhanglao.niubi.fragment.IosBottomDialogFragment;
import com.wuzhanglao.niubi.fragment.LoadImageFragment;
import com.wuzhanglao.niubi.fragment.NetworkFragment;
import com.wuzhanglao.niubi.fragment.TBHeadlineFragment;
import com.wuzhanglao.niubi.fragment.TestMeasureLayoutFragment;
import com.wuzhanglao.niubi.fragment.TranslationFragment;
import com.wuzhanglao.niubi.utils.UIUtils;

public class HomePresenter extends BaseMvpPresenter<HomeView, HomeModel> implements HomeViewCallback {

    private FragmentManager mFragmentManager;

    public HomePresenter(BaseActivity activity) {
        super(activity);
        getView().setCallback(this);
        mFragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public HomeModel setModel() {
        return new HomeModel();
    }

    @Override
    public HomeView setView() {
        return new HomeView(getActivity().getWindow().getDecorView());
    }

    @Override
    public void onBackBtnClick() {
        // TODO: 按钮双击问题解决
        if (UIUtils.isDoubleClick()) {
            return;
        }
        getActivity().onBackPressed();
    }

    @Override
    public void onItemClick(String title) {
        switch (title) {
            case "Glide transform":
                openFragment(new GlideFragment(), title);
                break;
            case "显示未读消息数控件":
                openFragment(new BottomBarFragment(), title);
                break;
            case "仿ios底部弹出对话框":
                openFragment(new IosBottomDialogFragment(), title);
                break;
            case "Activity动画特效":
                openFragment(new AnimFragment(), title);
                break;
            case "京东头条控件":
                openFragment(new TBHeadlineFragment(), title);
                break;
            case "广告倒计时控件":
                openFragment(new CountDownFragment(), title);
                break;
            case "网络请求":
                openFragment(new NetworkFragment(), title);
                break;
            case "点赞列表":
                openFragment(new ApproveListFragment(), title);
                break;
            case "可以拖动的布局":
                openFragment(new FloatViewFragment(), title);
                break;
            case "刮刮卡":
                openFragment(new GuaGuaKaFragment(), title);
                break;
            case "广告栏无限轮播":
                openFragment(new BannerFragment(), title);
                break;
            case "贝塞尔曲线":
                openFragment(new BezierFragment(), title);
                break;
            case "RxBus案例":
                getActivity().startActivity(new Intent(getActivity(), TestRxActivity1.class));
                break;
            case "TextView高亮显示":
                openFragment(new TranslationFragment(), title);
                break;
            case "加载网络图片":
                openFragment(new LoadImageFragment(), title);
                break;
            case "onMeasure和onLayout":
                openFragment(new TestMeasureLayoutFragment(), title);
                break;
        }
    }

    private void openFragment(Fragment fragment, String fragmentName) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
        getView().showBackButton(fragmentName);
    }
}