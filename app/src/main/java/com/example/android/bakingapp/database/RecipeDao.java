package com.example.android.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.android.bakingapp.model.Recipe;

/**
 * Created by Alessandro on 11/04/2018.
 */

/**
 * Data access object for Recipe.
 */
@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    Cursor selectAllRecipe();

    @Query("DELETE FROM Recipe WHERE id = :id")
    int deleteRecipeById(int id);

    @Query("SELECT * FROM Recipe WHERE id = :id")
    Recipe getRecipe(int id);

    @Query("SELECT * FROM Recipe WHERE id = :id")
    Cursor selectRecipeById(int id);

    @Insert
    long addRecipe(Recipe recipe);
}
