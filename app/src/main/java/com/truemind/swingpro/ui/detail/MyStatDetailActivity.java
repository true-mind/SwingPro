package com.truemind.swingpro.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.R;
import com.truemind.swingpro.util.LineGraph;

/**
 * Created by 현석 on 2017-06-19.
 */

public class MyStatDetailActivity extends BaseActivity {

    LinearLayout btnPrevious;
    LinearLayout btnNext;
    LinearLayout graph;

    TextView statMessage;

    LineGraph lineGraph;

    private int position_now = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_detail);

        initSlideMenu("통계");
        initView();
        initListener();

    }

    public void initView(){

        TextView txtPrevious = (TextView)findViewById(R.id.txtPrevious);
        TextView txtNext = (TextView)findViewById(R.id.txtNext);
        btnPrevious = (LinearLayout)findViewById(R.id.btnPrevious);
        btnNext = (LinearLayout)findViewById(R.id.btnNext);

        TextView para1 = (TextView)findViewById(R.id.para1);
        TextView paraMessage = (TextView)findViewById(R.id.paraMessage);

        statMessage = (TextView)findViewById(R.id.statMessage);

        TextView tableRow1 = (TextView)findViewById(R.id.tableRow1);
        TextView tableRow2 = (TextView)findViewById(R.id.tableRow2);
        TextView tableRow3 = (TextView)findViewById(R.id.tableRow3);
        TextView tableRow4 = (TextView)findViewById(R.id.tableRow4);
        graph = (LinearLayout)findViewById(R.id.graph);

        lineGraph = new LineGraph(getContext());
        graph.addView(lineGraph);

        Animation slide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_bottom);
        graph.startAnimation(slide);

        setFontToViewBold(txtPrevious, txtNext, para1, paraMessage, statMessage,
        tableRow1, tableRow2, tableRow3, tableRow4);

    }

    public void initListener(){

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Previous", Toast.LENGTH_SHORT).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
