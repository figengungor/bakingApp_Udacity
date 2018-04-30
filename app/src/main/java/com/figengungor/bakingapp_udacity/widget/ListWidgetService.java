package com.figengungor.bakingapp_udacity.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Ingredient;
import com.figengungor.bakingapp_udacity.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> ingredients;
    private Context context = null;
    private int appWidgetId;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private void setIngredientsData() {
        Recipe recipe = BakingWidgetProviderConfigureActivity.loadRecipePref(context, appWidgetId);
        if (recipe != null) {
            ingredients = recipe.getIngredients();
        } else {
            ingredients = new ArrayList<>();
        }
    }

    @Override
    public void onCreate() {
        setIngredientsData();
    }

    @Override
    public void onDataSetChanged() {
        setIngredientsData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients.size() > 0) return ingredients.size();
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.item_ingredient_widget);
        Ingredient ingredient = ingredients.get(position);
        remoteView.setTextViewText(R.id.ingredientInfoTv, context.getString(R.string.ingredient,
                String.valueOf(ingredient.getQuantity()), ingredient.getMeasure(), ingredient.getIngredient()));
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}