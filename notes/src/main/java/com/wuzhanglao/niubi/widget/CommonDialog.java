package com.wuzhanglao.niubi.widget;

//public abstract class CommonDialog extends Dialog {

//    private static final String TAG = CommonDialog.class.getSimpleName();
//    private static final int DEFAULT_DIALOG_GRAVITY = Gravity.BOTTOM;
//    private static final int DEFAULT_DIALOG_ANIM = R.style.IosBottomDialog;
//    private DialogDismissListener dialogDismissListener;
//
//    public CommonDialog(Context context) {
//        //给dialog定制了一个主题（透明背景，无边框，无标题栏，浮在Activity上面，模糊）
//        super(context, R.style.ios_bottom_dialog);
//        setContentView(setCustomContentView());
//        initAttributes();
//    }
//
//    private void initAttributes() {
//        //点击空白区域可以取消dialog
//        this.setCanceledOnTouchOutside(true);
//        //点击back键可以取消dialog
//        this.setCancelable(true);
//        Window window = this.getWindow();
//        //让Dialog显示在屏幕的底部
//        window.setGravity(DEFAULT_DIALOG_GRAVITY);
//        //设置窗口出现和窗口隐藏的动画
//        window.setWindowAnimations(DEFAULT_DIALOG_ANIM);
//        //设置BottomDialog的宽高属性
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//    }
//
//    protected abstract View setCustomContentView();
//
//    @Override
//    public void dismiss() {
//        super.dismiss();
//        if (dialogDismissListener != null) {
//            dialogDismissListener.onDismiss();
//        }
//    }
//
//    public void setDialogDismissListener(DialogDismissListener listener) {
//        this.dialogDismissListener = listener;
//    }
//
//    public interface DialogDismissListener {
//        void onDismiss();
//    }
//
//    public void setDialogGravity(int gravity) {
//        this.getWindow().setGravity(gravity);
//    }
//
//    public void setDialogAnim(int anim) {
//        this.getWindow().setWindowAnimations(anim);
//    }
//}