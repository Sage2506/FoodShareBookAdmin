package com.example.sage.foodsharebook.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sage.foodsharebook.R;
import com.example.sage.foodsharebook.models.Measure;

import java.util.ArrayList;
import java.util.List;

public class MeasuresListAdapter extends RecyclerView.Adapter<MeasuresListAdapter.MeasureViewHolder> {
    private List<Measure> dataset;
    private Boolean touch = true;

    public MeasuresListAdapter(){
        dataset = new ArrayList<>();
    }
    @Override
    public MeasureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_measure, parent, false);
        return new MeasureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MeasureViewHolder holder, int position) {
        final Measure model = dataset.get(position);
        holder.textView.setText(model.getName());
        holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(touch){
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    public void addMeasuresList(ArrayList<Measure> measures){
        dataset.addAll(measures);
        notifyDataSetChanged();
    }

    public List<Measure> allSelected(){
        List<Measure> list = new ArrayList<>();
        for(Measure measure : dataset){
            if(measure.isSelected())
                list.add(measure);
        }
        return list;
    }

    public int allSelectedCount(){
        int n = 0;
        for(Measure measure : dataset){
            if(measure.isSelected())
            n++;
        }
        return n;
    }

    public void switchEnable(){
        touch = !touch;
    }

    public void clearSelection(){
        for(Measure measure: dataset){
            if(measure.isSelected())
                measure.setSelected(!measure.isSelected());
        }
        notifyDataSetChanged();
    }
    public class MeasureViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;

        private MeasureViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
