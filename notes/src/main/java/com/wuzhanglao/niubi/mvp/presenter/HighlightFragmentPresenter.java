package com.wuzhanglao.niubi.mvp.presenter;

import com.wuzhanglao.niubi.mvp.model.ShanbayResp;
import com.wuzhanglao.niubi.mvp.view.HighlightFragmentView;
import com.wuzhanglao.niubi.utils.NetworkRequest;

import rx.functions.Action1;

/**
 * Created by wuming on 2016/12/2.
 */

public class HighlightFragmentPresenter extends BaseMvpPresenter<HighlightFragmentView> {
    public void getTranslation(String word) {
        Action1<ShanbayResp> onNext = new Action1<ShanbayResp>() {
            @Override
            public void call(ShanbayResp shanbayResp) {
                if (shanbayResp.getMsg().equals("SUCCESS")) {
                    view.getTranslationSuccess(shanbayResp);
                } else {
                    view.getTranslationFailed(shanbayResp);
                }
            }
        };
        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.getTranslationFailed(throwable);
            }
        };
        NetworkRequest.getInstance().getShanbayTranslation(word, onNext, onError);
    }
}
