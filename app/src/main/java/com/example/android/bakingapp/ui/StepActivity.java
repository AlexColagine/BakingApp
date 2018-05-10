
package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.SingleStepFragment;
import com.example.android.bakingapp.model.Steps;

import java.util.ArrayList;

import static com.example.android.bakingapp.Utils.STEPS;

public class StepActivity extends AppCompatActivity {

    SingleStepFragment singleStepFragment;
    ArrayList<Steps> singleStepArray = new ArrayList<>(0);
    Steps singleStep;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle data = getIntent().getExtras();
        singleStep = data.getParcelable(STEPS);

        if (singleStep != null) {
            setTitle(singleStep.getShortDescription());
            currentPosition = singleStep.getId();
            //FragmentManager fragmentManager = getSupportFragmentManager();
            singleStepFragment = SingleStepFragment.newInstance(singleStep.getDescription(),
                    singleStep.getVideo(),
                    singleStep.getThumbnail());
            singleStepFragment.setArguments(data);

        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        StepPagerAdapter mStepPagerAdapter = new StepPagerAdapter(getSupportFragmentManager(), singleStepArray);


        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
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

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(StepActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
                    stepsArrayList.get(position).getThumbnail());
        }

        @Override
        public int getCount() {
            return stepsArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stepsArrayList.get(position).getShortDescription();
        }
    }
}