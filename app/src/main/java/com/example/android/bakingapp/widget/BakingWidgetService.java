package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.database.BakingContract;
import com.example.android.bakingapp.model.Ingredients;

import java.util.ArrayList;

import static com.example.android.bakingapp.utils.StringUtils.INGREDIENT_ID;
import static com.example.android.bakingapp.utils.StringUtils.RECIPE_ID;

/**
 * Created by Alessandro on 16/05/2018.
 */

public class BakingWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<Ingredients> ingredientList = new ArrayList<>();
    private long recipeId;

    public BakingRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        recipeId = Long.valueOf(intent.getData().getSchemeSpecificPart());
    }

    private ArrayList<Ingredients> getIngredients(long recipeId) {
      /*  Uri baseIngredientUri = BakingContract
                .IngredientsEntry
                .CONTENT_ITEM_URI.build();

        String ingredientUriString = baseIngredientUri.toString() + "/" + recipeId;
        Uri ingredientUri = Uri.parse(ingredientUriString); */

        Cursor ingredientCursor = mContext.getContentResolver()
                .query(Uri.parse(BakingContract.IngredientsEntry.CONTENT_URI + "/" + recipeId),
                        null,
                        null,
                        null,
                        null);

        ArrayList<Ingredients> ingredients = new ArrayList<>();
        if (ingredientCursor != null) {
            while (ingredientCursor.moveToNext()) {
                Ingredients ingredient = getIngredientFromCursor(ingredientCursor);
                ingredients.add(ingredient);
            }
            ingredientCursor.close();
        }
        return ingredients;
    }

    private Ingredients getIngredientFromCursor(Cursor ingredientCursor) {
        Ingredients ingredient = new Ingredients();
        ingredient.setId(ingredientCursor.getLong(ingredientCursor
                .getColumnIndex(BakingContract.IngredientsEntry.COLUMN_ID)));
        ingredient.setRecipeID(ingredientCursor.getLong(ingredientCursor
                .getColumnIndex(BakingContract.IngredientsEntry.COLUMN_RECIPE_ID)));
        ingredient.setIngredient(ingredientCursor.getString(ingredientCursor
                .getColumnIndex(BakingContract.IngredientsEntry.COLUMN_INGREDIENT)));
        ingredient.setQuantity(ingredientCursor.getDouble(ingredientCursor
                .getColumnIndex(BakingContract.IngredientsEntry.COLUMN_QUANTITY)));
        ingredient.setMeasure(ingredientCursor.getString(ingredientCursor
                .getColumnIndex(BakingContract.IngredientsEntry.COLUMN_MEASURE)));
        return ingredient;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientList = getIngredients(recipeId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return (ingredientList != null) ? ingredientList.size() : 0;
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredientList.size() == 0) {
            return null;
        }

        // Get a layout item widget
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_ingredients);

        Ingredients ingredient = ingredientList.get(position);

        views.setTextViewText(R.id.text_ingredientWidget, ingredient.getIngredient());
        views.setTextViewText(R.id.text_measureWidget, ingredient.getQuantity() + " " + ingredient.getMeasure());

        Bundle extras = new Bundle();
        extras.putLong(RECIPE_ID, recipeId);
        extras.putLong(INGREDIENT_ID, ingredientList.get(position).getId());

        Intent sendWidget = new Intent();
        sendWidget.putExtras(extras);
        views.setOnClickFillInIntent(R.id.constraint_ingredient, sendWidget);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
