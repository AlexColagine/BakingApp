package com.example.android.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Steps;

/**
 * Created by Alessandro on 11/04/2018.
 */

/**
 * The Room database.
 */
@Database(entities = {Recipe.class , Ingredients.class , Steps.class} , version = 1)
public abstract class BakingDatabase extends RoomDatabase {

    /**
     * @return The DAO for the tables.
     */
    public abstract RecipeDao recipeDao();
    public abstract IngredientsDao ingredientsDao();
    public abstract StepsDao stepsDao();

    /** The only instance */
    private static BakingDatabase mInstance;

    /**
     * Gets the singleton instance of SampleDatabase.
     *
     * @param context The context.
     * @return The singleton instance of SampleDatabase.
     */
    public static synchronized BakingDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room
                    .databaseBuilder(context.getApplicationContext(), BakingDatabase.class, "ex")
                    .build();
        }
        return mInstance;
    }
}
