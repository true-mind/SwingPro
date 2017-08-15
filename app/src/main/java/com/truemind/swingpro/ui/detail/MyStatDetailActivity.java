package com.truemind.swingpro.ui.detail;

import android.content.Intent;
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
import com.truemind.swingpro.ui.main.MainActivity;
import com.truemind.swingpro.ui.test_tempo.TestTempoActivity;
import com.truemind.swingpro.util.ProgressDialog;
import com.truemind.swingpro.util.Save;

import java.util.ArrayList;

/**
 * Created by 현석 on 2017-06-19.
 */

public class MyStatDetailActivity extends BaseActivity {

    private static final int LIST_SIZE_BIGGER_THAN_16 = 0;
    private static final int LIST_SIZE_SMALLER_THAN_16 = 1;
    private static final int LIST_SIZE_ZERO = 2;
    public static ArrayList<Integer> LIST_AVG;

    LinearLayout btnPrevious;
    LinearLayout btnNext;
    LinearLayout graph_detail;

    TextView statMessage;

    TextView value1;
    TextView value2;
    TextView value3;
    TextView value4;

    GraphCall graphCall;

    private int STATUS = 0;
    private int position_now = 0;
    private boolean canGoPrevious = false;
    private boolean canGoNext = false;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_detail);

        initSlideMenu("통계");
        LIST_AVG = new ArrayList<>();
        initView();
        
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                if (Save.countTime(getContext())<1) {
                    LIST_AVG = new ArrayList<>();
                } else {
                    String[] dataSetString = Save.dataSetRecord(getContext()).split(",");

                    for (String aDataSetString : dataSetString)
                        LIST_AVG.add(Integer.parseInt(aDataSetString));

                }
                
                Log.d("MyTag", "List_Avg.Size: " + Integer.toString(Save.countTime(getContext())));
                if (Save.countTime(getContext()) < 1) {
                    threadhandler.sendEmptyMessage(LIST_SIZE_ZERO);
                } else if (Save.countTime(getContext()) < Constants.GRAPH_MAX_COUNT) {
                    for (int i = 0; i < Save.countTime(getContext()); i++) {
                        Constants.LIST_FOR_GRAPH = LIST_AVG;
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
                    Constants.LIST_FOR_GRAPH = LIST_AVG.subList((Save.countTime(getContext()) - Constants.GRAPH_MAX_COUNT + 1), Save.countTime(getContext()));

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
            STATUS = msg.what;
            initListener();
            progressDialog.dismiss();
        }
    };

    public void initView() {

        TextView txtPrevious = (TextView) findViewById(R.id.txtPrevious);
        TextView txtNext = (TextView) findViewById(R.id.txtNext);
        btnPrevious = (LinearLayout) findViewById(R.id.btnPrevious);
        btnNext = (LinearLayout) findViewById(R.id.btnNext);

        TextView para1 = (TextView) findViewById(R.id.para1);
        TextView paraMessage = (TextView) findViewById(R.id.paraMessage);

        statMessage = (TextView) findViewById(R.id.statMessage);

        TextView tableRow1 = (TextView) findViewById(R.id.tableRow1);
        TextView tableRow2 = (TextView) findViewById(R.id.tableRow2);
        TextView tableRow3 = (TextView) findViewById(R.id.tableRow3);
        TextView tableRow4 = (TextView) findViewById(R.id.tableRow4);
        graph_detail = (LinearLayout) findViewById(R.id.graph_detail);

        value1 = (TextView) findViewById(R.id.table1Value);
        value2 = (TextView) findViewById(R.id.table2Value);
        value3 = (TextView) findViewById(R.id.table3Value);
        value4 = (TextView) findViewById(R.id.table4Value);

        setFontToViewBold(txtPrevious, txtNext, para1, paraMessage, statMessage,
                tableRow1, tableRow2, tableRow3, tableRow4);

        bindData();

    }

    public void initListener() {

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canGoPrevious && STATUS == LIST_SIZE_BIGGER_THAN_16) {
                    graph_detail.removeAllViews();

                    if (position_now - Constants.GRAPH_MAX_COUNT >= 15) {
                        position_now = (position_now - Constants.GRAPH_MAX_COUNT + 1);
                        Log.d("MyTagStat", "Now Position is " + position_now);
                        Log.d("MyTagStat", "Go Previous from " + (position_now - Constants.GRAPH_MAX_COUNT) + " to " + position_now);
                        Constants.LIST_FOR_GRAPH = LIST_AVG.subList((position_now - Constants.GRAPH_MAX_COUNT), position_now);
                    } else {
                        Log.d("MyTagStat", "Go Previous from " + 0 + " to " + position_now);
                        Constants.LIST_FOR_GRAPH = LIST_AVG.subList(0, position_now);
                        position_now = 0;
                        Log.d("MyTagStat", "Now Position is " + position_now);
                        canGoPrevious = false;
                    }
                    canGoNext = true;
                    graphCall = new GraphCall(getContext(), graph_detail, Constants.GRAPH_MAX_COUNT, Constants.LIST_FOR_GRAPH);
                    graphCall.setGraph();
                    graphCall.showGraph();
                } else {
                    Toast.makeText(getContext(), "기록이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canGoNext && STATUS == LIST_SIZE_BIGGER_THAN_16) {
                    graph_detail.removeAllViews();

                    if (position_now + Constants.GRAPH_MAX_COUNT < Save.countTime(getContext()) - Constants.GRAPH_MAX_COUNT) {
                        position_now = (position_now + Constants.GRAPH_MAX_COUNT);
                        Log.d("MyTag", "Now Position is " + position_now);
                        Log.d("MyTag", "Go Next from " + position_now + " to " + (position_now + Constants.GRAPH_MAX_COUNT));
                        Constants.LIST_FOR_GRAPH = LIST_AVG.subList(position_now, position_now + Constants.GRAPH_MAX_COUNT);
                    } else {
                        Log.d("MyTag", "Go Next from " + (Save.countTime(getContext()) - Constants.GRAPH_MAX_COUNT) + " to " + (Save.countTime(getContext())));
                        Constants.LIST_FOR_GRAPH = LIST_AVG.subList((Save.countTime(getContext()) - Constants.GRAPH_MAX_COUNT) + 1, Save.countTime(getContext()));
                        position_now = Save.countTime(getContext());
                        Log.d("MyTag", "Now Position is " + position_now);
                        canGoNext = false;
                    }
                    canGoPrevious = true;
                    graphCall = new GraphCall(getContext(), graph_detail, Constants.GRAPH_MAX_COUNT, Constants.LIST_FOR_GRAPH);
                    graphCall.setGraph();
                    graphCall.showGraph();
                } else {
                    Toast.makeText(getContext(), "기록이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void bindData() {
        value1.setText(Float.toString(Save.bestScore(getContext())));
        value2.setText(Float.toString(Save.avgScore(getContext())));
        value3.setText(Save.firstDate(getContext()));
        value4.setText(Integer.toString(Save.countTime(getContext())));

        switch (STATUS) {
            case LIST_SIZE_ZERO:
                canGoNext = false;
                canGoPrevious = false;
                break;
            case LIST_SIZE_SMALLER_THAN_16:
                canGoNext = false;
                canGoPrevious = false;
                break;
            case LIST_SIZE_BIGGER_THAN_16:
                position_now = Save.countTime(getContext());
                canGoNext = false;
                canGoPrevious = true;
                break;
        }
    }

    @Override
    public void onBack() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        Constants.TAB_POSITION = 0;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
