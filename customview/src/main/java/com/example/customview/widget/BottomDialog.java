package com.example.customview.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.R;

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
        super(context, R.style.custom_dialog);
        setContentView(R.layout.bottom_dialog);
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
    }

    public static class Builder {
        private Paraments p;
        private Context context;

        public Builder(Context context) {
            p = new Paraments();
            this.context = context;
        }

        public Builder setTitle(String title) {
            p.title = title;
            return this;
        }

        public Builder setTitleSize(int size) {
            p.titleSize = size;
            return this;
        }

        public Builder setTitleColor(int color) {
            p.titleColor = color;
            return this;
        }

        public Builder addOption(String option, OnOptionClickListener listener) {
            p.options.add(new Option(option, listener));
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            p.cancelable = cancelable;
            return this;
        }

        public Builder setOptionTextSize(int size) {
            p.optionTextSize = size;
            return this;
        }

        public Builder setOptionTextColor(int color) {
            p.optionTextColor = color;
            return this;
        }

        public BottomDialog create() {
            final BottomDialog dialog = new BottomDialog(context);
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
                for (final Option option : p.options) {
                    TextView optionText = new TextView(context);
                    optionText.setText(option.getName());
                    optionText.setTextSize(p.optionTextSize);
                    optionText.setTextColor(p.optionTextColor);
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
                }
            }
            return dialog;
        }
    }

    public interface OnOptionClickListener {
        void onOptionClick();
    }

}

//这个类保存了dialog的众多参数
class Paraments {
    public String title;
    public int titleColor;
    public int titleSize;
    public int optionTextColor;
    public int optionTextSize;
    public boolean cancelable;
    public List<Option> options;

    public Paraments() {
        title = "标题";
        titleColor = Color.BLACK;
        titleSize = 20;
        optionTextColor = Color.BLACK;
        optionTextSize = 20;
        cancelable = true;
        options = new ArrayList();
    }
}

class Option {
    private String name;
    private BottomDialog.OnOptionClickListener listener;

    public Option() {
    }

    public Option(String name, BottomDialog.OnOptionClickListener listener) {
        this.name = name;
        this.listener = listener;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BottomDialog.OnOptionClickListener getListener() {
        return listener;
    }

    public void setListener(BottomDialog.OnOptionClickListener listener) {
        this.listener = listener;
    }
}