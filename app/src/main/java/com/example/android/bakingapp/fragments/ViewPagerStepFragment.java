package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepPagerAdapter;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

import static com.example.android.bakingapp.utils.StringUtils.STEPS_ARRAYLIST;
import static com.example.android.bakingapp.utils.StringUtils.STEP_ID;

/**
 * Created by Alessandro on 13/05/2018.
 */

public class ViewPagerStepFragment extends Fragment {

    View rootView;
    int currentPosition;
    ArrayList<Steps> stepsArrayList;

    public static ViewPagerStepFragment newInstance(int stepID){
        ViewPagerStepFragment pagerStepFragment = new ViewPagerStepFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STEP_ID , stepID);
        pagerStepFragment.setArguments(bundle);

        return pagerStepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_viewpagerstep , container , false);

        updateViewPagerUi();
        return rootView;
    }

    private void updateViewPagerUi(){

       if(getArguments() != null){
           stepsArrayList = getArguments().getParcelableArrayList(STEPS_ARRAYLIST);
           currentPosition = getArguments().getInt(STEP_ID);
       }

        @SuppressWarnings("ConstantConditions")
        StepPagerAdapter mStepPagerAdapter = new StepPagerAdapter(getActivity().getSupportFragmentManager() , new ArrayList<Steps>(0));

        mStepPagerAdapter.setSteps(stepsArrayList);

        ViewPager mViewPager = rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mStepPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
