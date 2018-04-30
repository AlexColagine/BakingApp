package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredients;

import java.util.ArrayList;

/**
 * Created by Alessandro on 13/04/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>{

    private Context mContext;
    private ArrayList<Ingredients> ingredientArrayList = new ArrayList<>();

    public IngredientsAdapter(Context context , ArrayList<Ingredients> ingredientArrayList){
        this.mContext = context;
        this.ingredientArrayList = ingredientArrayList;
    }

    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.ingredients_list , parent , false);
        return new IngredientsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientsViewHolder holder, int position) {
        Ingredients ingredients = ingredientArrayList.get(position);

        holder.name.setText(ingredients.getIngredient());
        holder.quantity.setText(String.valueOf(ingredients.getQuantity()));
        holder.measure.setText(ingredients.getMeasure());
    }

    @Override
    public int getItemCount() {
        return (ingredientArrayList != null) ? ingredientArrayList.size() : 0;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView quantity;
        TextView measure;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_ingredient);
            quantity = itemView.findViewById(R.id.text_quantity);
            measure = itemView.findViewById(R.id.text_measure);
        }
    }
}
