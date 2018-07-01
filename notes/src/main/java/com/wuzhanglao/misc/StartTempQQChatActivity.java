package com.wuzhanglao.niubi.misc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.DefaultToolbar;
import com.wuzhanglao.niubi.home.TransparentStatusActivity;

/**
 * Created by ming.wu@shanbay.com on 2017/6/15.
 */

public class StartTempQQChatActivity extends TransparentStatusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_temp_qq_chat);

        DefaultToolbar toolbar = new DefaultToolbar(this);
        toolbar.setmTvTitle("QQ临时对话");

        String qq = getIntent().getDataString();
        //mqqwpa://im/chat?chat_type=wpa&uin=910521727&version=1&src_type=web&web_src=qq.com
        String url = String.format("mqqwpa://im/chat?chat_type=wpa&uin=%s&version=1&src_type=web&web_src=qq.com",qq);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

}
