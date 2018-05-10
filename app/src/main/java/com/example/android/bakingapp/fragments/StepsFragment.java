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
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

import static com.example.android.bakingapp.Utils.STEPS;

/**
 * Created by Alessandro on 13/04/2018.
 */

public class StepsFragment extends Fragment {

    StepsAdapter stepsAdapter;
    ArrayList<Steps> stepsArrayList;
    RecyclerView stepsView;
    View rootView;

    public static StepsFragment newInstance(ArrayList<Steps> stepsArrayList){
        StepsFragment stepsFragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS, stepsArrayList);
        stepsFragment.setArguments(bundle);

        return stepsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_steps , container , false);
        updateStepsUi();
        return rootView;
    }

    @SuppressWarnings("ConstantConditions")
    private void updateStepsUi(){
        stepsView = rootView.findViewById(R.id.recycler_steps);
        stepsView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        stepsView.addItemDecoration(new DividerItemDecoration(getContext() , LinearLayoutManager.VERTICAL));
        stepsView.setHasFixedSize(true);
        if(getArguments() != null){
            stepsArrayList = getArguments().getParcelableArrayList(STEPS);
        }
        stepsAdapter = new StepsAdapter(getContext() , stepsArrayList);
        stepsView.setAdapter(stepsAdapter);
    }
}
