package com.truemind.swingpro.ui.intro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.truemind.swingpro.base.BaseFragment;
import com.truemind.swingpro.R;
import com.truemind.swingpro.ui.main.MainActivity;
import com.truemind.swingpro.util.CommonDialog;

/**
 * Created by 현석 on 2017-06-19.
 */

public class LoginFragment extends BaseFragment {

    FrameLayout layout;
    ImageView titleLogo;
    Button btnLocal;
    Button btnJoin;
    Button btnSignIn;

    public LoginFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (FrameLayout) inflater.inflate(R.layout.frag_login, container, false);
        initView();
        initListener();
        return layout;
    }

    public void initView() {


        titleLogo = (ImageView) layout.findViewById(R.id.titleLogo);
        btnLocal = (Button) layout.findViewById(R.id.btnLocal);
        btnJoin = (Button) layout.findViewById(R.id.btnJoin);
        btnSignIn = (Button) layout.findViewById(R.id.btnSignIn);
        LinearLayout btnBase = (LinearLayout) layout.findViewById(R.id.btnBase);

        ImageView titleLogo = (ImageView) layout.findViewById(R.id.titleLogo);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_top);
        titleLogo.startAnimation(animation);

        btnBase.setVisibility(View.VISIBLE);
        Animation btnAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_alpha);
        btnBase.startAnimation(btnAnimation);

        setFontToViewBold(getActivity(), btnLocal, btnJoin, btnSignIn);

    }


    public void initListener() {
        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(getActivity(), "지금은 로컬에서만 시작 가능합니다.");
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(getActivity(), "지금은 로컬에서만 시작 가능합니다.");
            }
        });
    }

}
