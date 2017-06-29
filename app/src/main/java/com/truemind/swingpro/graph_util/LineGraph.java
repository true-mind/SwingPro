package com.truemind.swingpro.graph_util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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

        float layout_height = GraphCall.base_height;
        int max_count = GraphCall.max_count;
        float interval = GraphCall.interval;

        /** ArrayList 에서 값을 꺼내와서 그래프를 그릴 수 있어야됨.*/
        List<Float> dataset = GraphCall.GRAPH_MATCHED_DATA;

        Paint paint = new Paint();
        paint.setColor(GraphCall.color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(GraphCall.LINE_WIDTH);

        for (int current_count = 0; current_count < max_count; current_count++) {
            float startX = (current_count+1) * interval;
            canvas.drawLine(startX, layout_height, startX, dataset.get(current_count), paint);
        }
    }


}
