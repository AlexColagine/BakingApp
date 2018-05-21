package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.utils.Connectivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.model.Ingredients;

import java.util.ArrayList;

import static com.example.android.bakingapp.utils.StringUtils.INGREDIENTS;
import static com.example.android.bakingapp.utils.StringUtils.RECYCLER_INGREDIENTS_STATE;

/**
 * Created by Alessandro on 13/04/2018.
 */

public class IngredientsFragment extends Fragment {

    IngredientsAdapter ingredientsAdapter;
    ArrayList<Ingredients> ingredientsArrayList;
    RecyclerView ingredientsView;
    View rootView;
    Parcelable ingredientState;

    public static IngredientsFragment newInstance(ArrayList<Ingredients> ingredientsArrayList) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENTS, ingredientsArrayList);
        ingredientsFragment.setArguments(bundle);

        return ingredientsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ingredientsView = rootView.findViewById(R.id.recycler_ingredients);

        if (Connectivity.internetAvailable(getContext())) {
            updateIngredientsUi();
        } else {
            setupUiOffline();
        }

        if (savedInstanceState != null) {
            ingredientState = savedInstanceState.getParcelable(RECYCLER_INGREDIENTS_STATE);
        }

        return rootView;
    }

    @SuppressWarnings("ConstantConditions")
    private void updateIngredientsUi() {
        ingredientsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ingredientsView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        ingredientsView.setHasFixedSize(true);
        if (getArguments() != null) {
            ingredientsArrayList = getArguments().getParcelableArrayList(INGREDIENTS);
        }
        ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientsArrayList);
        ingredientsView.setAdapter(ingredientsAdapter);
    }

    private void setupUiOffline(){
        ingredientsView.setVisibility(View.GONE);

        TextView errorMessage = rootView.findViewById(R.id.empty_text);
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.no_internet_connection));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (ingredientsView != null && Connectivity.internetAvailable(getContext())) {
            outState.putParcelable(RECYCLER_INGREDIENTS_STATE, ingredientsView.getLayoutManager().onSaveInstanceState());
        }
    }
}
