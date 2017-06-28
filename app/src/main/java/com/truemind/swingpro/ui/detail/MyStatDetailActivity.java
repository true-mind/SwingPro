package com.truemind.swingpro.ui.detail;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.R;
import com.truemind.swingpro.graph_util.GraphCall;
import com.truemind.swingpro.util.ProgressDialog;

/**
 * Created by 현석 on 2017-06-19.
 */

public class MyStatDetailActivity extends BaseActivity {

    private static final int LIST_SIZE_BIGGER_THAN_16 = 0;
    private static final int LIST_SIZE_SMALLER_THAN_16 = 1;
    private static final int LIST_SIZE_ZERO = 2;

    LinearLayout btnPrevious;
    LinearLayout btnNext;
    LinearLayout graph_detail;

    TextView statMessage;

    GraphCall graphCall;

    private int position_now = 0;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_detail);

        initSlideMenu("통계");
        initView();
        initListener();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("MyTag", "List_Avg.Size: " + Integer.toString(Constants.LIST_AVG.size()));
                if (Constants.LIST_AVG.size() < 1) {
                    threadhandler.sendEmptyMessage(LIST_SIZE_ZERO);
                } else if (Constants.LIST_AVG.size() < 16) {
                    for (int i = 0; i < Constants.LIST_AVG.size(); i++) {
                        Constants.LIST_FOR_GRAPH = Constants.LIST_AVG;
                    }
                    graph_detail.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onGlobalLayout() {
                            graph_detail.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            graphCall = new GraphCall(getContext(), graph_detail, Constants.GRAPH_MAX_COUNT, Constants.LIST_FOR_GRAPH);
                            graphCall.setGraph();
                        }
                    });

                    threadhandler.sendEmptyMessage(LIST_SIZE_SMALLER_THAN_16);
                } else {
                    for (int i = Constants.LIST_AVG.size() - Constants.GRAPH_MAX_COUNT;
                         i < Constants.LIST_AVG.size(); i++) {
                        Constants.LIST_FOR_GRAPH = Constants.LIST_AVG;
                    }
                    graph_detail.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onGlobalLayout() {
                            graph_detail.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            graphCall = new GraphCall(getContext(), graph_detail, Constants.GRAPH_MAX_COUNT, Constants.LIST_FOR_GRAPH);
                            graphCall.setGraph();
                        }
                    });
                    threadhandler.sendEmptyMessage(LIST_SIZE_BIGGER_THAN_16);
                }
            }
        });
    }

    /**
     * LineGraph를 통해 막대그래프를 추가할 때, constants에 있는 arrayList를 가지고 들어감.
     * 따라서 lineGraph의 domain에선 arrayList가 사용되어야 하며, 해당 그래프의 최대 출력 개수는
     * constants의 Max_count가 사용된다.
     */
    private Handler threadhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST_SIZE_BIGGER_THAN_16:
                    graphCall.showGraph();
                    break;
                case LIST_SIZE_SMALLER_THAN_16:
                    graphCall.showGraph();
                    break;
                case LIST_SIZE_ZERO:
                    break;
            }
            initListener();
            progressDialog.dismiss();
        }
    };
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
        graph_detail = (LinearLayout)findViewById(R.id.graph_detail);

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
