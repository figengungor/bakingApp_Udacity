package com.figengungor.bakingapp_udacity.ui.recipeDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by figengungor on 4/18/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bindItem(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredients != null) return ingredients.size();
        return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredientInfoTv)
        TextView ingredientInfoTv;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Ingredient ingredient) {
            ingredientInfoTv.setText(itemView.getContext().getString(R.string.ingredient,
                    String.valueOf(ingredient.getQuantity()), ingredient.getMeasure(), ingredient.getIngredient()));
        }
    }

}
