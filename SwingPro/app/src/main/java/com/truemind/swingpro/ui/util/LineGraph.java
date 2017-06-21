package com.truemind.swingpro.util;

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

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int max = Constants.GRAPH_MAX_COUNT+1;
        int interval = width/max;

        /** ArrayList 에서 값을 꺼내와서 그래프를 그릴 수 있어야됨.*/
        ArrayList dataset = Constants.LIST_AVG;

        Log.d("MyTag", "Width: "+Integer.toString(width));
        Log.d("MyTag", "Height: "+Integer.toString(height));
        Log.d("MyTag", "Max: "+Integer.toString(max));
        Log.d("MyTag", "Interval: "+Integer.toString(interval));

        Paint paint = new Paint();
        paint.setColor(Color.rgb(108, 130, 194));
        paint.setAntiAlias(true);

        paint.setStrokeWidth(30);
        for(int i = 1; i<max; i++){
            int startX = i*interval;
            canvas.drawLine(startX, height, startX, height-20*i, paint);
        }
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

}
