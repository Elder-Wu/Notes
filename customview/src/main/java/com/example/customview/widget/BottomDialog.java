package com.example.customview.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 仿IOS风格－从底部弹出的dialog
 * Created by wuming on 16/10/12.
 */

public class BottomDialog extends Dialog {

    public LinearLayout options_ll;
    public TextView title;
    public View title_line;

    public BottomDialog(Context context) {
        //给dialog定制了一个主题（透明背景，无边框，无标题栏，浮在Activity上面，模糊）
        super(context, R.style.ios_bottom_dialog);
        setContentView(R.layout.ios_bottom_dialog);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.bottom_dialog_title_tv);
        title_line = findViewById(R.id.bottom_dialog_title_line);
        options_ll = (LinearLayout) findViewById(R.id.options_ll);

        findViewById(R.id.bottom_dialog_cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog.this.dismiss();
            }
        });
        //点击空白区域可以取消dialog
        this.setCanceledOnTouchOutside(true);
        //点击back键可以取消dialog
        this.setCancelable(true);
        Window window = this.getWindow();
        //让Dialog显示在屏幕的底部
        window.setGravity(Gravity.BOTTOM);
        //设置窗口出现和窗口隐藏的动画
        window.setWindowAnimations(R.style.ios_bottom_dialog_anim);
        //设置BottomDialog的宽高属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public interface OnOptionClickListener {
        void onOptionClick();
    }

    public static class Builder {
        private Paraments p;
        private Context context;

        public Builder(Context context) {
            p = new Paraments();
            this.context = context;
        }

        public Builder setTitle(String title, int color) {
            p.title = title;
            p.titleColor = color;
            return this;
        }

        public Builder addOption(String option, int color, OnOptionClickListener listener) {
            p.options.add(new Option(option, color, listener));
            return this;
        }

        public BottomDialog create() {
            final BottomDialog dialog = new BottomDialog(context);
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            if (p.title.isEmpty()) {
                //设置标题栏不可见
                dialog.title.setVisibility(View.GONE);
                dialog.title_line.setVisibility(View.GONE);
            } else {
                dialog.title.setText(p.title);
                dialog.title.setTextColor(p.titleColor);
                dialog.title.setTextSize(p.titleSize);
                //设置标题栏可见
                dialog.title.setVisibility(View.VISIBLE);
                dialog.title_line.setVisibility(View.VISIBLE);
            }
            if (p.options.size() == 0) {
                dialog.options_ll.setVisibility(View.GONE);
            } else {
                for (int i = 0; i < p.options.size(); i++) {
                    final Option option = p.options.get(i);
                    final TextView optionText = new TextView(context);
                    optionText.setPadding(UIUtils.dp2px(20), UIUtils.dp2px(20), UIUtils.dp2px(20), UIUtils.dp2px(20));
                    optionText.setText(option.getName());
                    optionText.setTextSize(p.optionTextSize);
                    optionText.setGravity(Gravity.CENTER);
                    optionText.setTextColor(option.getColor());
                    optionText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (option.getListener() != null) {
                                option.getListener().onOptionClick();
                            }
                        }
                    });
                    dialog.options_ll.addView(optionText);
                    //添加条目之间的分割线
                    if (i != p.options.size() - 1) {
                        View divider = new View(context);
                        divider.setBackgroundColor(Color.BLACK);
                        dialog.options_ll.addView(divider, params);
                    }
                    //选择到底使用哪个selector文件
                    if(p.options.size()==1){
                        optionText.setBackgroundResource(R.drawable.bottom_dialog_option13);
                    } else if(i == 0){
                        optionText.setBackgroundResource(R.drawable.bottom_dialog_option1);
                    } else if(i<p.options.size()-1){
                        optionText.setBackgroundResource(R.drawable.bottom_dialog_option2);
                    } else {
                        optionText.setBackgroundResource(R.drawable.bottom_dialog_option3);
                    }
                }
            }
            return dialog;
        }
    }
}

//这个类保存了dialog的众多参数
class Paraments {
    //// TODO: 16/10/14 这里应该进行一下dp和px的转换
    public static final int titleSize = UIUtils.dp2px(20);
    public static final int optionTextSize = UIUtils.dp2px(20);
    public String title;
    public int titleColor;
    public boolean cancelable;
    public List<Option> options;

    public Paraments() {
        title = "";
        titleColor = Color.BLACK;
        cancelable = true;
        options = new ArrayList();
    }
}

class Option {
    private String name;
    private int color;
    private BottomDialog.OnOptionClickListener listener;

    public Option() {
    }

    public Option(String name, int color, BottomDialog.OnOptionClickListener listener) {
        this.name = name;
        this.color = color;
        this.listener = listener;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public BottomDialog.OnOptionClickListener getListener() {
        return listener;
    }

    public void setListener(BottomDialog.OnOptionClickListener listener) {
        this.listener = listener;
    }
}