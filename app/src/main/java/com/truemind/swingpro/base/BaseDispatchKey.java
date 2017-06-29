package com.truemind.swingpro.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.ui.detail.MyRecordDetailActivity;
import com.truemind.swingpro.ui.detail.MyStatDetailActivity;
import com.truemind.swingpro.ui.main.MainActivity;
import com.truemind.swingpro.ui.notice.NoticeActivity;
import com.truemind.swingpro.util.CommonDialog;

/**
 * Created by 현석 on 2017-06-19.
 * <p>
 * 모든 testActivity에서는 이 BaseDispatchKey를 상속받아야 한다.
 */

public abstract class BaseDispatchKey extends Activity {

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

    TextView serviceText;
    TextView privacyText;

    /**
     * Typeface로 폰트 적용
     * (배민 주아체로 적용)
     *
     * @param views 적용을 원하는 모든 TextView
     *              ,로 구분하여 무제한 개수의 동시 적용 가능
     */
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
     */

    public void setFontToViewBold2(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(this.getAssets(), "BMDOHYEON_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    public void setColor(View v, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setBackgroundColor(getResources().getColor(color, null));
        } else {
            v.setBackgroundColor(getResources().getColor(color));
        }
    }

    public void setTxtColor(TextView v, int color) {
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
     *
     * @return activity
     */
    public Activity getContext() {
        return this;
    }

    public Context getAppContext() {
        return getApplicationContext();
    }

    public void initSlideMenu(String headerTitle) {

        slidingPage = (LinearLayout) findViewById(R.id.slidingPage);
        menuBtn = (LinearLayout) findViewById(R.id.menuBtn);
        TextView txtTitleBar = (TextView) findViewById(R.id.txtTitleBar);
        txtTitleBar.setText(headerTitle);

        baseMyAccount = (LinearLayout) findViewById(R.id.baseMyAccount);
        baseMyStat = (LinearLayout) findViewById(R.id.baseMyStat);
        baseMyRecord = (LinearLayout) findViewById(R.id.baseMyRecord);
        baseMeasure = (LinearLayout) findViewById(R.id.baseMeasure);
        baseNotice = (LinearLayout) findViewById(R.id.baseNotice);
        baseSetting = (LinearLayout) findViewById(R.id.baseSetting);
        baseBluetooth = (LinearLayout) findViewById(R.id.baseBluetooth);
        privacyText = (TextView) findViewById(R.id.privacyText);
        serviceText = (TextView) findViewById(R.id.serviceText);

        SpannableString contentPrivacy = new SpannableString("개인정보취급방침");
        contentPrivacy.setSpan(new UnderlineSpan(), 0, contentPrivacy.length(), 0);

        SpannableString contentService = new SpannableString("서비스이용약관");
        contentService.setSpan(new UnderlineSpan(), 0, contentService.length(), 0);

        privacyText.setText(contentPrivacy);
        serviceText.setText(contentService);

        TextView txtMyAccount = (TextView) findViewById(R.id.txtMyAccount);
        TextView txtMyStat = (TextView) findViewById(R.id.txtMyStat);
        TextView txtMyRecord = (TextView) findViewById(R.id.txtMyRecord);
        TextView txtMeasure = (TextView) findViewById(R.id.txtMeasure);
        TextView txtNotice = (TextView) findViewById(R.id.txtNotice);
        TextView txtSetting = (TextView) findViewById(R.id.txtSetting);
        TextView txtBluetooth = (TextView) findViewById(R.id.txtBluetooth);
        setFontToViewBold(txtBluetooth, txtSetting, txtNotice, txtMeasure, txtMyRecord, txtMyStat, txtMyAccount, txtTitleBar, privacyText, serviceText);

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

    public void menuEvent() {
        if (isPageOpen) {
            slidingPage.startAnimation(translateRightAnim);
        } else {
            slidingPage.setVisibility(View.VISIBLE);
            slidingPage.startAnimation(translateLeftAnim);
        }
    }

    /**
     * Side Menu Button Listener
     */
    public void menuBtnListener() {

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
                if (Constants.LIST_AVG.size() < 1) {
                    Toast.makeText(getContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), MyRecordDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
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


        privacyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(getContext(), "로컬 버전에선 개인정보를 수집하지 않습니다.");
            }
        });

        serviceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(getContext(), "서비스 이용약관에 해당되지 않습니다.");
            }
        });
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            if (isPageOpen) {
                slidingPage.setVisibility(View.GONE);
                isPageOpen = false;
            } else {
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

    public void viewOnVisible(View viewVisible, View... viewsGone) {

        viewVisible.setVisibility(View.VISIBLE);
        for (View view : viewsGone) {
            view.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                onkey1();
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                onkey2();
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                onkey3();
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                onkey4();
            } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
                onkey5();
            } else if (keyCode == KeyEvent.KEYCODE_1) {
                onkey6();
            } else if (keyCode == KeyEvent.KEYCODE_2) {
                onkey7();
            } else if (keyCode == KeyEvent.KEYCODE_3) {
                onkey8();
            } else if (keyCode == KeyEvent.KEYCODE_4) {
                onkey9();
            } else if (keyCode == KeyEvent.KEYCODE_5) {
                onkey10();
            } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                onKeyBack();
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_SHOW_UI);
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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

    public void onKeyBack() {
        if (isPageOpen) {
            menuEvent();
        } else {
            onBack();
        }
    }

    public abstract void onBack();


    public int getMyPreferences(int seq) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return Integer.parseInt(pref.getString(Integer.toString(seq), "0"));
    }

    public int getMyPreferencesSize() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return Integer.parseInt(pref.getString("Max", "0"));
    }

    public void saveMyPreferences(int seq, int value, int max) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Integer.toString(seq), Integer.toString(value));
        editor.putString("Max", Integer.toString(max));
        editor.apply();
    }

    public void removeMyPreferences(String seq) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(seq);
        editor.apply();
    }

    public void removeMyAllPreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
