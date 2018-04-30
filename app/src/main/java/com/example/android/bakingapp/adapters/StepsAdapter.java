package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

/**
 * Created by Alessandro on 13/04/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private Context mContext;
    private ArrayList<Steps> stepArrayList = new ArrayList<>();

    public StepsAdapter(Context context , ArrayList<Steps> stepArrayList){
        this.mContext = context;
        this.stepArrayList = stepArrayList;
    }

    @Override
    public StepsAdapter.StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.steps_list , parent , false);
        return new StepsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.StepsViewHolder holder, int position) {
        Steps steps = stepArrayList.get(position);

        holder.numberStep.setText(String.valueOf(steps.getId()));
        holder.shortDescription.setText(steps.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return (stepArrayList != null) ? stepArrayList.size() : 0;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder  {

        TextView numberStep;
        TextView shortDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);
            numberStep = itemView.findViewById(R.id.text_id);
            shortDescription = itemView.findViewById(R.id.text_short_description);
        }
    }
}
