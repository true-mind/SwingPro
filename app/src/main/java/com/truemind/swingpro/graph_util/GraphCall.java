package com.truemind.swingpro.graph_util;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 현석 on 2017-06-28.
 */

public class GraphCall {

    static final int LINE_WIDTH = 30;
    private static final String TAG = "MyTag";

    LineGraph lineGraph;
    Context context;
    LinearLayout graphBase;
    static float base_width;
    static float base_height;
    static float interval;

    static int max_count;
    private static float divider;
    static int color = Color.rgb(108, 130, 194);

    boolean NOT_IN_REGULAR_CASE = false;
    int minus_parameter;
    private List<Integer> dataset = new ArrayList<>();
    static List<Float> GRAPH_MATCHED_DATA = new ArrayList<>();

    public int min_in_list;
    public int max_in_list;

    /**
     * View instantiate 이후 호출되어야 함.
     * 색 지정 가능한 생성자.
     *
     * @param context   호출하는 대상의 context를 받아와야함
     * @param graphBase LinearLayout으로, 그래프를 표현하기 위한 빈 layout이 필요함.
     * @param max_count 그래프의 가로 최대 개수
     * @param dataset   그래프를 출력하기 위한 데이터 셋
     * @param color     막대 그래프의 막대 색상 정의
     */
    public GraphCall(Context context, LinearLayout graphBase, int max_count, List<Integer> dataset, int color) {
        this.context = context;
        this.graphBase = graphBase;
        this.max_count = max_count;
        this.dataset = dataset;
        this.color = color;
    }

    /**
     * View instantiate 이후 호출되어야 함.
     * 색 지정 불가능한 생성자. -> default로 색상을 Color.rgb(108, 130, 194)로 지정하였음.
     *
     * @param context   호출하는 대상의 context를 받아와야함
     * @param graphBase LinearLayout으로, 그래프를 표현하기 위한 빈 layout이 필요함.
     * @param max_count 그래프의 가로 최대 개수
     * @param dataset   그래프를 출력하기 위한 데이터 셋
     */
    public GraphCall(Context context, LinearLayout graphBase, int max_count, List<Integer> dataset) {
        this.context = context;
        this.graphBase = graphBase;
        this.max_count = max_count;
        this.dataset = dataset;
        this.color = Color.rgb(108, 130, 194);
    }

    /**
     * Graph를 만드는 메서드.
     * 이 메서드는 그래프를 출력하는 클래스에서 호출이 필요하며, 레이아웃 생성 이후에 호출되어야 한다.
     * LinearLayout의 width와 height를 받아와야 하므로 뷰 생성 시점 이후에 호출되어야 함.
     * getViewTreeObserver().addOnGlobalLayoutListener()의 사용을 권장함.
     * (위 리스너 사용시, 이후에는 View.getViewTreeObserver().removeOnGlobalLayoutListener(this)를 통해
     * 해당 리스너의 제거가 필요하다.)
     */
    public void setGraph() {
        setInterval();
        DGD();
        setGraphData();
        getBestScore();
        lineGraph = new LineGraph(context);
        logOutData();
    }

    /**
     * Graph를 출력하는 메서드
     * 그래프를 출력하고자하는 클래스에서의 호출이 필요하다.
     * graph를 생성자를 통해 전달받은 LinearLayout에 출력하며, 이후 애니메이션 발생.
     */
    public void showGraph() {
        graphBase.addView(lineGraph);
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slide_from_bottom);
        graphBase.startAnimation(slide);
    }

    /**
     * define Graph divider
     * Call only if dataset got values.
     * <p>
     * DGD를 통해 dataSet에서의 최소 값과 최대 값을 받아,
     * constructor를 통해 받아온 layout의 height의 최대 출력 범위에 맞춰 나타낼 수 있다.
     * 기본적으로, 연산은 최대값과 최소값의 차를 height로 나누는 연산을 취하며,
     * 만약 max값과 min의 값이 같아 그 차가 0일 때 그래프는 1로 나눠진다.
     * <p>
     * 또한 divisor의 범위가 1보다 작을때 (또한 0보다 클 때) 나누는 수에 의하여 그래프의 값은
     * 더 커지므로, divisor의 범위가 작을 때는, minus 연산을 취하며, 이를 위해 NOT_IN_REGULAR_CASE로 알려준다.
     *
     * @return division
     */
    private float DGD() {

        min_in_list = dataset.get(0);
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i) < min_in_list) {
                min_in_list = dataset.get(i);
            }
        }

        max_in_list = dataset.get(0);
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i) > max_in_list) {
                max_in_list = dataset.get(i);
            }
        }
        if (max_in_list - min_in_list == 0) {
            Log.d(TAG, "DGD Result : 1");
            NOT_IN_REGULAR_CASE = true;
            return 1;
        } else {
            float division = (max_in_list - min_in_list) / base_height;
            Log.d(TAG, "DGD Result : " + Float.toString(division));
            if (0 < division && division < 1) {
                Log.d(TAG, "NOT_IN_REGULAR_CASE");
                NOT_IN_REGULAR_CASE = true;
                while (max_in_list - minus_parameter > base_height) {
                    minus_parameter = minus_parameter + 100;
                    Log.d(TAG, "minus para--------: " + Integer.toString(minus_parameter));
                }
                return 1;
            } else {
                NOT_IN_REGULAR_CASE = false;
                return division;
            }

        }
    }

    /**
     * 만약 현재 최고 속도보다 더 빠른 속도의 값이 나타날 때는 Constants.BEST_SCORE를 갱신
     */
    public void getBestScore() {
        if (min_in_list < Constants.BEST_SCORE) {
            Constants.BEST_SCORE = min_in_list;
        }
    }

    /**
     * 그래프의 데이터를 지정한다.
     * 그래프의 데이터를 지정하기에 앞서 divider를 받아오며, 생성된 divider를 통해
     * 베이스의 최대 높이에 맞춰진 그래프를 생성한다.
     */
    public void setGraphData() {

        divider = DGD();

        for (int cur_pos = 0; cur_pos < max_count; cur_pos++) {
            float base_height_minus_data;
            if (cur_pos < dataset.size()) {

                float divided_data;

                if (NOT_IN_REGULAR_CASE) {
                    divided_data = dataset.get(cur_pos) - minus_parameter;

                    Log.d(TAG, "----------------------------------------------------------------------");
                    Log.d(TAG, "dataset.get(" + cur_pos + ") : " + Float.toString(dataset.get(cur_pos)));
                    Log.d(TAG, "minus_parameter : " + Float.toString(minus_parameter));
                    Log.d(TAG, "minus_data : " + Float.toString(divided_data));
                } else {

                    if (divider > 1) {
                        divided_data = dataset.get(cur_pos) / divider;
                    } else {
                        divided_data = dataset.get(cur_pos) * divider;
                    }

                    Log.d(TAG, "----------------------------------------------------------------------");
                    Log.d(TAG, "dataset.get(" + cur_pos + ") : " + Float.toString(dataset.get(cur_pos)));
                    Log.d(TAG, "divider : " + Float.toString(divider));
                    Log.d(TAG, "divided_data : " + Float.toString(divided_data));

                }

                base_height_minus_data = base_height - divided_data;

                if (divided_data < 20) {
                    base_height_minus_data = base_height - 20;
                }
                GRAPH_MATCHED_DATA.add(cur_pos, base_height_minus_data);
            } else {
                base_height_minus_data = base_height;
                GRAPH_MATCHED_DATA.add(cur_pos, base_height_minus_data);
            }
        }

    }

    public void logOutData() {
        Log.d(TAG, "Width: " + Float.toString(base_width));
        Log.d(TAG, "Height: " + Float.toString(base_height));
        Log.d(TAG, "Max: " + Integer.toString(max_count));
        Log.d(TAG, "Interval: " + Float.toString(interval));
        Log.d(TAG, "divider: " + Float.toString(divider));
        Log.d(TAG, "max_in_list: " + Integer.toString(max_in_list));
        Log.d(TAG, "min_in_list: " + Integer.toString(min_in_list));
    }

    public void setInterval() {
        base_width = graphBase.getWidth();
        base_height = graphBase.getHeight();
        interval = base_width / max_count + 1;
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
