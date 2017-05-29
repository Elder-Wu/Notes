package com.wuzhanglao.niubi.home;

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
import com.wuzhanglao.niubi.fragment.TimerViewFragment;
import com.wuzhanglao.niubi.fragment.NetworkFragment;
import com.wuzhanglao.niubi.fragment.TBHeadlineFragment;
import com.wuzhanglao.niubi.fragment.TestMeasureLayoutFragment;
import com.wuzhanglao.niubi.utils.UIUtils;

public class HomePresenter extends BaseMvpPresenter<HomeView, HomeModel> implements HomeCallback {

	@Override
	public void onAttach() {
		getView().setCallback(this);
	}

	@Override
	public void onBackBtnClick() {
		if (UIUtils.isDoubleClick()) {
			return;
		}
	}

	@Override
	public void onItemClick(String title) {
		switch (title) {
			case "Glide transform":
				getView().openFragment(new GlideFragment(), title);
				break;
			case "显示未读消息数控件":
				getView().openFragment(new BottomBarFragment(), title);
				break;
			case "仿ios底部弹出对话框":
				getView().openFragment(new IosBottomDialogFragment(), title);
				break;
			case "Activity动画特效":
				getView().openFragment(new AnimFragment(), title);
				break;
			case "京东头条控件":
				getView().openFragment(new TBHeadlineFragment(), title);
				break;
			case "广告倒计时控件":
				getView().openFragment(new CountDownFragment(), title);
				break;
			case "网络请求":
				getView().openFragment(new NetworkFragment(), title);
				break;
			case "点赞列表":
				getView().openFragment(new ApproveListFragment(), title);
				break;
			case "可以拖动的布局":
				getView().openFragment(new FloatViewFragment(), title);
				break;
			case "刮刮卡":
				getView().openFragment(new GuaGuaKaFragment(), title);
				break;
			case "广告栏无限轮播":
				getView().openFragment(new BannerFragment(), title);
				break;
			case "贝塞尔曲线":
				getView().openFragment(new BezierFragment(), title);
				break;
			case "RxBus案例":
				getView().openRxBus();
				break;
			case "TextView高亮显示":
//				openFragment(new TranslationFragment(), title);
				break;
			case "加载网络图片":
				getView().openFragment(new LoadImageFragment(), title);
				break;
			case "onMeasure和onLayout":
				getView().openFragment(new TestMeasureLayoutFragment(), title);
				break;
			case "TimerView":
				getView().openFragment(new TimerViewFragment(), title);
				break;
		}
	}
}