package com.wuzhanglao.niubi.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

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

                if (TextUtils.isEmpty(shanbayResp.getMsg())) {
                    Log.d("result", "null");
                    view.getTranslationFailed();
                } else if (shanbayResp.getMsg().equals("SUCCESS")) {
                    Log.d("result", "success");
                    view.getTranslationFailed();
                } else {
                    Log.d("result", "else");
                    view.getTranslationFailed();
                }
            }
        };
        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("result",throwable.toString());
                view.getTranslationFailed();
            }
        };
        NetworkRequest.getInstance().getShanbayTranslation(word, onNext, onError);
    }
}
