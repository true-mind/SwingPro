package com.truemind.swingpro.ui.test_seq;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.ui.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by 현석 on 2017-06-26.
 */

public class TestSeqResult extends BaseActivity {

    ArrayList<String> dataClone = new ArrayList<>();
    Button btnBack;

    TextView txtBtn1Time1;
    TextView txtBtn1Time2;
    TextView txtBtn1Time3;
    TextView txtBtn1Time4;

    TextView txtBtn2Time1;
    TextView txtBtn2Time2;
    TextView txtBtn2Time3;
    TextView txtBtn2Time4;

    TextView txtBtn3Time1;
    TextView txtBtn3Time2;
    TextView txtBtn3Time3;
    TextView txtBtn3Time4;

    TextView txtBtn4Time1;
    TextView txtBtn4Time2;
    TextView txtBtn4Time3;
    TextView txtBtn4Time4;

    TextView txtTotalTime1;
    TextView txtTotalTime2;
    TextView txtTotalTime3;
    TextView txtTotalTime4;
    TextView txtAvgTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_seq_result);
        super.onCreate(savedInstanceState);

        initView();
        initSlideMenu("Test");
        initListener();
    }

    public void initView() {
        dataClone = getIntent().getStringArrayListExtra("data");

        TextView txtTitle1 = (TextView) findViewById(R.id.txtTitle1);
        TextView txtTitle2 = (TextView) findViewById(R.id.txtTitle2);
        TextView txt1 = (TextView) findViewById(R.id.txt1);
        TextView txt2 = (TextView) findViewById(R.id.txt2);
        TextView txt3 = (TextView) findViewById(R.id.txt3);
        TextView txt4 = (TextView) findViewById(R.id.txt4);
        TextView txt5 = (TextView) findViewById(R.id.txt5);
        TextView txt6 = (TextView) findViewById(R.id.txt6);
        TextView txt7 = (TextView) findViewById(R.id.txt7);
        TextView txt8 = (TextView) findViewById(R.id.txt8);
        TextView txt9 = (TextView) findViewById(R.id.txt9);
        TextView txt10 = (TextView) findViewById(R.id.txt10);
        TextView txt11 = (TextView) findViewById(R.id.txt11);
        TextView txt12 = (TextView) findViewById(R.id.txt12);
        TextView txt13 = (TextView) findViewById(R.id.txt13);
        TextView txt14 = (TextView) findViewById(R.id.txt14);
        TextView txt15 = (TextView) findViewById(R.id.txt15);
        TextView txt16 = (TextView) findViewById(R.id.txt16);

        txtBtn1Time1 = (TextView) findViewById(R.id.txtBtn1Time1);
        txtBtn1Time2 = (TextView) findViewById(R.id.txtBtn1Time2);
        txtBtn1Time3 = (TextView) findViewById(R.id.txtBtn1Time3);
        txtBtn1Time4 = (TextView) findViewById(R.id.txtBtn1Time4);

        txtBtn2Time1 = (TextView) findViewById(R.id.txtBtn2Time1);
        txtBtn2Time2 = (TextView) findViewById(R.id.txtBtn2Time2);
        txtBtn2Time3 = (TextView) findViewById(R.id.txtBtn2Time3);
        txtBtn2Time4 = (TextView) findViewById(R.id.txtBtn2Time4);

        txtBtn3Time1 = (TextView) findViewById(R.id.txtBtn3Time1);
        txtBtn3Time2 = (TextView) findViewById(R.id.txtBtn3Time2);
        txtBtn3Time3 = (TextView) findViewById(R.id.txtBtn3Time3);
        txtBtn3Time4 = (TextView) findViewById(R.id.txtBtn3Time4);

        txtBtn4Time1 = (TextView) findViewById(R.id.txtBtn4Time1);
        txtBtn4Time2 = (TextView) findViewById(R.id.txtBtn4Time2);
        txtBtn4Time3 = (TextView) findViewById(R.id.txtBtn4Time3);
        txtBtn4Time4 = (TextView) findViewById(R.id.txtBtn4Time4);

        txtTotalTime1 = (TextView) findViewById(R.id.txtTotalTime1);
        txtTotalTime2 = (TextView) findViewById(R.id.txtTotalTime2);
        txtTotalTime3 = (TextView) findViewById(R.id.txtTotalTime3);
        txtTotalTime4 = (TextView) findViewById(R.id.txtTotalTime4);
        txtAvgTime = (TextView) findViewById(R.id.txtAvgTime);

        btnBack = (Button) findViewById(R.id.btnBack);

        setFontToViewBold(txtTitle1, txtTitle2, txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9
                , txt10, txt11, txt12, txt13, txt14, txt15, txt16, txtBtn1Time1, txtBtn1Time2, txtBtn1Time3, txtBtn1Time4
                , txtBtn2Time1, txtBtn2Time2, txtBtn2Time3, txtBtn2Time4, txtBtn3Time1, txtBtn3Time2, txtBtn3Time3, txtBtn3Time4
                , txtBtn4Time1, txtBtn4Time2, txtBtn4Time3, txtBtn4Time4, txtTotalTime1, txtTotalTime2, txtTotalTime3, txtTotalTime4
                , txtAvgTime, btnBack);

        dataBind();
    }

    public void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    public void dataBind() {
        txtBtn1Time1.setText(dataClone.get(0));
        txtBtn1Time2.setText(dataClone.get(1));
        txtBtn1Time3.setText(dataClone.get(2));
        txtBtn1Time4.setText(dataClone.get(3));
        txtTotalTime1.setText(dataClone.get(4));

        txtBtn2Time1.setText(dataClone.get(5));
        txtBtn2Time2.setText(dataClone.get(6));
        txtBtn2Time3.setText(dataClone.get(7));
        txtBtn2Time4.setText(dataClone.get(8));
        txtTotalTime2.setText(dataClone.get(9));

        txtBtn3Time1.setText(dataClone.get(10));
        txtBtn3Time2.setText(dataClone.get(11));
        txtBtn3Time3.setText(dataClone.get(12));
        txtBtn3Time4.setText(dataClone.get(13));
        txtTotalTime3.setText(dataClone.get(14));

        txtBtn4Time1.setText(dataClone.get(15));
        txtBtn4Time2.setText(dataClone.get(16));
        txtBtn4Time3.setText(dataClone.get(17));
        txtBtn4Time4.setText(dataClone.get(18));
        txtTotalTime4.setText(dataClone.get(19));

        String dataIntoConstants = dataClone.get(20);
        txtAvgTime.setText(dataIntoConstants);
        float sumAll = (Constants.AVG_SCORE * Constants.LIST_AVG.size()) + Integer.parseInt(dataIntoConstants);
        Constants.LIST_AVG.add(Integer.parseInt(dataIntoConstants));
        Constants.AVG_SCORE = sumAll / Constants.LIST_AVG.size();
        if (Constants.START_DATE.length() < 3) {
            long today = System.currentTimeMillis();
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
            Constants.START_DATE = date.format(today);
        }

        saveMyPreferences(getMyPreferencesSize(),
                Integer.parseInt(dataIntoConstants), getMyPreferencesSize() + 1);

    }

    public void goBack() {
        startActivity(new Intent(getContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onBack() {
        goBack();
    }
}
