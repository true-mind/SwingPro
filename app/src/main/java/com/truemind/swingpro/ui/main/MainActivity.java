package com.truemind.swingpro.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.truemind.swingpro.BaseActivity;
import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-15.
 *
 * 이 탭뷰에서는 프래그먼트 간의 slide Animation을 사용하지만 ViewPager를 통해
 * slide Animation을 구현하지 않는다.
 * ViewPager를 통해 구현할 경우 다음과 같은 문제가 있다.
 *
 * -1.  ViewPager를 이용할 경우 AppCompat 를 이용하여야 한다.
 *      이는 ViewPager Adapter에 사용되는 "FragmentStatePagerAdapter(혹은 FragmentPagerAdaper)" 때문인데,
 *      위 Adapter는 Android.support.v4.app에만 존재하기 때문이다.
 *
 * -2.  ViewPager를 이용하여 구현하였을 때 프래그먼트 간의 메모리 누수 문제가 있다.
 *      만약 FragmentStatePagerAdapter를 사용하였을 경우 프래그먼트는 좌우의 프래그먼트만 유지하므로,
 *      0 - 2의 tab으로 넘어가는 등의 동작을 할 경우에는 해당 프래그먼트가 초기화 된다.
 *
 *      또한, FragmentPagerAdapter를 사용하였을 경우에는 프래그먼트를 해제하지 않기 때문에
 *      메모리 누수가 발생할 염려가 크다.
 *
 * -3. ViewPager를 이용할 시 Swipe를 통해 프래그먼트를 전환시킬 수 있지만, 이 경우 TabView의 레이아웃만을
 *     사용한 해당 Activity의 경우 버튼의 색이 바뀌지 않는다.
 *     따라서 강제로 바꾸는 경우 현재 fragment의 index를 받아와야 하는데, 이 이벤트를 받아오는 메소드 구현이
 *     복잡하기 짝이없다.
 *
 * 따라서 swipe를 통해 프래그먼트를 전환시킬 수는 없지만, slideAnimation을 통해 프래그먼트 전환 효과를
 * 줄 수는 있다.
 *
 */

public class MainActivity extends BaseActivity {

    private static final int FIRST_TAB = 0;
    private static final int SECOND_TAB = 1;
    private static final int THIRD_TAB = 2;

    Animation pageAnimationRight;
    Animation pageAnimationLeft;

    private TextView tabTxt1;
    private TextView tabTxt2;
    private TextView tabTxt3;

    private LinearLayout tab1;
    private LinearLayout tab2;
    private LinearLayout tab3;

    private View tabMark1;
    private View tabMark2;
    private View tabMark3;

    ImageView img;

    FrameLayout container;

    public int currentTab = Constants.TAB_POSITION;
    public int previousTab = Constants.TAB_POSITION;

    /**for onBackPress*/
    public static final long FINISH_INTERVAL_TIME = 2000;
    public long backPressedTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

        initSlideMenu("SwingPro");

    }


    /** View Initializing*/
    public void initView() {
        container = (FrameLayout) findViewById(R.id.container);
        img = (ImageView)findViewById(R.id.img);
        tabTxt1 = (TextView) findViewById(R.id.tabTxt1);
        tabTxt2 = (TextView) findViewById(R.id.tabTxt2);
        tabTxt3 = (TextView) findViewById(R.id.tabTxt3);
        setFontToViewBold(tabTxt1, tabTxt2, tabTxt3);

        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab3 = (LinearLayout) findViewById(R.id.tab3);

        tabMark1 = findViewById(R.id.tabMark1);
        tabMark2 = findViewById(R.id.tabMark2);
        tabMark3 = findViewById(R.id.tabMark3);

        initTab(Constants.TAB_POSITION);
        tabMarking(Constants.TAB_POSITION);

        pageChange animationListener = new pageChange();
        pageAnimationRight = AnimationUtils.loadAnimation(getContext(),R.anim.slide_to_right_fast);
        pageAnimationLeft = AnimationUtils.loadAnimation(getContext(),R.anim.slide_to_left_fast);
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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SWING_BEAT_LINK)));
            }
        });

    }

    /**
     * 각 Tab의 listener.
     * tab 버튼 클릭 시 동작 지정.
     * currentTab의 기록 시점은 해당 탭을 클릭하였을 때이며, 프래그먼트의 replace 이후
     * tab을 swipe하는 애니메이션 발생시킴.
     *
     * tabMarking을 통해 색을 입힐 view를 선택하여 tab 선택 효과를 입힌다.
     * */
    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentTab = (int) v.getTag();
            initTab(currentTab);
            tabMarking(currentTab);
            if(currentTab > previousTab){
                container.startAnimation(pageAnimationLeft);
            }else if(previousTab>currentTab){
                container.startAnimation(pageAnimationRight);
            }else{
                //intentionally do nothing
                //Refresh
            }

        }
    };

    public void initTab(int pos){
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
     * Animation의 listener.
     * 이 pageChange listener를 통해 pageChange 시 발생하는 애니메이션의 시작과 끝 지점을 파악할 수 있다.
     * (viewpager와 비슷한 효과를 줄 수 있음.)
     *
     * */
    private class pageChange implements Animation.AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            previousTab = currentTab;
            switch (currentTab) {

            }
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
     * @param seq 색을 지정할 tab 위치.
     *            이 tab은 선택되었을 때의 색상으로 변경되며, 나머지 탭들은 기본 색으로 돌아감
     * */
    public void tabMarking(int seq) {

        switch (seq){
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
     * @param selected 선택되었을 때의 색으로 지정할 텍스트
     * @param unselected  선택되지 않은, 기본 색으로 지정할 텍스트
     * */
    public void setTabTextColor(TextView selected, TextView... unselected){
        setTxtColor(selected, R.color.colorMint);

        for (TextView black : unselected)
            setTxtColor(black, R.color.colorBlack);
    }

    /**
     * 각 탭의 Marker 들의 색상을 변경시킴
     * @param selected 선택되었을 때의 색으로 지정할 마커
     * @param unselected  선택되지 않은, 기본 색으로 지정할 마커
     * */
    public void setTabMarkColor(View selected, View... unselected){
        setColor(selected, R.color.colorMint);

        for (View white : unselected)
            setColor(white, R.color.colorWhite);
    }


    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if(0<=intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getContext(), R.string.exitMessage,Toast.LENGTH_SHORT).show();
        }
    }

}
