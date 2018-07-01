package com.wuzhanglao.niubi.fragment;

/**
 * Created by wuming on 2016/12/2.
 */

//public class TranslationFragment extends BaseMvpFragment<HighlightFragmentView, HighlightFragmentPresenter>
//        implements HighlightFragmentView {
//
//    private static final String TAG = TranslationFragment.class.getSimpleName();
//    private static final String s = "      Any contributions, large or small, #$% 174major 1884 features, bug fixes, additional language translations, unit/integration tests are welcomed and appreciated but will be thoroughly reviewed and discussed.";
//    private ClickableTextView englishText;
//    private TranslationDialog translationDialog;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_highlight,container,false);
//        englishText = (ClickableTextView) rootView.findViewById(R.id.fragment_highlight_text);
//        englishText.setText(s);
//        englishText.setHighlightTextClickListener(new ClickableTextView.HighLightTextClickListener() {
//            @Override
//            public void onHighlightTextClick(String text) {
//                translationDialog.showLoading();
//                presenter.getTranslation(text);
//            }
//        });
//        translationDialog = new TranslationDialog(getContext());
//        return rootView;
//    }
//
//    //获取翻译结果成功,展示查询结果
//    @Override
//    public void getTranslationSuccess(Object result) {
//        Log.d(TAG, result.toString());
//        translationDialog.showTranslation((ShanbayResp) result);
//    }
//
//    //获取翻译结果失败
//    @Override
//    public void getTranslationFailed() {
//        translationDialog.dismiss();
//        if (NetworkUtil.isNetworkAvailable()) {
//            AppUtils.showToast("查询失败");
//        } else {
//            AppUtils.showToast("请检查网络连接");
//        }
//    }
//
//    @Override
//    public HighlightFragmentPresenter initPresenter() {
//        return new HighlightFragmentPresenter();
//    }
//
//
//}
