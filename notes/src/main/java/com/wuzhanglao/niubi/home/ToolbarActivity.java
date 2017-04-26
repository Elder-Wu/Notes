package com.wuzhanglao.niubi.home;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;

/**
 * date:16/10/14
 * author:wuzhanglao
 * website:www.wuzhanglao.com
 */

public abstract class ToolbarActivity extends BaseActivity {

    private TextView toolbar_title_tv;
    private TextView toolbar_back_tv;

    public void initDefaultToolBar() {
        toolbar_back_tv = (TextView) findViewById(R.id.general_toolbar_back_tv);
        toolbar_title_tv = (TextView) findViewById(R.id.general_toolbar_title_tv);

        toolbar_back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setToolbarTitle(@NonNull String title) {
        toolbar_title_tv.setText(title);
    }

    public void setToolbarBackVisible(boolean visible) {
        if (visible) {
            toolbar_back_tv.setVisibility(View.VISIBLE);
        } else {
            toolbar_back_tv.setVisibility(View.INVISIBLE);
        }
    }
}
