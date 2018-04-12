package com.example.android.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

/**
 * Created by Alessandro on 11/04/2018.
 */

/**
 * Data access object for Ingredients.
 */
@Dao
public interface IngredientsDao {

    @Query("SELECT * FROM Ingredients")
    Cursor selectAllIngredients();

    @Query("DELETE FROM Ingredients WHERE id = :id")
    int deleteIngredientsById(int id);

    @Query("SELECT * FROM Ingredients WHERE id = :id")
    Steps getIngredients(int id);

    @Query("SELECT * FROM Ingredients WHERE id = :id")
    Cursor selectIngredientsById(int id);

    @Insert
    void addIngredients(ArrayList<Ingredients> ingredients);

    @Insert
    long addIng(Ingredients ingredients);
}
