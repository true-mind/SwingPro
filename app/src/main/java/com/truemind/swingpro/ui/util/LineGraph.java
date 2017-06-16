package com.truemind.swingpro.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;

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
        int interval = layout_width/max_count+1;

        /** ArrayList 에서 값을 꺼내와서 그래프를 그릴 수 있어야됨.*/
        ArrayList dataset = Constants.LIST_AVG;

        Log.d("MyTag", "Width: "+Integer.toString(layout_width));
        Log.d("MyTag", "Height: "+Integer.toString(layout_height));
        Log.d("MyTag", "Max: "+Integer.toString(max_count));
        Log.d("MyTag", "Interval: "+Integer.toString(interval));

        Paint paint = new Paint();
        paint.setColor(Color.rgb(108, 130, 194));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(LINE_WIDTH);

        /**
         * 현재 demoList의 값만 가지고 있음
         * */
        for(int current_count = 1; current_count<max_count; current_count++){

            //LINE START POINT
            int startX = current_count*interval;

            //DUMMY DATA
            int line_height = 20 * current_count;

            if(line_height > layout_height){
                line_height = layout_height;
            }

            canvas.drawLine(startX, layout_height, startX, layout_height-line_height, paint);
        }
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

}
