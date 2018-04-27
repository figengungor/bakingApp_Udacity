package com.figengungor.bakingapp_udacity.ui.recipes;

import android.arch.lifecycle.ViewModelProvider;

import com.figengungor.bakingapp_udacity.data.DataManager;

/**
 * Created by figengungor on 4/17/2018.
 */

public class RecipesViewModelFactory implements ViewModelProvider.Factory {

    DataManager dataManager;

    public RecipesViewModelFactory(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public RecipesViewModel create(Class modelClass) {
        return new RecipesViewModel(dataManager);
    }
}
