package com.figengungor.bakingapp_udacity.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.figengungor.bakingapp_udacity.data.model.Recipe;
import com.figengungor.bakingapp_udacity.ui.recipes.RecipesActivity;
import com.google.gson.Gson;

/**
 * The configuration screen for the {@link BakingWidgetProvider BakingWidgetProvider} AppWidget.
 */
public class BakingWidgetProviderConfigureActivity extends RecipesActivity {

    private static final String PREFS_NAME = "com.figengungor.bakingapp_udacity.widget.BakingWidgetProvider";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public BakingWidgetProviderConfigureActivity() {
        super();
    }

    static void saveRecipePref(Context context, int appWidgetId, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        Gson gson = new Gson();
        String recipeJson = gson.toJson(recipe);
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, recipeJson);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static Recipe loadRecipePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String recipeJson = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);

        if (recipeJson != null) {
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(recipeJson, Recipe.class);
            return recipe;
        } else {
            return null;
        }
    }

    static void deleteRecipePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
    }

    @Override
    public void onItemClick(Recipe recipe) {
        final Context context = BakingWidgetProviderConfigureActivity.this;

        // When the button is clicked, store the string locally
        saveRecipePref(context, mAppWidgetId, recipe);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingWidgetProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}

