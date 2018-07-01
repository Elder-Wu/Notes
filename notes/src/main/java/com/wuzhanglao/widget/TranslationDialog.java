//package com.wuzhanglao.niubi.widget;
//
///*
// * Created by wuming on 2016/12/6.
// */
//
//import android.content.Context;
//import android.media.MediaPlayer;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.ViewSwitcher;
//
//import com.wuzhanglao.niubi.R;
//import com.wuzhanglao.mvp.model.ShanbayResp;
//
//import java.io.IOException;
//
//public class TranslationDialog extends CommonDialog {
//
//    private static final String TAG = TranslationDialog.class.getSimpleName();
//    private String currentAudio;
//    private MediaPlayer mediaPlayer = new MediaPlayer();
//    private TextView word_tv;
//    private TextView phonetic_tv;
//    private TextView translation_tv;
//    private ImageView more_iv;
//    private ViewSwitcher viewSwitcher;
//    private boolean clickFlag = false;
//
//    public TranslationDialog(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected View setCustomContentView() {
//        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_translation, new RelativeLayout(getContext()));
//        word_tv = (TextView) rootView.findViewById(R.id.dialog_translation_word_tv);
//        phonetic_tv = (TextView) rootView.findViewById(R.id.dialog_translation_phonetic_tv);
//        translation_tv = (TextView) rootView.findViewById(R.id.dialog_translation_translation_tv);
//        viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.dialog_translation_viewswitcher);
//        more_iv = (ImageView) rootView.findViewById(R.id.dialog_translation_more_iv);
//
//        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (translation_tv.getLayout().getLineCount() > 3) {
//                    if (clickFlag) {
//                        more_iv.setVisibility(View.GONE);
//                    } else {
//                        more_iv.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    more_iv.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        more_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickFlag = true;
//                translation_tv.setMaxLines(Integer.MAX_VALUE);
//            }
//        });
//        word_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(currentAudio)) {
//                    Log.d(TAG, "audio->" + currentAudio);
//                    try {
//                        mediaPlayer.reset();
//                        mediaPlayer.setDataSource(currentAudio);
//                        mediaPlayer.prepare();
//                        mediaPlayer.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        return rootView;
//    }
//
//    public void showLoading() {
//        show();
//        translation_tv.setMaxLines(3);
//        viewSwitcher.setDisplayedChild(1);
//    }
//
//    @Override
//    public void dismiss() {
//        super.dismiss();
//        clickFlag = false;
//    }
//
//    public void showTranslation(ShanbayResp resp) {
//        word_tv.setText(resp.getData().getContent());
//        phonetic_tv.setText("/" + resp.getData().getPronunciation() + "/");
//        translation_tv.setText(resp.getData().getCn_definition().getDefn());
//        currentAudio = resp.getData().getUs_audio();
//        viewSwitcher.setDisplayedChild(0);
//    }
//}
