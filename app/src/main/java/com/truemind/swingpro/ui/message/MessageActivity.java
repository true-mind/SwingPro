package com.truemind.swingpro.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-19.
 */

public class MessageActivity extends BaseActivity {

    TextView noMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initSlideMenu("메시지함");
        initView();
    }

    public void initView() {

        noMessage = (TextView) findViewById(R.id.noMessage);
        setFontToViewBold(noMessage);
    }

    @Override
    public void onBack() {
        finish();
    }
}
