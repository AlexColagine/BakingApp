package com.example.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingapp.fragments.SingleStepFragment;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

/**
 * Created by Alessandro on 13/05/2018.
 */

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class StepPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Steps> stepsArrayList;

    public StepPagerAdapter(FragmentManager fm, ArrayList<Steps> stepsArrayList) {
        super(fm);
        setSteps(stepsArrayList);
    }

    public void setSteps(@NonNull ArrayList<Steps> stepsArrayList) {
        this.stepsArrayList = stepsArrayList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return SingleStepFragment.newInstance(stepsArrayList.get(position).getDescription(),
                stepsArrayList.get(position).getVideo(),
                stepsArrayList.get(position).getThumbnail(),
                stepsArrayList.get(position).getShortDescription(),
                stepsArrayList.get(position).getId());
    }

    @Override
    public int getCount() {
        return (stepsArrayList != null) ? stepsArrayList.size() : 0;
    }

}