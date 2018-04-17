package com.example.android.bakingapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

import static com.example.android.bakingapp.database.BakingContract.CONTENT_AUTHORITY;

/**
 * Created by Alessandro on 11/04/2018.
 */

public class BakingProvider extends ContentProvider {

    /** The match code for some items table. */
    private static final int CODE_RECIPES = 101;
    private static final int CODE_INGREDIENTS = 201;
    private static final int CODE_STEPS = 301;

    /** The match code for an item table. */
    private static final int CODE_RECIPES_WITH_ID = 102;
    private static final int CODE_INGREDIENTS_WITH_ID = 202;
    private static final int CODE_STEPS_WITH_ID = 302;

    /** The URI matcher. */
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(CONTENT_AUTHORITY, BakingContract.RecipeEntry.TABLE_NAME, CODE_RECIPES);
        matcher.addURI(CONTENT_AUTHORITY, BakingContract.RecipeEntry.TABLE_NAME + "/#", CODE_RECIPES_WITH_ID);

        matcher.addURI(CONTENT_AUTHORITY, BakingContract.IngredientsEntry.TABLE_NAME, CODE_INGREDIENTS);
        matcher.addURI(CONTENT_AUTHORITY, BakingContract.IngredientsEntry.TABLE_NAME + "/#", CODE_INGREDIENTS_WITH_ID);

        matcher.addURI(CONTENT_AUTHORITY, BakingContract.StepsEntry.TABLE_NAME, CODE_STEPS);
        matcher.addURI(CONTENT_AUTHORITY, BakingContract.StepsEntry.TABLE_NAME + "/#", CODE_STEPS_WITH_ID);

    }

    BakingDatabase bakingDb;

    @Override
    public boolean onCreate() {
        bakingDb = BakingDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final Context context = getContext();
        if (context == null) return null;

        Cursor cursor;
        String id;
        int match = matcher.match(uri);
        switch (match){
            case CODE_RECIPES:
                cursor = bakingDb.recipeDao().selectAllRecipe();
                cursor.setNotificationUri(context.getContentResolver(), uri);
                break;
            case CODE_INGREDIENTS:
                id = uri.getQueryParameter(BakingContract.IngredientsEntry.COLUMN_ID);
                cursor = bakingDb.ingredientsDao().selectIngredientsById(Integer.valueOf(id));
                cursor.setNotificationUri(context.getContentResolver(), uri);
                break;
            case CODE_STEPS:
                id = uri.getQueryParameter(BakingContract.StepsEntry.COLUMN_ID);
                cursor = bakingDb.stepsDao().selectStepsById(Integer.valueOf(id));
                cursor.setNotificationUri(context.getContentResolver(), uri);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final Context context = getContext();
        if (context == null) return null;

        long id;
        final int match = matcher.match(uri);
        switch (match){
            case CODE_RECIPES_WITH_ID:
                id = bakingDb.recipeDao().addRecipe(Recipe.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_INGREDIENTS_WITH_ID:
                id = bakingDb.ingredientsDao().addIng(Ingredients.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_STEPS_WITH_ID:
                id = bakingDb.stepsDao().addStep(Steps.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final Context context = getContext();
        if (context == null) return 0;

        int count;
        String id;
        switch(matcher.match(uri)){
            case CODE_RECIPES:
                count = bakingDb.recipeDao().deleteRecipeById((int) ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            case CODE_INGREDIENTS:
                id = uri.getQueryParameter(BakingContract.IngredientsEntry.COLUMN_ID);
                count = bakingDb.ingredientsDao().deleteIngredientsById(Integer.valueOf(id));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            case CODE_STEPS:
                id = uri.getQueryParameter(BakingContract.IngredientsEntry.COLUMN_ID);
                count = bakingDb.stepsDao().deleteStepsById(Integer.valueOf(id));
                context.getContentResolver().notifyChange(uri, null);
                return count;

            default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final Context context = getContext();
        if (context == null) return 0;

        switch(matcher.match(uri)){
            case CODE_INGREDIENTS:
                final ArrayList<Ingredients> ingredients = new ArrayList<>();
                for (ContentValues valueIngredient : values)
                    ingredients.add(Ingredients.fromContentValues(valueIngredient));

                bakingDb.ingredientsDao().addIngredients(ingredients);
                return values.length;
            case CODE_STEPS:
                final ArrayList<Steps> steps = new ArrayList<>();
                for (ContentValues valueStep : values)
                    steps.add(Steps.fromContentValues(valueStep));

                bakingDb.stepsDao().addSteps(steps);
                return values.length;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
