
package com.example.android.bakingapp.ui;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Bundle data = getIntent().getExtras();
        singleStep = data.getParcelable(STEPS);

        if (singleStep != null) {
            int stepID = singleStep.getId();


            singleStepArray = data.getParcelableArrayList(STEPS_ARRAYLIST);
            data.putParcelableArrayList(STEPS_ARRAYLIST, singleStepArray);
            data.putInt(STEP_ID, stepID);

            ViewPagerStepFragment pagerStepFragment = ViewPagerStepFragment.newInstance(singleStep.getId());
            pagerStepFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, pagerStepFragment)
                    .commit();
        }

    }

}