package com.truemind.swingpro.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;

import java.util.Timer;
import java.util.TimerTask;

import com.truemind.swingpro.util.CommonDialog;
import com.truemind.swingpro.util.CustomAdapter;

/**
 * Created by 현석 on 2017-06-15.
 * <p>
 * 이 탭뷰에서는 프래그먼트 간의 slide Animation을 사용하지만 ViewPager를 통해
 * slide Animation을 구현하지 않는다.
 * ViewPager를 통해 구현할 경우 다음과 같은 문제가 있다.
 * <p>
 * -1.  ViewPager를 이용할 경우 AppCompat 를 이용하여야 한다.
 * 이는 ViewPager Adapter에 사용되는 "FragmentStatePagerAdapter(혹은 FragmentPagerAdaper)" 때문인데,
 * 위 Adapter는 Android.support.v4.app에만 존재하기 때문이다.
 * <p>
 * -2.  ViewPager를 이용하여 구현하였을 때 프래그먼트 간의 메모리 누수 문제가 있다.
 * 만약 FragmentStatePagerAdapter를 사용하였을 경우 프래그먼트는 좌우의 프래그먼트만 유지하므로,
 * 0 - 2의 tab으로 넘어가는 등의 동작을 할 경우에는 해당 프래그먼트가 초기화 된다.
 * <p>
 * 또한, FragmentPagerAdapter를 사용하였을 경우에는 프래그먼트를 해제하지 않기 때문에
 * 메모리 누수가 발생할 염려가 크다.
 * <p>
 * -3. ViewPager를 이용할 시 Swipe를 통해 프래그먼트를 전환시킬 수 있지만, 이 경우 TabView의 레이아웃만을
 * 사용한 해당 Activity의 경우 버튼의 색이 바뀌지 않는다.
 * 따라서 강제로 바꾸는 경우 현재 fragment의 index를 받아와야 하는데, 이 이벤트를 받아오는 메소드 구현이
 * 복잡하기 짝이없다.
 * <p>
 * 따라서 swipe를 통해 프래그먼트를 전환시킬 수는 없지만, slideAnimation을 통해 프래그먼트 전환 효과를
 * 줄 수는 있다.
 */

public class MainActivity extends BaseActivity {

    private static final int FIRST_TAB = 0;
    private static final int SECOND_TAB = 1;
    private static final int THIRD_TAB = 2;

    Animation pageAnimationRight;
    Animation pageAnimationLeft;
    Animation alphaBounce;

    private TextView tabTxt1;
    private TextView tabTxt2;
    private TextView tabTxt3;

    private LinearLayout tab1;
    private LinearLayout tab2;
    private LinearLayout tab3;

    private View tabMark1;
    private View tabMark2;
    private View tabMark3;

    ViewPager imgPager;

    FrameLayout container;

    LinearLayout settingBtnBase;
    TextView txtKeyMap;
    TextView txtBluetooth2;
    LinearLayout btnKeyMap;
    LinearLayout btnBluetooth;

    public int currentTab = Constants.TAB_POSITION;
    public int previousTab = Constants.TAB_POSITION;

    Button btnGoLook;
    Timer timer;

    /**
     * for onBackPress
     */
    private int backCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

        initSlideMenu("SwingPro");
        viewPagerTimerControll(5);
    }


    /**
     * View Initializing
     */
    public void initView() {
        container = (FrameLayout) findViewById(R.id.container);
        imgPager = (ViewPager) findViewById(R.id.imgPager);
        tabTxt1 = (TextView) findViewById(R.id.tabTxt1);
        tabTxt2 = (TextView) findViewById(R.id.tabTxt2);
        tabTxt3 = (TextView) findViewById(R.id.tabTxt3);

        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab3 = (LinearLayout) findViewById(R.id.tab3);

        tabMark1 = findViewById(R.id.tabMark1);
        tabMark2 = findViewById(R.id.tabMark2);
        tabMark3 = findViewById(R.id.tabMark3);

        initTab(Constants.TAB_POSITION);
        tabMarking(Constants.TAB_POSITION);

        btnGoLook = (Button) findViewById(R.id.btnGoLook);

        settingBtnBase = (LinearLayout) findViewById(R.id.settingBtnBase);
        btnKeyMap = (LinearLayout) findViewById(R.id.btnKeyMap);
        btnBluetooth = (LinearLayout) findViewById(R.id.btnBluetooth);
        txtKeyMap = (TextView) findViewById(R.id.txtKeyMap);
        txtBluetooth2 = (TextView) findViewById(R.id.txtBluetooth2);
        setFontToViewBold(tabTxt1, tabTxt2, tabTxt3, txtKeyMap, txtBluetooth2, btnGoLook);

        /** View Pager*/
        CustomAdapter viewPagerAdapter = new CustomAdapter(getLayoutInflater());
        imgPager.setAdapter(viewPagerAdapter);

        /** "Setting" 탭에서의 하단 버튼 생성을 위해*/
        alphaBounce = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_bounce);
        settingBtnBaseInit();

        /** 뷰페이저를 이용하지 않는 탭 뷰의 애니메이션을 위해 존재*/
        pageChange animationListener = new pageChange();
        pageAnimationRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_to_right_fast);
        pageAnimationLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_to_left_fast);
        pageAnimationRight.setAnimationListener(animationListener);
        pageAnimationLeft.setAnimationListener(animationListener);
    }

    public void initListener() {

        tab1.setOnClickListener(movePageListener);
        tab1.setTag(FIRST_TAB);

        tab2.setOnClickListener(movePageListener);
        tab2.setTag(SECOND_TAB);

        tab3.setOnClickListener(movePageListener);
        tab3.setTag(THIRD_TAB);

        /**
         * 뷰페이저 아이템 마다의 "보러가기" 버튼을 위해 있음.
         * 아이템의 보러가기를 눌렀을 때 해당 아이템의 위치에 따라서
         * 이동되는 링크가 다르기 때문에 각각을 switch-case로 정의해야함.
         * */

        btnGoLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (imgPager.getCurrentItem()) {
                    case 0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SWING_BEAT_LINK)));
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MINTO_WEB_LINK)));
                        break;
                    case 2:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SWING_BEAT_LINK)));
                        break;
                }
            }
        });
    }

    /**
     * ViewPager 주기적인 자동 스크롤 태스크
     *
     * @param seconds 초단위로 스크롤 타이밍을 받음 - 예 : 4초 간격
     *                <p>
     *                무조건 오른쪽으로 스크롤 됨.
     */
    public void viewPagerTimerControll(int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new viewPagerTimer(), 0, seconds * 1000);
    }

    private class viewPagerTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    int vp_page = imgPager.getCurrentItem();
                    int max_page = Constants.VIEW_PAGER_MAX_COUNT;
                    if (vp_page == max_page - 1) {
                        imgPager.setCurrentItem(0, true);
                    } else {
                        imgPager.setCurrentItem(vp_page + 1, true);
                    }

                }
            });
        }
    }


    /**
     * 각 Tab의 listener.
     * tab 버튼 클릭 시 동작 지정.
     * currentTab의 기록 시점은 해당 탭을 클릭하였을 때이며, 프래그먼트의 replace 이후
     * tab을 swipe하는 애니메이션 발생시킴.
     * <p>
     * tabMarking을 통해 색을 입힐 view를 선택하여 tab 선택 효과를 입힌다.
     */
    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentTab = (int) v.getTag();
            initTab(currentTab);
            tabMarking(currentTab);
            if (currentTab > previousTab) {
                //container.startAnimation(pageAnimationLeft);
            } else if (previousTab > currentTab) {
                //container.startAnimation(pageAnimationRight);
            } else {
                //intentionally do nothing
                //Refresh
            }
            settingBtnBaseInit();
        }
    };

    public void initTab(int pos) {
        switch (pos) {
            case FIRST_TAB:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new MyRecordFragment())
                        .commit();
                break;
            case SECOND_TAB:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new MeasureFragment())
                        .commit();
                break;
            case THIRD_TAB:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new SettingFragment())
                        .commit();
                break;
        }
    }


    /**
     * 아래 메서드는 모든 fragment 갱신 시 호출 되며, 호출 직후 현재 탭이 두번째 탭인지 확인.
     * 이 후 세번째 탭일 경우 추가 세팅 버튼 레이아웃을 노출시킨다.
     */
    public void settingBtnBaseInit() {
        if (currentTab == THIRD_TAB) {
            settingBtnBase.setVisibility(View.VISIBLE);
            settingBtnBase.startAnimation(alphaBounce);
            btnKeyMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonDialog dialog = new CommonDialog();
                    dialog.showDialog(getContext(), "Key Map", getResources().getString(R.string.KeyMap), true, "확인 (Enter)");
                }
            });
            btnBluetooth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBTSetting();
                }
            });

        } else {
            settingBtnBase.setVisibility(View.GONE);
        }

    }

    /**
     * Animation의 listener.
     * 이 pageChange listener를 통해 pageChange 시 발생하는 애니메이션의 시작과 끝 지점을 파악할 수 있다.
     * (viewpager와 비슷한 효과를 줄 수 있음.)
     */
    private class pageChange implements Animation.AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            previousTab = currentTab;
            settingBtnBaseInit();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationStart(Animation animation) {

        }
    }

    /**
     * tabMarking을 통해 현재 선택된 tab의 색를 지정할 수 있음.
     *
     * @param seq 색을 지정할 tab 위치.
     *            이 tab은 선택되었을 때의 색상으로 변경되며, 나머지 탭들은 기본 색으로 돌아감
     */
    public void tabMarking(int seq) {

        switch (seq) {
            case FIRST_TAB:
                setTabTextColor(tabTxt1, tabTxt2, tabTxt3);
                setTabMarkColor(tabMark1, tabMark2, tabMark3);
                break;
            case SECOND_TAB:
                setTabTextColor(tabTxt2, tabTxt1, tabTxt3);
                setTabMarkColor(tabMark2, tabMark1, tabMark3);
                break;
            case THIRD_TAB:
                setTabTextColor(tabTxt3, tabTxt1, tabTxt2);
                setTabMarkColor(tabMark3, tabMark1, tabMark2);
                break;
        }

    }

    /**
     * 각 탭의 텍스트들의 색상을 변경시킴
     *
     * @param selected   선택되었을 때의 색으로 지정할 텍스트
     * @param unselected 선택되지 않은, 기본 색으로 지정할 텍스트
     */
    public void setTabTextColor(TextView selected, TextView... unselected) {
        setTxtColor(selected, R.color.colorMint);

        for (TextView black : unselected)
            setTxtColor(black, R.color.colorBlack);
    }

    /**
     * 각 탭의 Marker 들의 색상을 변경시킴
     *
     * @param selected   선택되었을 때의 색으로 지정할 마커
     * @param unselected 선택되지 않은, 기본 색으로 지정할 마커
     */
    public void setTabMarkColor(View selected, View... unselected) {
        setColor(selected, R.color.colorMint);

        for (View white : unselected)
            setColor(white, R.color.colorWhite);
    }


    @Override
    public void onBack() {
        backCount++;
        Toast.makeText(getContext(), R.string.exitMessage, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backCount = 0;
            }
        }, 2000);
        if (backCount > 1) {
            finish();
        }
    }

}
