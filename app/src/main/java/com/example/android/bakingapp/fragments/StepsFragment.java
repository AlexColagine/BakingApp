package com.example.android.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.ui.StepActivity;
import com.example.android.bakingapp.utils.Connectivity;

import java.util.ArrayList;

import static com.example.android.bakingapp.utils.StringUtils.RECYCLER_STEPS_STATE;
import static com.example.android.bakingapp.utils.StringUtils.STEPS;
import static com.example.android.bakingapp.utils.StringUtils.STEPS_ARRAYLIST;
import static com.example.android.bakingapp.utils.StringUtils.STEP_ID;
import static com.example.android.bakingapp.utils.StringUtils.TABLET;

/**
 * Created by Alessandro on 13/04/2018.
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepClickListener{

    StepsAdapter stepsAdapter;
    ArrayList<Steps> stepsArrayList;
    RecyclerView stepsView;
    View rootView;
    private boolean mTablet;
    Parcelable stepState;


    public static StepsFragment newInstance(ArrayList<Steps> stepsArrayList, boolean mTablet) {
        StepsFragment stepsFragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS, stepsArrayList);
        bundle.putBoolean(TABLET, mTablet);
        stepsFragment.setArguments(bundle);

        return stepsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        stepsView = rootView.findViewById(R.id.recycler_steps);

        if (Connectivity.internetAvailable(getContext())) {
            updateStepsUi();
        } else {
            setupUiOffline();
        }

        if (savedInstanceState != null) {
            stepState = savedInstanceState.getParcelable(RECYCLER_STEPS_STATE);
        }

        return rootView;
    }

    @SuppressWarnings("ConstantConditions")
    private void updateStepsUi() {
        stepsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        stepsView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        stepsView.setHasFixedSize(true);
        if (getArguments() != null) {
            stepsArrayList = getArguments().getParcelableArrayList(STEPS);
            mTablet = getArguments().getBoolean(TABLET);
        }
        stepsAdapter = new StepsAdapter(getContext(), stepsArrayList , this);
        stepsView.setAdapter(stepsAdapter);
    }

    private void setupUiOffline() {
        stepsView.setVisibility(View.GONE);

        TextView errorMessage = rootView.findViewById(R.id.empty_text);
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.no_internet_connection));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (stepsView != null && Connectivity.internetAvailable(getContext())) {
            outState.putParcelable(RECYCLER_STEPS_STATE, stepsView.getLayoutManager().onSaveInstanceState());
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void stepItemClick(Steps steps) {
        if(mTablet){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Bundle data = new Bundle();
            data.putParcelableArrayList(STEPS_ARRAYLIST , stepsArrayList);
            data.putInt(STEP_ID , steps.getId());

            ViewPagerStepFragment pagerStepFragment = new ViewPagerStepFragment();
            pagerStepFragment.setArguments(data);
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container , pagerStepFragment)
                    .commit();

        } else {
            Intent sendStep = new Intent(getContext() , StepActivity.class);
            sendStep.putExtra(STEPS , steps);
            sendStep.putExtra(STEPS_ARRAYLIST , stepsArrayList);
            startActivity(sendStep);
        }
    }

}
