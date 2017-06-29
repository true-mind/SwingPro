package com.truemind.swingpro.ui.intro;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.R;

public class IntroActivity extends BaseActivity {

    Handler handler = new Handler();


    /**
     * for onBackPress
     */
    public static final long FINISH_INTERVAL_TIME = 2000;
    public long backPressedTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_intro);
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new IntroFragment())
                .commit();

        goLogin();

    }

    public void goLogin() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new LoginFragment())
                        .commit();
            }
        }, 1000);
    }

    @Override
    public void onBack() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getContext(), R.string.exitMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
