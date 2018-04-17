package com.figengungor.bakingapp_udacity.data.remote;

import com.figengungor.bakingapp_udacity.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by figengungor on 4/17/2018.
 */

public interface BakingService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();


}
