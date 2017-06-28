package com.truemind.swingpro.ui.test_tempo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.base.BaseDispatchKey;
import com.truemind.swingpro.util.AdapterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 현석 on 2017-06-21.
 */

public class TestTempoActivity extends BaseDispatchKey{

    private ImageView imgDot;
    private Spinner spinner;
    private TextView txtBpm;
    private TextView txtDistance;
    private ToggleButton toggle;
    private Button btnTempo;
    private SpinnerAdapter spinnerAdapter;

    private List<String> keyValues;
    private ArrayList<String> results;

    private long myBaseTime;
    private long timeInterval;
    private float distance;
    private boolean reverse;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tempo);

        initView();
        initSlideMenu("Test");
        initListener();
    }

    private void initView() {
        results = new ArrayList<>();
        keyValues = new ArrayList<>();
        for(int i = 10; i<110; i = i+10){
            keyValues.add(Integer.toString(i));
        }

        imgDot = (ImageView) findViewById(R.id.imgDot);
        spinner = (Spinner) findViewById(R.id.spinnerTempo);
        txtBpm = (TextView) findViewById(R.id.txtBpm);
        txtDistance = (TextView) findViewById(R.id.txtDistance);
        toggle = (ToggleButton) findViewById(R.id.btnTempoToggle);
        btnTempo = (Button) findViewById(R.id.btnTempo);
        btnTempo.setClickable(false);

        setFontToViewBold(toggle, btnTempo, txtBpm, txtDistance);

        spinnerAdapter = new AdapterSpinner(getContext(), keyValues);

        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(Constants.TEST_TEMPO_BPM_KEY-1);

        distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 305, getResources().getDisplayMetrics());
    }

    public void initListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.TEST_TEMPO_BPM_KEY = (position+1);
                Constants.TEST_TEMPO_BPM = Integer.parseInt(keyValues.get(position));
                if(toggle.isChecked()){
                    toggle.performClick();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggle.isChecked()){
                    startMetronome();
                }else{
                    endMetronome();
                }
            }
        });

        btnTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long curTime;
                long distance;
                String txt = "";
                curTime = SystemClock.elapsedRealtime();
                distance = curTime-myBaseTime;
                if(distance>0){
                    if(distance>timeInterval/2){
                        txt = "-"+String.valueOf(timeInterval-distance)+"ms";
                    }else{
                        txt = "+"+String.valueOf(distance)+"ms";
                    }
                }else if(distance==0){
                    txt = "0ms";
                }
                txtDistance.setText(txt);
                results.add(txt);
            }
        });
    }

    private void endMetronome() {
        imgDot.animate().cancel();
        imgDot.setX(0);
        txtDistance.setText("");

        handler.removeMessages(0);
        handler = null;
        btnTempo.setClickable(false);

        Intent intent = new Intent(TestTempoActivity.this, TempoResultActivity.class);
        intent.putStringArrayListExtra("results", results);
        startActivity(intent);
        finish();
    }

    private void startMetronome() {
        timeInterval = 60000 / Constants.TEST_TEMPO_BPM;
        myBaseTime = SystemClock.elapsedRealtime();
        reverse = false;

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(!reverse){
                    Log.d("MyTag", "x:"+imgDot.getX());
                    imgDot.animate().translationX(distance).setDuration(timeInterval).start(); //+305
                }else{
                    imgDot.animate().translationX(0).setDuration(timeInterval).start();
                }
                reverse = !reverse;
                handler.sendEmptyMessageDelayed(0, timeInterval);
                myBaseTime = SystemClock.elapsedRealtime();
            }
        };

        handler.sendEmptyMessage(0);
        btnTempo.setClickable(true);
    }

    @Override
    public void onkey1() {
        btnTempo.performClick();
    }

    @Override
    public void onkey2() {

    }

    @Override
    public void onkey3() {

    }

    @Override
    public void onkey4() {

    }

    @Override
    public void onkey5() {

    }

    @Override
    public void onkey6() {

    }

    @Override
    public void onkey7() {

    }

    @Override
    public void onkey8() {

    }

    @Override
    public void onkey9() {

    }

    @Override
    public void onkey10() {

    }

    @Override
    public void onKeyBack() {
        handler.removeMessages(0);
        handler = null;
        finish();
    }
}
