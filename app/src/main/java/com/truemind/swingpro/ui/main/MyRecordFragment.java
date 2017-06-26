package com.truemind.swingpro.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.base.BaseFragment;
import com.truemind.swingpro.R;
import com.truemind.swingpro.ui.detail.MyStatDetailActivity;
import com.truemind.swingpro.ui.message.MessageActivity;
import com.truemind.swingpro.ui.notice.NoticeActivity;
import com.truemind.swingpro.util.LineGraph;

/**
 * Created by 현석 on 2017-06-15.
 */

public class MyRecordFragment extends BaseFragment {

    LinearLayout layout;
    TextView speedAvg;
    TextView speedFast;
    TextView statMessage;
    LineGraph lineGraph;
    LinearLayout graph;

    TextView txtDetail1;
    TextView txtDetail2;

    LinearLayout btnNotice;
    LinearLayout btnMessage;
    LinearLayout btnAccount;

    TextView noticeAlert;
    TextView messageAlert;

    ImageView img_ad;
    boolean isNotice = true;

    public MyRecordFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.frag_my_record, container, false);
        initView();
        initFooter(getActivity(), layout);
        initListener();
        return layout;
    }

    public void initView(){

        graph = (LinearLayout)layout.findViewById(R.id.graph);

        TextView titleStat = (TextView)layout.findViewById(R.id.titleStat);
        TextView titleRec = (TextView)layout.findViewById(R.id.titleRecord);

        TextView para1 = (TextView)layout.findViewById(R.id.para1);
        TextView paraMessage = (TextView)layout.findViewById(R.id.paraMessage);

        txtDetail1 = (TextView)layout.findViewById(R.id.txtDetail1);
        txtDetail2 = (TextView)layout.findViewById(R.id.txtDetail2);

        TextView txtSpeedFast = (TextView)layout.findViewById(R.id.txtSpeedFast);
        TextView txtSpeedAvg = (TextView)layout.findViewById(R.id.txtSpeedAvg);

        statMessage = (TextView)layout.findViewById(R.id.statMessage);

        /** "내 기록" 란에서 사용됨.
         * -> Speedfast는 내 최고 속도,
         * -> SpeedAvg는 내 평균 속도에서 기록되어야 함.*/
        speedFast = (TextView)layout.findViewById(R.id.speedFast);
        speedAvg = (TextView)layout.findViewById(R.id.speedAvg);

        TextView ms1 = (TextView)layout.findViewById(R.id.ms1);
        TextView ms2 = (TextView)layout.findViewById(R.id.ms2);

        TextView btnTxtNotice = (TextView)layout.findViewById(R.id.btnTxtNotice);
        TextView btnTxtMessage = (TextView)layout.findViewById(R.id.btnTxtMessage);
        TextView btnTxtAccount = (TextView)layout.findViewById(R.id.btnTxtAccount);

        noticeAlert = (TextView)layout.findViewById(R.id.noticeAlert);
        messageAlert = (TextView)layout.findViewById(R.id.messageAlert);

        btnNotice = (LinearLayout)layout.findViewById(R.id.btnNotice);
        btnMessage = (LinearLayout)layout.findViewById(R.id.btnMessage);
        btnAccount = (LinearLayout)layout.findViewById(R.id.btnAccount);

        img_ad = (ImageView)layout.findViewById(R.id.img_ad);
/*
        graph.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        graph.getMeasuredWidth();
        graph.getMeasuredHeight();
*/

        /** LineGraph를 통해 막대그래프를 추가할 때, constants에 있는 arrayList를 가지고 들어감.
         * 따라서 lineGraph의 domain에선 arrayList가 사용되어야 하며, 해당 그래프의 최대 출력 개수는
         * constants의 Max_count가 사용된다.
         * */
        lineGraph = new LineGraph(getActivity());
        graph.addView(lineGraph);

        Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_bottom);

        setFontToViewBold(getActivity(), titleStat, titleRec, txtDetail1, txtDetail2, txtSpeedFast, txtSpeedAvg, para1, paraMessage,
                speedFast, speedAvg, ms1, ms2, statMessage, btnTxtNotice, btnTxtMessage, btnTxtAccount, noticeAlert, messageAlert);
        graph.startAnimation(slide);

        alertSetter();
    }

    /**
     *  각 버튼 베이스에서 알림이 떴는지 여부를 알려주기 위함.
     * 각 알림 텍스트 뷰 들의 경우 해당 뷰 들의 visibility가 gone으로 설정되어 있으므로,
     * 만약 message 혹은 notice가 존재할 경우 isNotice를 true로 만들어주어야 한다.
     * */
    public void alertSetter(){
        if(isNotice /*TODO : notice가 있을 때*/) {

            noticeAlert.setVisibility(View.VISIBLE);
            messageAlert.setVisibility(View.VISIBLE);

            noticeAlert.setText("1");
            messageAlert.setText("1");
        }
    }


    public void initListener(){

        txtDetail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyStatDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
                Toast.makeText(getActivity(), "Account", Toast.LENGTH_SHORT).show();
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
