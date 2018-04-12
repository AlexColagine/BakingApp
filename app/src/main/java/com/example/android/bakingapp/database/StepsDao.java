package com.example.android.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

/**
 * Created by Alessandro on 11/04/2018.
 */

/**
 * Data access object for Steps.
 */
@Dao
public interface StepsDao {

    @Query("SELECT * FROM Steps")
    Cursor selectAllSteps();

    @Query("DELETE FROM Steps WHERE id = :id")
    int deleteStepsById(int id);

    @Query("SELECT * FROM Steps WHERE id = :id")
    Steps getSteps(int id);

    @Query("SELECT * FROM Steps WHERE id = :id")
    Cursor selectStepsById(int id);

    @Insert
    void addSteps(ArrayList<Steps> steps);

    @Insert
    long addStep(Steps steps);

}
