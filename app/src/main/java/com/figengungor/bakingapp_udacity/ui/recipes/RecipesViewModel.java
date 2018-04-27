package com.figengungor.bakingapp_udacity.ui.recipes;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.figengungor.bakingapp_udacity.data.DataManager;
import com.figengungor.bakingapp_udacity.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.figengungor.bakingapp_udacity.utils.ErrorUtils.EMPTY;
import static com.figengungor.bakingapp_udacity.utils.ErrorUtils.SERVER_ERROR;

/**
 * Created by figengungor on 4/17/2018.
 */

public class RecipesViewModel extends ViewModel {

    MutableLiveData<List<Recipe>> recipesLiveData;
    MutableLiveData<Boolean> isLoadingLiveData;
    MutableLiveData<Throwable> errorLiveData;

    DataManager dataManager;

    public RecipesViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
        recipesLiveData = new MutableLiveData<>();
        isLoadingLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public void getRecipes() {
        isLoadingLiveData.setValue(true);
        recipesLiveData.setValue(null);
        errorLiveData.setValue(null);

        dataManager.getRecipes(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful()) {
                    List<Recipe> recipes = response.body();
                    if (recipes != null && recipes.size() > 0) {
                        recipesLiveData.setValue(recipes);
                    } else {
                        errorLiveData.setValue(new Throwable(EMPTY));
                    }
                } else {
                    errorLiveData.setValue(new Throwable(SERVER_ERROR));
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable throwable) {
                isLoadingLiveData.setValue(false);
                errorLiveData.setValue(throwable);
            }
        });
    }

    public MutableLiveData<List<Recipe>> getRecipesLiveData() {
        return recipesLiveData;
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public MutableLiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }
}
