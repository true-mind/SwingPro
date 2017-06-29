package com.truemind.swingpro.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.base.BaseFragment;
import com.truemind.swingpro.R;
import com.truemind.swingpro.graph_util.GraphCall;
import com.truemind.swingpro.ui.account.MyAccountSetting;
import com.truemind.swingpro.ui.detail.MyStatDetailActivity;
import com.truemind.swingpro.ui.message.MessageActivity;
import com.truemind.swingpro.ui.notice.NoticeActivity;
import com.truemind.swingpro.graph_util.LineGraph;
import com.truemind.swingpro.util.ProgressDialog;

/**
 * Created by 현석 on 2017-06-15.
 */

public class MyRecordFragment extends BaseFragment {

    private static final int LIST_SIZE_BIGGER_THAN_16 = 0;
    private static final int LIST_SIZE_SMALLER_THAN_16 = 1;
    private static final int LIST_SIZE_ZERO = 2;

    LinearLayout layout;
    TextView speedAvg;
    TextView speedFast;
    TextView statMessage;
    GraphCall graphCall;
    LinearLayout graph;

    TextView txtDetail1;
    TextView txtDetail2;

    LinearLayout btnNotice;
    LinearLayout btnMessage;
    LinearLayout btnAccount;

    TextView noticeAlert;
    TextView messageAlert;
    TextView txtNoData;

    ProgressDialog progressDialog;
    ImageView img_ad;
    boolean isNotice = false;

    public MyRecordFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.frag_my_record, container, false);
        initView();
        initFooter(getActivity(), layout);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
/*
        Constants.LIST_AVG.add(6234);
        Constants.LIST_AVG.add(2334);
        Constants.LIST_AVG.add(14234);
        Constants.LIST_AVG.add(1254);
        Constants.LIST_AVG.add(3424);
        Constants.LIST_AVG.add(5624);
        Constants.LIST_AVG.add(1324);
        Constants.LIST_AVG.add(2234);
        Constants.LIST_AVG.add(3234);
        Constants.LIST_AVG.add(2334);
        Constants.LIST_AVG.add(3434);
        Constants.LIST_AVG.add(1534);
        Constants.LIST_AVG.add(16634);
        Constants.LIST_AVG.add(4534);
        Constants.LIST_AVG.add(2334);
        Constants.LIST_AVG.add(5234);
        Constants.LIST_AVG.add(7734);
        Constants.LIST_AVG.add(5334);
        Constants.LIST_AVG.add(6634);
        Constants.LIST_AVG.add(8834);
        Constants.LIST_AVG.add(2534);
        Constants.LIST_AVG.add(7534);
        Constants.LIST_AVG.add(2334);
        Constants.LIST_AVG.add(5534);
        Constants.LIST_AVG.add(4234);
        Constants.LIST_AVG.add(6434);
        Constants.LIST_AVG.add(6734);
*/

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("MyTag", "List_Avg.Size: " + Integer.toString(Constants.LIST_AVG.size()));
                if (Constants.LIST_AVG.size() < 1) {
                    threadhandler.sendEmptyMessage(LIST_SIZE_ZERO);
                } else if (Constants.LIST_AVG.size() < 16) {
                    for (int i = 0; i < Constants.LIST_AVG.size(); i++) {
                        Constants.LIST_FOR_GRAPH = Constants.LIST_AVG;
                    }
                    txtNoData.setVisibility(View.INVISIBLE);
                    graph.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            graph.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            graphCall = new GraphCall(getActivity(), graph, Constants.GRAPH_MAX_COUNT, Constants.LIST_FOR_GRAPH);
                            graphCall.setGraph();
                        }
                    });

                    threadhandler.sendEmptyMessage(LIST_SIZE_SMALLER_THAN_16);
                } else {
                    Constants.LIST_FOR_GRAPH = Constants.LIST_AVG.subList((Constants.LIST_AVG.size() - Constants.GRAPH_MAX_COUNT), Constants.LIST_AVG.size()-1);
                    txtNoData.setVisibility(View.INVISIBLE);
                    graph.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            graph.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            graphCall = new GraphCall(getActivity(), graph, Constants.GRAPH_MAX_COUNT, Constants.LIST_FOR_GRAPH);
                            graphCall.setGraph();
                        }
                    });
                    threadhandler.sendEmptyMessage(LIST_SIZE_BIGGER_THAN_16);
                }
            }
        });
        return layout;
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
                    txtNoData.setVisibility(View.INVISIBLE);
                    break;
                case LIST_SIZE_SMALLER_THAN_16:
                    graphCall.showGraph();
                    txtNoData.setVisibility(View.INVISIBLE);
                    break;
                case LIST_SIZE_ZERO:
                    txtNoData.setVisibility(View.VISIBLE);
                    break;
            }
            initListener();
            progressDialog.dismiss();
        }
    };

    public void initView() {

        graph = (LinearLayout) layout.findViewById(R.id.graph);

        TextView titleStat = (TextView) layout.findViewById(R.id.titleStat);
        TextView titleRec = (TextView) layout.findViewById(R.id.titleRecord);

        TextView para1 = (TextView) layout.findViewById(R.id.para1);
        TextView paraMessage = (TextView) layout.findViewById(R.id.paraMessage);

        txtDetail1 = (TextView) layout.findViewById(R.id.txtDetail1);
        txtDetail2 = (TextView) layout.findViewById(R.id.txtDetail2);

        TextView txtSpeedFast = (TextView) layout.findViewById(R.id.txtSpeedFast);
        TextView txtSpeedAvg = (TextView) layout.findViewById(R.id.txtSpeedAvg);

        statMessage = (TextView) layout.findViewById(R.id.statMessage);

        /** "내 기록" 란에서 사용됨.
         * -> Speedfast는 내 최고 속도,
         * -> SpeedAvg는 내 평균 속도에서 기록되어야 함.*/
        speedFast = (TextView) layout.findViewById(R.id.speedFast);
        speedAvg = (TextView) layout.findViewById(R.id.speedAvg);
        TextView txtCPSDesc = (TextView) layout.findViewById(R.id.txtCPSDesc);

        TextView ms1 = (TextView) layout.findViewById(R.id.ms1);
        TextView ms2 = (TextView) layout.findViewById(R.id.ms2);

        TextView btnTxtNotice = (TextView) layout.findViewById(R.id.btnTxtNotice);
        TextView btnTxtMessage = (TextView) layout.findViewById(R.id.btnTxtMessage);
        TextView btnTxtAccount = (TextView) layout.findViewById(R.id.btnTxtAccount);

        noticeAlert = (TextView) layout.findViewById(R.id.noticeAlert);
        messageAlert = (TextView) layout.findViewById(R.id.messageAlert);

        btnNotice = (LinearLayout) layout.findViewById(R.id.btnNotice);
        btnMessage = (LinearLayout) layout.findViewById(R.id.btnMessage);
        btnAccount = (LinearLayout) layout.findViewById(R.id.btnAccount);

        img_ad = (ImageView) layout.findViewById(R.id.img_ad);

        txtNoData = (TextView) layout.findViewById(R.id.txtNoData);

        setFontToViewBold(getActivity(), titleStat, titleRec, txtDetail1, txtDetail2, txtSpeedFast, txtSpeedAvg, para1, paraMessage, txtCPSDesc,
                speedFast, speedAvg, ms1, ms2, statMessage, btnTxtNotice, btnTxtMessage, btnTxtAccount, noticeAlert, messageAlert, txtNoData);

        bindData();
        alertSetter();
    }

    /**
     * 각 버튼 베이스에서 알림이 떴는지 여부를 알려주기 위함.
     * 각 알림 텍스트 뷰 들의 경우 해당 뷰 들의 visibility가 gone으로 설정되어 있으므로,
     * 만약 message 혹은 notice가 존재할 경우 isNotice를 true로 만들어주어야 한다.
     */
    public void alertSetter() {
        if (isNotice /*TODO : notice가 있을 때*/) {

            noticeAlert.setVisibility(View.VISIBLE);
            messageAlert.setVisibility(View.VISIBLE);

            noticeAlert.setText("1");
            messageAlert.setText("1");
        }
    }

    public void bindData() {
        if (Constants.BEST_SCORE == 999999999) {
            speedFast.setText("NR");
            speedAvg.setText("NR");
        } else {
            speedFast.setText(Float.toString(1 / Constants.BEST_SCORE * 1000));
            Log.d("MyTag", "BEST_SCORE: " + Float.toString(1 / Constants.BEST_SCORE * 1000));
            speedAvg.setText(Float.toString(1 / Constants.AVG_SCORE * 1000));
            Log.d("MyTag", "AVG_SCORE: " + Float.toString(1 / Constants.AVG_SCORE * 1000));
        }
    }

    public void initListener() {

        txtDetail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.LIST_AVG.size() < 1) {
                    Toast.makeText(getActivity(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), MyStatDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        txtDetail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Detail : My Record", Toast.LENGTH_SHORT).show();
            }
        });

        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyAccountSetting.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        img_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SWING_BEAT_LINK)));
            }
        });

    }


}
