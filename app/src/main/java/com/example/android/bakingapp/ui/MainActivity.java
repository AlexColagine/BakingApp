package com.example.android.bakingapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.api.BakingAPI;
import com.example.android.bakingapp.api.EndPoint;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Connectivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
    RecyclerView recipeView;
    SwipeRefreshLayout swipe;
    ProgressBar loadingIndicator;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateRecipeUi();
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
                if (!Connectivity.internetAvailable(getApplicationContext())){
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

    private void updateRecipeUi() {
        swipe = findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);

        errorMessage = findViewById(R.id.empty_text);
        recipeView = findViewById(R.id.recycler_recipe);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            recipeView.setLayoutManager(new GridLayoutManager(getApplicationContext() , 2));
        } else {
            recipeView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        recipeView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeList);
        recipeView.setAdapter(recipeAdapter);

        loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        if (recipeList.size() != 0) {
            recipeList.clear();
            getApi();
        }
    }
}
