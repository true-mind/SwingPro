package com.truemind.swingpro.ui.test_tempo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.truemind.swingpro.R;
import com.truemind.swingpro.base.BaseActivity;

import java.util.ArrayList;

public class TempoResultActivity extends BaseActivity {

    private ArrayList<String> results;
    private TextView txtBpm;
    private TextView txt1;
    private TextView txt2;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_result);

        initView();



    }

    private void initView() {
        results = getIntent().getExtras().getStringArrayList("results");
        txtBpm = (TextView) findViewById(R.id.txtBpmRst);
        txt1 = (TextView) findViewById(R.id.txt20);
        txt2 = (TextView) findViewById(R.id.txt21);
        listView = (ListView) findViewById(R.id.tempo_result_listview);

        setFontToViewBold(txtBpm, txt1, txt2);
    }
}
