package com.example.customview.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * 仿IOS风格的Dialog
 * 从底部弹出的
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

    private void addOption(String optionName, OnOptionClickListener onOptionClickListener) {
//        TextView
    }

    public static class Builder {
        private Parament p;
        private Context context;

        public Builder(Context context) {
            p = new Parament();
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
            if(p.title.isEmpty()){
                dialog.title.setVisibility(View.GONE);
                dialog.title_line.setVisibility(View.GONE);
            } else{
                dialog.title.setText(p.title);
                dialog.title.setTextColor(p.titleColor);
                dialog.title.setTextSize(p.titleSize);
            }
            if(p.options.size()==0){
                dialog.options_ll.setVisibility(View.GONE);
            } else {
                for(final Option option:p.options){
                    TextView optionText = new TextView(context);
                    optionText.setText(option.getName());
                    optionText.setTextSize(p.optionTextSize);
                    optionText.setTextColor(p.optionTextColor);
                    optionText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if(option.getListener()!=null){
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

    //这个类保存了dialog的众多参数
    private static class Parament {
        public String title = "标题";
        public int titleColor = Color.BLACK;
        public int titleSize = 20;
        public int optionTextColor = Color.BLACK;
        public int optionTextSize = 20;
        public boolean cancelable = true;
        public List<Option> options = new ArrayList();

        public Parament() {
        }
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