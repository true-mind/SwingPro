package com.truemind.swingpro.util;

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

    public TempoRecyclerAdapter(ArrayList<String> results, int itemLayout){
        this.itemLayout = itemLayout;
        this.results = results;
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
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public ViewHolder(View itemView){
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.listitem_tempo_tv);
        }
    }
}
