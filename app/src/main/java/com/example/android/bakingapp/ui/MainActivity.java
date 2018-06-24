package com.example.android.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.api.BakingAPI;
import com.example.android.bakingapp.api.EndPoint;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Connectivity;
import com.example.android.bakingapp.widget.BakingWidgetProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bakingapp.utils.StringUtils.RECIPE_LIST_STATE;
import static com.example.android.bakingapp.utils.StringUtils.RECYCLER_RECIPE_STATE;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecipeAdapter.RecipeClickListener {

    ArrayList<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
    RecyclerView recipeView;
    SwipeRefreshLayout swipe;
    ProgressBar loadingIndicator;
    TextView errorMessage;
    Parcelable recipeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateRecipeUi(savedInstanceState);
        getApi();

    }

    private void getApi() {

        EndPoint api = BakingAPI.getRequest();
        Call<ArrayList<Recipe>> response = api.getRecipes();

        response.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipeList.addAll(response.body());
                }
                swipe.setRefreshing(false);
                loadingIndicator.setVisibility(View.GONE);
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                if (!Connectivity.internetAvailable(getApplicationContext())) {
                    swipe.setRefreshing(false);
                    loadingIndicator.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText(getString(R.string.no_internet_connection));
                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText(getString(R.string.no_download_data));
                }
            }
        });

    }

    private void updateRecipeUi(Bundle savedInstanceState) {
        swipe = findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);

        errorMessage = findViewById(R.id.empty_text);
        loadingIndicator = findViewById(R.id.loading_spinner);
        recipeView = findViewById(R.id.recycler_recipe);

        if(savedInstanceState != null){
            recipeState = savedInstanceState.getParcelable(RECYCLER_RECIPE_STATE);
            recipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST_STATE);
            loadingIndicator.setVisibility(View.GONE);
        } else {
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recipeView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            recipeView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        recipeView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeList, this);
        recipeView.setAdapter(recipeAdapter);

    }

    @Override
    public void onRefresh() {
        if (recipeList.size() != 0) {
            recipeList.clear();
            getApi();
        }
    }

    private void addWidget(Recipe recipe) {
        Intent sendWidget = new Intent(this, BakingWidgetProvider.class);
        sendWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        sendWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        ArrayList<Ingredients> ingredientsArrayList = recipe.getIngredient();

        @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder") StringBuilder builder = new StringBuilder();
        for (Ingredients ingredients : ingredientsArrayList) {
            builder.append(" * ").append(ingredients.getIngredient()).append("\n").append(ingredients.getQuantity()).append(" --> ").append(ingredients.getMeasure());
            builder.append("\n");
        }

        BakingWidgetProvider.title = recipe.getName();
        BakingWidgetProvider.listIngredients = builder.toString();
        sendBroadcast(sendWidget);
    }

    @Override
    public void onRecipeClickFavorite(Recipe recipe, FloatingActionButton fabFavorite) {
        if (!recipe.getFavorite()) {
            fabFavorite.setImageResource(R.drawable.icon_fill_fab);
            addWidget(recipe);
            recipe.setFavorite(true);
            Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
        } else {
            fabFavorite.setImageResource(R.drawable.icon_fab);
            recipe.setFavorite(false);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_RECIPE_STATE , recipeView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(RECIPE_LIST_STATE , recipeList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        updateRecipeUi(savedInstanceState);
    }
}
