package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.model.Ingredients;

import java.util.ArrayList;

/**
 * Created by Alessandro on 13/04/2018.
 */

public class IngredientsFragment extends Fragment {

    IngredientsAdapter ingredientsAdapter;
    ArrayList<Ingredients> ingredientsArrayList;
    RecyclerView ingredientsView;
    View rootView;

    public static IngredientsFragment newInstance(ArrayList<Ingredients> ingredientsArrayList){
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredients", ingredientsArrayList);
        ingredientsFragment.setArguments(bundle);

        return ingredientsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ingredients , container , false);
        updateIngredientsUi();
        return rootView;
    }


    @SuppressWarnings("ConstantConditions")
    private void updateIngredientsUi(){
        ingredientsView = rootView.findViewById(R.id.recycler_ingredients);
        ingredientsView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        ingredientsView.addItemDecoration(new DividerItemDecoration(getContext() , LinearLayoutManager.VERTICAL));
        ingredientsView.setHasFixedSize(true);
        if(getArguments() != null){
            ingredientsArrayList = getArguments().getParcelableArrayList("ingredients");
        }
        ingredientsAdapter = new IngredientsAdapter(getContext() , ingredientsArrayList);
        ingredientsView.setAdapter(ingredientsAdapter);
    }
}
