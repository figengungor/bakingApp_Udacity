package com.figengungor.bakingapp_udacity.ui.recipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.DataManager;
import com.figengungor.bakingapp_udacity.data.model.Recipe;
import com.figengungor.bakingapp_udacity.ui.recipeDetail.RecipeDetailActivity;
import com.figengungor.bakingapp_udacity.utils.ErrorUtils;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesActivity extends AppCompatActivity implements RecipeAdapter.ItemListener {

    private static final String TAG = RecipesActivity.class.getSimpleName();
    @BindView(R.id.recipeRv)
    RecyclerView recipeRv;

    @BindView(R.id.messageLayout)
    ConstraintLayout messageLayout;

    @BindView(R.id.messageTv)
    TextView messageTv;

    @BindView(R.id.loadingPw)
    ProgressWheel loadingPw;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @OnClick(R.id.retryBtn)
    void onRetryBtnClicked() {
        viewModel.getRecipes();
    }

    RecipesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        viewModel = ViewModelProviders.of(this, new RecipesViewModelFactory(DataManager.getInstance()))
                .get(RecipesViewModel.class);

        viewModel.getRecipesLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes != null) showRecipes(recipes);
                else recipeRv.setVisibility(View.GONE);
            }
        });

        viewModel.getIsLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                Log.d(TAG, "onChanged: getIsLoading -> " + isLoading);
                showLoadingIndicator(isLoading);
            }
        });

        viewModel.getErrorLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                Log.d(TAG, "onChanged: getError -> " + throwable);
                if (throwable != null) showError(throwable);
                else messageLayout.setVisibility(View.GONE);

            }
        });

        setupUI();
    }

    private void setupUI() {
        setupLayoutManager();
        loadData();
    }

    private void setupLayoutManager() {
        int spanCount = getResources().getInteger(R.integer.spanCount);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recipeRv.setLayoutManager(gridLayoutManager);
    }

    private void loadData() {
        if (viewModel.getRecipesLiveData().getValue() == null) {
            viewModel.getRecipes();
            Log.d(TAG, "loadData: fetch recipes");
        }
    }


    private void showRecipes(List<Recipe> recipes) {
        recipeRv.setVisibility(View.VISIBLE);
        messageLayout.setVisibility(View.GONE);
        RecipeAdapter adapter = new RecipeAdapter(recipes, this);
        recipeRv.setAdapter(adapter);
    }

    private void showLoadingIndicator(Boolean isLoading) {
        if (isLoading) {
            loadingPw.setVisibility(View.VISIBLE);
        } else {
            loadingPw.setVisibility(View.GONE);
        }
    }

    private void showError(Throwable throwable) {
        recipeRv.setVisibility(View.GONE);
        messageLayout.setVisibility(View.VISIBLE);
        retryBtn.setVisibility(View.VISIBLE);
        messageTv.setText(ErrorUtils.displayFriendlyErrorMessage(this, throwable));
    }


    @Override
    public void onItemClick(Recipe recipe) {
        startActivity(new Intent(this, RecipeDetailActivity.class)
                .putExtra(RecipeDetailActivity.EXTRA_RECIPE, Parcels.wrap(recipe)));
    }
}
