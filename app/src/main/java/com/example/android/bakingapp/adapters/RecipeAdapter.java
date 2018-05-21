package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.RecipeActivity;

import java.util.ArrayList;

import static com.example.android.bakingapp.utils.StringUtils.RECIPE_KEY;

/**
 * Created by Alessandro on 16/04/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    int resImage;
   // RecipeClickListener clickListener;

  /*  public interface RecipeClickListener {
        void onRecipeClickImage(Recipe recipe);

        void onRecipeClickFavorite(Recipe recipe);
    } */

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeArrayList /*RecipeClickListener clickListener*/) {
        this.mContext = context;
        this.recipeArrayList = recipeArrayList;
        //this.clickListener = clickListener;
    }

    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.cardview_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeAdapter.RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeArrayList.get(position);

        if (recipe.getFavorite()) {
            holder.fabFavorite.setImageResource(R.drawable.icon_fill_fab);
        } else {
            holder.fabFavorite.setImageResource(R.drawable.icon_fab);
        }

        holder.recipeName.setText(recipe.getName());

        if (TextUtils.equals(mContext.getString(R.string.nutella), holder.recipeName.getText().toString())) {
            resImage = R.drawable.img_nutella;
        } else if (TextUtils.equals(mContext.getString(R.string.brownies), holder.recipeName.getText().toString())) {
            resImage = R.drawable.img_brownies;
        } else if (TextUtils.equals(mContext.getString(R.string.y_cake), holder.recipeName.getText().toString())) {
            resImage = R.drawable.img_yellow_cake;
        } else if (TextUtils.equals(mContext.getString(R.string.cheesecake), holder.recipeName.getText().toString())) {
            resImage = R.drawable.img_cheesecake;
        } else if (TextUtils.isEmpty(holder.recipeName.getText().toString())) {
            Glide.with(mContext)
                    .load(recipe.getImage())
                    .placeholder(R.drawable.icon_placeholder)
                    .fitCenter()
                    .error(R.drawable.icon_error)
                    .into(holder.recipeImage);
        }
        Glide.with(mContext)
                .load(resImage)
                .into(holder.recipeImage);

        holder.recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendRecipe = new Intent(mContext, RecipeActivity.class);
                sendRecipe.putExtra(RECIPE_KEY, recipe);
                mContext.startActivity(sendRecipe);
            }
        });

        holder.fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recipe.getFavorite()) {
                    holder.fabFavorite.setImageResource(R.drawable.icon_fill_fab);
                    recipe.setFavorite(true);
                } else {
                    holder.fabFavorite.setImageResource(R.drawable.icon_fab);
                    recipe.setFavorite(false);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return (recipeArrayList != null) ? recipeArrayList.size() : 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        ImageView recipeImage;
        TextView recipeName;
        FloatingActionButton fabFavorite;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.image_recipe);
            recipeName = itemView.findViewById(R.id.text_recipe);
            fabFavorite = itemView.findViewById(R.id.fab);
        }

    }
}
