package com.truemind.swingpro.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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
    private ArrayList<Float> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record_detail);
        initView();
        initSlideMenu("내 기록");
        addData();
    }

    private void addData() {
        // TODO: 2017. 6. 29. results에 이전 기록 넣기
        recyclerView.setAdapter(new RecordRecyclerAdapter(getContext(), results, R.layout.listitem_record));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initView() {
        txt1 = (TextView) findViewById(R.id.txt25);
        txt2 = (TextView) findViewById(R.id.txt26);
        txt_ms = (TextView) findViewById(R.id.txt_ms);
        txt_ms2 = (TextView) findViewById(R.id.txt_ms2);
        tv_avg = (TextView) findViewById(R.id.record_avg_tv);
        tv_max = (TextView) findViewById(R.id.record_max_tv);
        recyclerView = (RecyclerView) findViewById(R.id.record_recyclerView);

        setFontToViewBold(txt1, txt2, txt_ms, txt_ms2, tv_avg, tv_max);
    }

    @Override
    public void onBack() {
        finish();
    }
}
