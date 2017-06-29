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
 * Created by jeongjisu on 2017. 6. 28..
 */

public class TempoRecyclerAdapter extends RecyclerView.Adapter<TempoRecyclerAdapter.ViewHolder> {

    private ArrayList<String> results;
    private int itemLayout;
    Context context;

    public TempoRecyclerAdapter(Context context, ArrayList<String> results, int itemLayout) {
        this.itemLayout = itemLayout;
        this.results = results;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = results.get(position);
        holder.tv.setText(item);
        setFontToViewBold(holder.tv);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.listitem_tempo_tv);
        }
    }

    public void setFontToViewBold(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

}
