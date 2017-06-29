package com.truemind.swingpro.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.util.RecordRecyclerAdapter;

import java.util.ArrayList;

public class MyRecordDetailActivity extends BaseActivity {

    private TextView txt1;
    private TextView txt2;
    private TextView txt_ms;
    private TextView txt_ms2;
    private TextView tv_avg;
    private TextView tv_max;
    private RecyclerView recyclerView;
    private ArrayList<Integer> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record_detail);
        initView();
        initSlideMenu("내 기록");
        bindData();
        addData();
    }

    public void bindData() {
        if (Constants.BEST_SCORE == 999999999) {
            txt_ms.setText("NR");
            txt_ms2.setText("NR");
        } else {
            txt_ms.setText(Float.toString(1 / Constants.BEST_SCORE * 1000));
            Log.d("MyTag", "BEST_SCORE: " + Float.toString(1 / Constants.BEST_SCORE * 1000));
            txt_ms2.setText(Float.toString(1 / Constants.AVG_SCORE * 1000));
            Log.d("MyTag", "AVG_SCORE: " + Float.toString(1 / Constants.AVG_SCORE * 1000));
        }
    }

    private void addData() {
        recyclerView.setAdapter(new RecordRecyclerAdapter(getContext(), Constants.LIST_AVG, R.layout.listitem_record));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initView() {
        txt1 = (TextView) findViewById(R.id.txt25);
        txt2 = (TextView) findViewById(R.id.txt26);
        TextView txtPreRec = (TextView) findViewById(R.id.txtPreRec);
        txt_ms = (TextView) findViewById(R.id.txt_ms);
        txt_ms2 = (TextView) findViewById(R.id.txt_ms2);
        tv_avg = (TextView) findViewById(R.id.record_avg_tv);
        tv_max = (TextView) findViewById(R.id.record_max_tv);
        recyclerView = (RecyclerView) findViewById(R.id.record_recyclerView);

        setFontToViewBold(txt1, txt2, txt_ms, txt_ms2, tv_avg, tv_max, txtPreRec);
    }

    @Override
    public void onBack() {
        finish();
    }
}
