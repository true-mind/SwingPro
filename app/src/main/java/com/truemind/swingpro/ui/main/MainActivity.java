package com.truemind.swingpro.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.truemind.swingpro.BaseActivity;
import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-15.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlideMenu("SwingPro");
    }
}
