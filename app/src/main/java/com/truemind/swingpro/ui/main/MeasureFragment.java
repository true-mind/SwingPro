package com.truemind.swingpro.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.swingpro.BaseFragment;
import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-15.
 */

public class MeasureFragment extends BaseFragment{

    LinearLayout layout;

    public MeasureFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.frag_measure, container, false);
        initView();
        return layout;
    }

    public void initView(){
        TextView testSeqTitle = (TextView)layout.findViewById(R.id.testSeqTitle);
        TextView testTempoTitle = (TextView)layout.findViewById(R.id.testTempoTitle);
        TextView testSeqDesc = (TextView)layout.findViewById(R.id.testSeqDesc);
        TextView testTempoDesc = (TextView)layout.findViewById(R.id.testTempoDesc);
        setFontToViewBold(getActivity(), testSeqTitle, testTempoTitle, testSeqDesc, testTempoDesc);
    }
}
