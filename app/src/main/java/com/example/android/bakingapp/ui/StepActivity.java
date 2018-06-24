
package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.ViewPagerStepFragment;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

import static com.example.android.bakingapp.utils.StringUtils.STEPS;
import static com.example.android.bakingapp.utils.StringUtils.STEPS_ARRAYLIST;
import static com.example.android.bakingapp.utils.StringUtils.STEP_ID;

public class StepActivity extends AppCompatActivity {

    ArrayList<Steps> singleStepArray;
    Steps singleStep;
    int stepID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if(savedInstanceState == null){
            Intent intent = getIntent();
            singleStep = intent.getParcelableExtra(STEPS);
            singleStepArray = intent.getParcelableArrayListExtra(STEPS_ARRAYLIST);
            stepID = singleStep.getId();

            Bundle data = new Bundle();
            data.putParcelable(STEPS , singleStep);
            data.putParcelableArrayList(STEPS_ARRAYLIST, singleStepArray);
            data.putInt(STEP_ID, stepID);

            ViewPagerStepFragment pagerStepFragment = ViewPagerStepFragment.newInstance(singleStep.getId());
            pagerStepFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, pagerStepFragment)
                    .commit();
        } else {
          singleStep = savedInstanceState.getParcelable(STEPS);
          stepID = savedInstanceState.getInt(STEP_ID);
          singleStepArray = savedInstanceState.getParcelableArrayList(STEPS_ARRAYLIST);
          }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEPS , singleStep);
        outState.putInt(STEP_ID , stepID);
        outState.putParcelableArrayList(STEPS_ARRAYLIST , singleStepArray);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        singleStep = savedInstanceState.getParcelable(STEPS);
        stepID = savedInstanceState.getInt(STEP_ID);
        singleStepArray = savedInstanceState.getParcelableArrayList(STEPS_ARRAYLIST);
    }
}