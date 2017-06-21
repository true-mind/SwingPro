package com.truemind.swingpro.ui.intro;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.truemind.swingpro.base.BaseFragment;
import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-19.
 */

public class IntroFragment extends BaseFragment{

    FrameLayout layout;

    public IntroFragment(){

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (FrameLayout)inflater.inflate(R.layout.frag_intro, container, false);
        initView();
        return layout;
    }

    public void initView() {

        ImageView titleLogo = (ImageView)layout.findViewById(R.id.titleLogo);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha);
        titleLogo.startAnimation(animation);
    }


}
