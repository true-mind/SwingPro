package com.truemind.swingpro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.truemind.swingpro.BaseActivity;
import com.truemind.swingpro.R;
import com.truemind.swingpro.ui.main.MainActivity;

/**
 * Created by 현석 on 2017-06-16.
 */

public class LoginActivity extends BaseActivity{

    ImageView titleLogo;
    Button btnLocal;
    Button btnJoin;
    Button btnSignIn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
    }

    public void initView(){
        titleLogo = (ImageView)findViewById(R.id.titleLogo);
        btnLocal = (Button)findViewById(R.id.btnLocal);
        btnJoin = (Button)findViewById(R.id.btnJoin);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        LinearLayout btnBase = (LinearLayout)findViewById(R.id.btnBase);

        ImageView titleLogo = (ImageView)findViewById(R.id.titleLogo);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.slide_to_top);
        titleLogo.startAnimation(animation);

        btnBase.setVisibility(View.VISIBLE);
        Animation btnAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.btn_alpha);
        btnBase.startAnimation(btnAnimation);

        setFontToViewBold(btnLocal, btnJoin, btnSignIn);

    }

    public void initListener(){
        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
