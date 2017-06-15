package com.truemind.swingpro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by 현석 on 2017-04-20.
 */
public abstract class BaseActivity extends Activity {

    boolean isPageOpen = false;
    Animation translateLeftAnim;
    Animation translateRightAnim;
    LinearLayout slidingPage;
    ImageButton menuBtn;

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
        Typeface NanumNormal = Typeface.createFromAsset(this.getAssets(), "BMJUA_ttf.ttf");

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
        Typeface NanumNormal = Typeface.createFromAsset(this.getAssets(), "BMDOHYEON_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
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
        menuBtn = (ImageButton)findViewById(R.id.menuBtn);
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

        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

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

    public void menuEvent(){
        if(isPageOpen){
            slidingPage.startAnimation(translateLeftAnim);
        }
        else{
            slidingPage.setVisibility(View.VISIBLE);
            slidingPage.startAnimation(translateRightAnim);
        }
    }

    public void menuBtnListener(){

        baseMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                menuEvent();
            }
        });

        baseMyStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
                menuEvent();
            }
        });

        baseNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
                menuEvent();
            }
        });

        baseSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "6", Toast.LENGTH_SHORT).show();
                menuEvent();
            }
        });

        baseBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "7", Toast.LENGTH_SHORT).show();
                menuEvent();
                goBTSetting();
            }
        });
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


/*
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            if(keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                onkey1();
            }
            else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                onkey2();
            }
            else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                onkey3();
            }
            else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                onkey4();
            }
            else if(keyCode == KeyEvent.KEYCODE_ENTER) {
                onkey5();
            }
            else if(keyCode == KeyEvent.KEYCODE_1) {
                onkey6();
            }
            else if(keyCode == KeyEvent.KEYCODE_2) {
                onkey7();
            }
            else if(keyCode == KeyEvent.KEYCODE_3) {
                onkey8();
            }
            else if(keyCode == KeyEvent.KEYCODE_4) {
                onkey9();
            }
            else if(keyCode == KeyEvent.KEYCODE_5) {
                onkey10();
            }
            else if(keyCode == KeyEvent.KEYCODE_BACK){
                onKeyBack();
            }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
                AudioManager mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_SHOW_UI);
            }else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
                AudioManager mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_SHOW_UI);
            }
            return true;
        }
        return false;
    }

    public abstract void onkey1();
    public abstract void onkey2();
    public abstract void onkey3();
    public abstract void onkey4();
    public abstract void onkey5();

    public abstract void onkey6();
    public abstract void onkey7();
    public abstract void onkey8();
    public abstract void onkey9();
    public abstract void onkey10();

    public abstract void onKeyBack();*/

}
