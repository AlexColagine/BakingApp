package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.ui.StepActivity;

import java.util.ArrayList;

import static com.example.android.bakingapp.Utils.STEPS;

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
        final Steps steps = stepArrayList.get(position);

        holder.numberStep.setText(String.valueOf(steps.getId()));
        holder.shortDescription.setText(steps.getShortDescription());
        holder.clickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStep = new Intent(mContext , StepActivity.class);
                sendStep.putExtra(STEPS , steps);
                mContext.startActivity(sendStep);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (stepArrayList != null) ? stepArrayList.size() : 0;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder  {

        ConstraintLayout clickItem;
        TextView numberStep;
        TextView shortDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);
            clickItem = itemView.findViewById(R.id.constraint_step);
            numberStep = itemView.findViewById(R.id.text_id);
            shortDescription = itemView.findViewById(R.id.text_short_description);
        }
    }
}
