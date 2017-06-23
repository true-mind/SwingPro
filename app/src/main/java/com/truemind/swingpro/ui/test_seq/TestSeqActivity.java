package com.truemind.swingpro.ui.test_seq;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.R;
import com.truemind.swingpro.base.BaseDispatchKey;

/**
 * Created by 현석 on 2017-06-21.
 */

public class TestSeqActivity extends BaseDispatchKey {

    private final static int TIMER_INIT = 990;
    private final static int TIMER_RUN = 991;
    private final static int TIMER_STOP = 992;

    private final static int BUTTON1 = 1;
    private final static int BUTTON2 = 2;
    private final static int BUTTON3 = 3;
    private final static int BUTTON4 = 4;

    public int CURRENT_SEQUENCE = BUTTON1;

    public int CURRENT_COUNT = 1;

    public int cur_Status = TIMER_INIT;
    public long myBaseTime;

    TextView txtCurSeq;
    TextView txtTotalTime;
    TextView txtStart;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    TextView txtBtn1Time;
    TextView txtBtn2Time;
    TextView txtBtn3Time;
    TextView txtBtn4Time;

    TextView txtSeq1;
    TextView txtSeq2;
    TextView txtSeq3;
    TextView txtSeq4;
    TextView txtSeqAvg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_seq);

        initView();
        initSlideMenu("Test");
        initListener();

    }

    public void initView() {
        txtCurSeq = (TextView) findViewById(R.id.txtCurSeq);
        txtTotalTime = (TextView) findViewById(R.id.txtTotalTime);
        txtStart = (TextView) findViewById(R.id.txtStart);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn1.setEnabled(true);

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
        TextView txt17 = (TextView) findViewById(R.id.txt17);
        TextView txt18 = (TextView) findViewById(R.id.txt18);

        txtBtn1Time = (TextView) findViewById(R.id.txtBtn1Time);
        txtBtn2Time = (TextView) findViewById(R.id.txtBtn2Time);
        txtBtn3Time = (TextView) findViewById(R.id.txtBtn3Time);
        txtBtn4Time = (TextView) findViewById(R.id.txtBtn4Time);

        txtSeq1 = (TextView) findViewById(R.id.txtSeq1);
        txtSeq2 = (TextView) findViewById(R.id.txtSeq2);
        txtSeq3 = (TextView) findViewById(R.id.txtSeq3);
        txtSeq4 = (TextView) findViewById(R.id.txtSeq4);
        txtSeqAvg = (TextView) findViewById(R.id.txtSeqAvg);

        setFontToViewBold(txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10, txt11, txt12,
                txt13, txt14, txt15, txt16, txt17, txt18, txtCurSeq, txtTotalTime, txtStart, btn1, btn2,
                btn3, btn4, txtSeq1, txtSeq2, txtSeq3, txtSeq4, txtSeqAvg);

    }

    public void initListener() {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CURRENT_SEQUENCE == BUTTON1) {
                    myBaseTime = SystemClock.elapsedRealtime();
                    myTimer.sendEmptyMessage(0);
                    cur_Status = TIMER_RUN;
                    CURRENT_SEQUENCE = BUTTON2;
                    txtStart.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getContext(), "버튼 순서가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CURRENT_SEQUENCE == BUTTON2) {
                    txtBtn2Time.setText(getTimeOut());
                    cur_Status = TIMER_RUN;
                    CURRENT_SEQUENCE = BUTTON3;
                } else {
                    Toast.makeText(getContext(), "버튼 순서가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CURRENT_SEQUENCE == BUTTON3) {
                    txtBtn3Time.setText(getTimeOut());
                    cur_Status = TIMER_RUN;
                    CURRENT_SEQUENCE = BUTTON4;
                } else {
                    Toast.makeText(getContext(), "버튼 순서가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CURRENT_SEQUENCE == BUTTON4) {
                    txtBtn4Time.setText(getTimeOut());
                    cur_Status = TIMER_STOP;
                    myTimer.removeMessages(0);
                    myTimer.sendEmptyMessage(1);
                } else {
                    Toast.makeText(getContext(), "버튼 순서가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    Handler myTimer = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    txtTotalTime.setText(getTimeOut());
                    myTimer.sendEmptyMessage(0);
                    break;
                case 1:
                    myBaseTime = 0;
                    CURRENT_SEQUENCE = BUTTON1;
                    switch (CURRENT_COUNT) {
                        case 1:
                            txtSeq1.setText(txtTotalTime.getText().toString());
                            CURRENT_COUNT++;
                            txtStart.setVisibility(View.VISIBLE);
                            txtSeqAvg.setText(MathAvg(Integer.parseInt(txtTotalTime.getText().toString())));
                            break;
                        case 2:
                            txtSeq2.setText(txtTotalTime.getText().toString());
                            CURRENT_COUNT++;
                            txtStart.setVisibility(View.VISIBLE);
                            txtSeqAvg.setText(MathAvg(Integer.parseInt(txtSeq1.getText().toString())
                                    , Integer.parseInt(txtSeq2.getText().toString())));
                            break;
                        case 3:
                            txtSeq3.setText(txtTotalTime.getText().toString());
                            CURRENT_COUNT++;
                            txtStart.setVisibility(View.VISIBLE);
                            txtSeqAvg.setText(MathAvg(Integer.parseInt(txtSeq1.getText().toString())
                                    , Integer.parseInt(txtSeq2.getText().toString())
                                    , Integer.parseInt(txtSeq3.getText().toString())));
                            break;
                        case 4:
                            txtSeq4.setText(txtTotalTime.getText().toString());
                            CURRENT_COUNT++;
                            txtSeqAvg.setText(MathAvg(Integer.parseInt(txtSeq1.getText().toString())
                                    , Integer.parseInt(txtSeq2.getText().toString())
                                    , Integer.parseInt(txtSeq3.getText().toString())
                                    , Integer.parseInt(txtSeq4.getText().toString())));
                            break;
                    }
                    if (CURRENT_COUNT < 5) {
                        txtCurSeq.setText(Integer.toString(CURRENT_COUNT));
                    } else {
                        CURRENT_COUNT = 4;
                        btn1.setEnabled(false);
                        btn2.setEnabled(false);
                        btn3.setEnabled(false);
                        btn4.setEnabled(false);
                    }
            }

        }
    };

    public String MathAvg(int... times) {
        int timesAdd = 0;
        for (int i = 0; i < times.length; i++) {
            timesAdd += times[i];
        }
        return Integer.toString(timesAdd / times.length);
    }

    private String getTimeOut() {
        long now = SystemClock.elapsedRealtime();
        long outTime = now - myBaseTime;
        return Long.toString(outTime);
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onkey1() {
        btn1.performClick();
    }

    @Override
    public void onkey2() {
        btn2.performClick();
    }

    @Override
    public void onkey3() {
        btn3.performClick();
    }

    @Override
    public void onkey4() {
        btn4.performClick();
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
        finish();
    }
}
