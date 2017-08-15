package com.truemind.swingpro.ui.test_tempo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.ui.main.MainActivity;
import com.truemind.swingpro.util.TempoRecyclerAdapter;

import java.util.ArrayList;

public class TempoResultActivity extends BaseActivity {

    private ArrayList<String> results;
    private TextView txtBpm;
    private TextView txt1;
    private TextView txt2;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_result);

        initView();
        initSlideMenu("Test");
        addData();
    }

    private void addData() {
        recyclerView.setAdapter(new TempoRecyclerAdapter(getContext(), results, R.layout.listitem_tempo));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initView() {
        results = getIntent().getExtras().getStringArrayList("results");
        txtBpm = (TextView) findViewById(R.id.txtBpmRst);
        txt1 = (TextView) findViewById(R.id.txt20);
        txt2 = (TextView) findViewById(R.id.txt21);
        recyclerView = (RecyclerView) findViewById(R.id.tempo_result_recyclerView);

        setFontToViewBold(txtBpm, txt1, txt2);
    }

    @Override
    public void onBack() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        Constants.TAB_POSITION = 1;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
