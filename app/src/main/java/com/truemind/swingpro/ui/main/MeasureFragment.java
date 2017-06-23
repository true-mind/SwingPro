package com.truemind.swingpro.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.swingpro.base.BaseFragment;
import com.truemind.swingpro.R;
import com.truemind.swingpro.ui.test_seq.TestSeqActivity;

/**
 * Created by 현석 on 2017-06-15.
 */

public class MeasureFragment extends BaseFragment{

    LinearLayout layout;
    LinearLayout btn1;
    LinearLayout btn2;

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
        initListener();
        return layout;
    }

    public void initView(){
        TextView testSeqTitle = (TextView)layout.findViewById(R.id.testSeqTitle);
        TextView testTempoTitle = (TextView)layout.findViewById(R.id.testTempoTitle);
        TextView testSeqDesc = (TextView)layout.findViewById(R.id.testSeqDesc);
        TextView testTempoDesc = (TextView)layout.findViewById(R.id.testTempoDesc);

        btn1 = (LinearLayout)layout.findViewById(R.id.btn1);
        btn2 = (LinearLayout)layout.findViewById(R.id.btn2);
        setFontToViewBold(getActivity(), testSeqTitle, testTempoTitle, testSeqDesc, testTempoDesc);
    }

    public void initListener(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestSeqActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
