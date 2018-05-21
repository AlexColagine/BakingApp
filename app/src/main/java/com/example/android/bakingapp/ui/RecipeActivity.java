package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsFragment;
import com.example.android.bakingapp.fragments.StepsFragment;
import com.example.android.bakingapp.model.Recipe;

import static com.example.android.bakingapp.utils.StringUtils.RECIPE;

public class RecipeActivity extends AppCompatActivity /*implements StepsAdapter.StepClickListener */ {

    private IngredientsFragment ingredientsFragment;
    private StepsFragment stepsFragment;
    boolean mTablet;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); */
        /**
         * Create the adapter that will return a fragment for each of the three
         * primary sections of the activity.
         *
         * The {@link android.support.v4.view.PagerAdapter} that will provide
         * fragments for each of the sections. We use a
         * {@link FragmentPagerAdapter} derivative, which will keep every
         * loaded fragment in memory. If this becomes too memory intensive, it
         * may be best to switch to a
         * {@link android.support.v4.app.FragmentStatePagerAdapter}.
         * */

        Bundle data = getIntent().getExtras();
        recipe = data.getParcelable(RECIPE);

        if (recipe != null && recipe.getName() != null) {
            setTitle(recipe.getName());
            ingredientsFragment = IngredientsFragment.newInstance(recipe.getIngredient());
        }


        if (findViewById(R.id.step_container) != null) {
            mTablet = true;

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_ingredients, ingredientsFragment)
                    .commit();

            stepsFragment = StepsFragment.newInstance(recipe.getStep(), mTablet);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_steps, stepsFragment)
                    .commit();

        } else {
            mTablet = false;

            stepsFragment = StepsFragment.newInstance(recipe.getStep(), mTablet);
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipePagerAdapter mRecipePagerAdapter = new RecipePagerAdapter(fragmentManager,
                    ingredientsFragment,
                    stepsFragment);
            /**
             * Set up the ViewPager with the sections adapter.
             *
             * The {@link ViewPager} that will host the section contents.
             */
            ViewPager mViewPager = findViewById(R.id.container);
            mViewPager.setAdapter(mRecipePagerAdapter);

            TabLayout tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(RecipeActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    private void setupUiTablet(){
        setupIngredientUiTablet();
        setupStepUiTablet();
    }

    private void setupIngredientUiTablet(){
        IngredientsAdapter ingredientsAdapter;
        ArrayList<Ingredients> ingredientsArrayList = recipe.getIngredient();
        RecyclerView ingredientsView = findViewById(R.id.rv_tablet_ingredients);

        ingredientsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ingredientsView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ingredientsView.setHasFixedSize(true);

        ingredientsAdapter = new IngredientsAdapter(this, ingredientsArrayList);
        ingredientsView.setAdapter(ingredientsAdapter);
    }

    private void setupStepUiTablet(){
        StepsAdapter stepsAdapter;
        stepsArrayList = recipe.getStep();
        RecyclerView stepsView = findViewById(R.id.rv_tablet_steps);

        stepsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        stepsView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        stepsView.setHasFixedSize(true);

        stepsAdapter = new StepsAdapter(this, stepsArrayList , this);
        stepsView.setAdapter(stepsAdapter);
    }

*/
/*    @Override
    public void stepItemClick(Steps steps) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle data = new Bundle();
        data.putParcelableArrayList(STEPS_ARRAYLIST , stepsArrayList);
        data.putInt(STEP_ID , steps.getId());

        ViewPagerStepFragment pagerStepFragment = new ViewPagerStepFragment();
        pagerStepFragment.setArguments(data);
        fragmentManager.beginTransaction()
                .replace(R.id.tablet_step_container , pagerStepFragment)
                .commit();
    }  */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class RecipePagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        private IngredientsFragment ingredientsFragment;
        private StepsFragment stepsFragment;

        public RecipePagerAdapter(FragmentManager fm,
                                  IngredientsFragment ingredientsFragment,
                                  StepsFragment stepsFragment) {
            super(fm);
            this.ingredientsFragment = ingredientsFragment;
            this.stepsFragment = stepsFragment;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ingredientsFragment;
                case 1:
                    return stepsFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.fragment_ingredients);
                case 1:
                    return getString(R.string.fragment_steps);
            }
            return null;
        }
    }
}
