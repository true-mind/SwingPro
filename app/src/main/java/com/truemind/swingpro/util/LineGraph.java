package com.truemind.swingpro.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.truemind.swingpro.Constants;

import java.util.ArrayList;

/**
 * Created by 현석 on 2017-06-16.
 */

public class LineGraph extends View {

    private static final int LINE_WIDTH = 30;

    public LineGraph(Context context) {
        super(context);
    }

    public LineGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layout_width = canvas.getWidth();
        int layout_height = canvas.getHeight();
        int max_count = Constants.GRAPH_MAX_COUNT;
        int interval = layout_width / max_count + 1;

        /** ArrayList 에서 값을 꺼내와서 그래프를 그릴 수 있어야됨.*/
        ArrayList<Integer> dataset = Constants.LIST_FOR_GRAPH;
        ArrayList<Integer> datasetClone = Constants.LIST_FOR_GRAPH;
        int min_in_list;
        if (dataset.size() > 0) {
            min_in_list = dataset.get(0);

            Log.d("MyTag", "Width: " + Integer.toString(layout_width));
            Log.d("MyTag", "Height: " + Integer.toString(layout_height));
            Log.d("MyTag", "Max: " + Integer.toString(max_count));
            Log.d("MyTag", "Interval: " + Integer.toString(interval));

            Paint paint = new Paint();
            paint.setColor(Color.rgb(108, 130, 194));
            paint.setAntiAlias(true);
            paint.setStrokeWidth(LINE_WIDTH);

            /** 현재 list 내에서 가장 작은 값 찾기 */
            for (int i = 0; i < dataset.size(); i++) {
                if (dataset.get(i) < min_in_list) {
                    min_in_list = dataset.get(i);
                }
            }

            Log.d("MyTag", "min_in_list : " + Integer.toString(min_in_list));

            /**
             * 그래프 선들의 height를 구하기 위한 for문
             * */
            for (int current_count = 1; current_count < max_count; current_count++) {
                //LINE START POINT
                int line_height;
                /** 이 그래프의 최대 크기는 min_in_list를 보유한 값이므로, 그 min_in_list를 기준으로 제외하여 만든다. */
                if (current_count - 1 < dataset.size()) {
                    line_height = layout_height + min_in_list - dataset.get(current_count - 1);

                } else {
                    line_height = 0;
                }

                if (line_height > layout_height) {
                    line_height = layout_height;
                }

                datasetClone.set(current_count-2, line_height);
            }

            int min_in_line_height = datasetClone.get(0);
            for (int i = 0; i < dataset.size(); i++) {
                if (datasetClone.get(i) < min_in_list) {
                    min_in_line_height = datasetClone.get(i);
                }
            }

            int max_in_line_height = datasetClone.get(0);
            for (int i = 0; i < dataset.size(); i++) {
                if (datasetClone.get(i) > min_in_list) {
                    max_in_line_height = datasetClone.get(i);
                }
            }

            int divider = 1;
            if((max_in_line_height - min_in_line_height)>layout_height){
                divider = (max_in_line_height - min_in_line_height)/layout_height;
            }

            for (int current_count = 1; current_count < max_count; current_count++) {
                int startX = current_count * interval;
                int line_height;
                if (current_count - 1 < dataset.size()) {
                    Log.d("MyTag", "dataset.size:" + Integer.toString(dataset.size()));
                    line_height = datasetClone.get(current_count-1)/divider;

                    Log.d("MyTag", "divider:" + Integer.toString(divider));
                    if (line_height < 20) {
                        line_height = 20;
                    }
                } else {
                    line_height = 0;
                }
                canvas.drawLine(startX, layout_height, startX, layout_height - line_height, paint);
            }
        }
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

}
