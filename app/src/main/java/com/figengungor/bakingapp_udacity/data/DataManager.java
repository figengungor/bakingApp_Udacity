package com.figengungor.bakingapp_udacity.data;

import com.figengungor.bakingapp_udacity.data.model.Recipe;
import com.figengungor.bakingapp_udacity.data.remote.BakingService;
import com.figengungor.bakingapp_udacity.data.remote.BakingServiceFactory;
import java.util.List;
import retrofit2.Callback;

/**
 * Created by figengungor on 4/17/2018.
 */

public class DataManager {

    private static DataManager instance;
    private BakingService bakingService;

    private DataManager() {
        this.bakingService = BakingServiceFactory.createService();
    }

    public static DataManager getInstance() {
        if (instance == null) instance = new DataManager();
        return instance;
    }

    public void getRecipes(Callback<List<Recipe>> callback) {
        bakingService.getRecipes().enqueue(callback);
    }
}
