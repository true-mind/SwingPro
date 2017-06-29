package com.truemind.swingpro.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.ui.account.MyAccountSetting;
import com.truemind.swingpro.ui.detail.MyStatDetailActivity;
import com.truemind.swingpro.ui.main.MainActivity;
import com.truemind.swingpro.ui.notice.NoticeActivity;


/**
 * Created by 현석 on 2017-04-20.
 */
public abstract class BaseActivity extends Activity {

    boolean isPageOpen = false;
    Animation translateLeftAnim;
    Animation translateRightAnim;
    LinearLayout slidingPage;
    LinearLayout menuBtn;

    LinearLayout baseMyAccount;
    LinearLayout baseMyStat;
    LinearLayout baseMyRecord;
    LinearLayout baseMeasure;
    LinearLayout baseNotice;
    LinearLayout baseSetting;
    LinearLayout baseBluetooth;


    /**
     * Typeface로 폰트 적용
     * (배민 주아체로 적용)
     *
     * @param views 적용을 원하는 모든 TextView
     *              ,로 구분하여 무제한 개수의 동시 적용 가능
     * */
    public void setFontToViewBold(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(getContext().getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    /**
     * Typeface로 폰트 적용
     * (배민 도현체로 적용)
     *
     * @param views 적용을 원하는 모든 TextView
     *              ,로 구분하여 무제한 개수의 동시 적용 가능
     * */

    public void setFontToViewBold2(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(getContext().getAssets(), "BMDOHYEON_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    public void setColor(View v, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setBackgroundColor(getResources().getColor(color, null));
        } else {
            v.setBackgroundColor(getResources().getColor(color));
        }
    }

    public void setTxtColor(TextView v, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setTextColor(getResources().getColor(color, null));
        } else {
            v.setTextColor(getResources().getColor(color));
        }
    }

    public void goBTSetting() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.setClassName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings");
        startActivity(intent);
    }

    /**
     * 현재 context를 불러오기
     * @return activity
     * */
    public Activity getContext()
    {
        return this;
    }

    public Context getAppContext()
    {
        return getApplicationContext();
    }

    public void initSlideMenu(String headerTitle) {

        slidingPage = (LinearLayout)findViewById(R.id.slidingPage);
        menuBtn = (LinearLayout)findViewById(R.id.menuBtn);
        TextView txtTitleBar = (TextView)findViewById(R.id.txtTitleBar);
        txtTitleBar.setText(headerTitle);

        baseMyAccount = (LinearLayout)findViewById(R.id.baseMyAccount);
        baseMyStat = (LinearLayout)findViewById(R.id.baseMyStat);
        baseMyRecord = (LinearLayout)findViewById(R.id.baseMyRecord);
        baseMeasure = (LinearLayout)findViewById(R.id.baseMeasure);
        baseNotice = (LinearLayout)findViewById(R.id.baseNotice);
        baseSetting = (LinearLayout)findViewById(R.id.baseSetting);
        baseBluetooth = (LinearLayout)findViewById(R.id.baseBluetooth);

        TextView txtMyAccount = (TextView)findViewById(R.id.txtMyAccount);
        TextView txtMyStat = (TextView)findViewById(R.id.txtMyStat);
        TextView txtMyRecord = (TextView)findViewById(R.id.txtMyRecord);
        TextView txtMeasure = (TextView)findViewById(R.id.txtMeasure);
        TextView txtNotice = (TextView)findViewById(R.id.txtNotice);
        TextView txtSetting = (TextView)findViewById(R.id.txtSetting);
        TextView txtBluetooth = (TextView)findViewById(R.id.txtBluetooth);
        setFontToViewBold(txtBluetooth, txtSetting, txtNotice, txtMeasure, txtMyRecord, txtMyStat, txtMyAccount, txtTitleBar);

        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.slide_from_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.slide_from_right);

        SlidingPageAnimationListener animationListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animationListener);
        translateRightAnim.setAnimationListener(animationListener);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuEvent();
            }
        });

        menuBtnListener();
    }

    public void initFooter(){
        TextView txtFooter = (TextView)findViewById(R.id.txtFooter);
        TextView txtFooter2 = (TextView)findViewById(R.id.txtFooter2);
        setFontToViewBold2(txtFooter, txtFooter2);
    }

    public void menuEvent(){
        if(isPageOpen){
            slidingPage.startAnimation(translateRightAnim);
        }
        else{
            slidingPage.setVisibility(View.VISIBLE);
            slidingPage.startAnimation(translateLeftAnim);
        }
    }

    /**
     *Side Menu Button Listener
     *
     * */
    public void menuBtnListener(){

        baseMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyAccountSetting.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                menuEvent();
            }
        });

        baseMyStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.LIST_AVG.size() < 1) {
                    Toast.makeText(getContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), MyStatDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                menuEvent();
            }
        });

        baseMyRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
                menuEvent();
            }
        });

        baseMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.TAB_POSITION = 1;
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                menuEvent();
            }
        });

        baseNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                menuEvent();
            }
        });

        baseSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.TAB_POSITION = 2;
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                menuEvent();
            }
        });

        baseBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuEvent();
                goBTSetting();
            }
        });
    }

    public void viewOnVisible(View viewVisible, View... viewsGone){

        viewVisible.setVisibility(View.VISIBLE);
        for (View view : viewsGone){
            view.setVisibility(View.GONE);
        }

    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            if (isPageOpen) {
                slidingPage.setVisibility(View.GONE);
                isPageOpen = false;
            }
            else {
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationStart(Animation animation) {

        }
    }

}
