package com.figengungor.bakingapp_udacity.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

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
