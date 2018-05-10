package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsFragment;
import com.example.android.bakingapp.fragments.StepsFragment;
import com.example.android.bakingapp.model.Recipe;

import static com.example.android.bakingapp.Utils.RECIPE;

public class RecipeActivity extends AppCompatActivity {

    private IngredientsFragment ingredientsFragment;
    private StepsFragment stepsFragment;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            //noinspection ConstantConditions
            getSupportActionBar().setTitle(recipe.getName());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        ingredientsFragment = IngredientsFragment.newInstance(recipe.getIngredient());
        stepsFragment = StepsFragment.newInstance(recipe.getStep());


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

        TabLayout tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(RecipeActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class RecipePagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        private IngredientsFragment ingredientsFragment;
        private StepsFragment stepsFragment;

        public RecipePagerAdapter(FragmentManager fm ,
                                    IngredientsFragment ingredientsFragment,
                                    StepsFragment stepsFragment){
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
            switch(position){
                case 0:
                    return getString(R.string.fragment_ingredients);
                case 1:
                    return getString(R.string.fragment_steps);
            }
            return null;
        }
    }
}
