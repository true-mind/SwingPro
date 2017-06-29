package com.truemind.swingpro.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truemind.swingpro.R;

import java.util.ArrayList;

/**
 * Created by jeongjisu on 2017. 6. 29..
 */

public class RecordRecyclerAdapter extends RecyclerView.Adapter<RecordRecyclerAdapter.ViewHolder> {
    private ArrayList<Integer> results;
    private int itemLayout;
    Context context;

    public RecordRecyclerAdapter(Context context, ArrayList<Integer> results, int itemLayout) {
        this.context = context;
        this.results = results;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int item = results.get(position);
        holder.tv.setText("" + item);
        setFontToViewBold(holder.tv, holder.tv_ms);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tv_ms;

        ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.listitem_record_tv);
            tv_ms = (TextView) itemView.findViewById(R.id.listitem_record_tv_ms);
        }
    }

    public void setFontToViewBold(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }
}