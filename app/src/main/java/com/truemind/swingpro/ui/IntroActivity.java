package com.truemind.swingpro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.truemind.swingpro.BaseActivity;
import com.truemind.swingpro.R;
import com.truemind.swingpro.ui.main.MainActivity;

public class IntroActivity extends BaseActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ImageView titleLogo = (ImageView)findViewById(R.id.titleLogo);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.alpha);
        titleLogo.startAnimation(animation);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 1000);
    }
}
