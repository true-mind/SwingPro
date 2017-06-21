package com.truemind.swingpro.ui.main;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.swingpro.BaseFragment;
import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.util.LineGraph;

/**
 * Created by 현석 on 2017-06-15.
 */

public class MyRecordFragment extends BaseFragment {

    LinearLayout layout;
    TextView speedAvg;
    TextView speedFast;
    TextView statMessage;
    LineGraph lineGraph;
    LinearLayout graph;

    public static final int GRAPH_DOMAIN_MAX = 16;

    public MyRecordFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.frag_my_record, container, false);
        initView();
        initListener();
        return layout;
    }

    public void initView(){

        graph = (LinearLayout)layout.findViewById(R.id.graph);

        TextView titleStat = (TextView)layout.findViewById(R.id.titleStat);
        TextView titleRec = (TextView)layout.findViewById(R.id.titleRecord);

        TextView para1 = (TextView)layout.findViewById(R.id.para1);
        TextView paraMessage = (TextView)layout.findViewById(R.id.paraMessage);

        TextView txtDetail1 = (TextView)layout.findViewById(R.id.txtDetail1);
        TextView txtDetail2 = (TextView)layout.findViewById(R.id.txtDetail2);

        TextView txtSpeedFast = (TextView)layout.findViewById(R.id.txtSpeedFast);
        TextView txtSpeedAvg = (TextView)layout.findViewById(R.id.txtSpeedAvg);

        statMessage = (TextView)layout.findViewById(R.id.statMessage);

        speedFast = (TextView)layout.findViewById(R.id.speedFast);
        speedAvg = (TextView)layout.findViewById(R.id.speedAvg);

        TextView ms1 = (TextView)layout.findViewById(R.id.ms1);
        TextView ms2 = (TextView)layout.findViewById(R.id.ms2);
        setFontToViewBold(getActivity(), titleStat, titleRec, txtDetail1, txtDetail2, txtSpeedFast, txtSpeedAvg, para1, paraMessage,
        speedFast, speedAvg, ms1, ms2, statMessage);

        graph.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        graph.getMeasuredWidth();
        graph.getMeasuredHeight();

        lineGraph = new LineGraph(getActivity());
        graph.addView(lineGraph);

        Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_bottom);
        graph.startAnimation(slide);
    }

    public void initListener(){


    }




}
