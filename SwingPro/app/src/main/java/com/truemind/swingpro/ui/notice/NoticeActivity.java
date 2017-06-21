package com.truemind.swingpro.ui.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-19.
 */

public class NoticeActivity extends BaseActivity {

    TextView noMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initSlideMenu("공지사항");
        initView();
    }

    public void initView(){

        noMessage = (TextView)findViewById(R.id.noMessage);
        setFontToViewBold(noMessage);
    }
}