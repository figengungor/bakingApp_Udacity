package com.figengungor.bakingapp_udacity.ui.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Recipe;
import com.squareup.picasso.Picasso;

import net.wujingchao.android.view.SimpleTagImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by figengungor on 4/17/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    List<Recipe> recipes;
    ItemListener itemListener;

    interface ItemListener {
        void onItemClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipes, ItemListener itemListener) {
        this.recipes = recipes;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindItem(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeNameTv)
        TextView recipeNameTv;
        @BindView(R.id.simpleTagIv)
        SimpleTagImageView simpleTagIv;

        @OnClick(R.id.recipeContainerCv)
        public void onItemClicked() {
            itemListener.onItemClick(recipes.get(getAdapterPosition()));
        }

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Recipe recipe) {
            recipeNameTv.setText(recipe.getName());
            simpleTagIv.setTagText(itemView.getContext().getString(R.string.servings, recipe.getServings()));
            if (!TextUtils.isEmpty(recipe.getImage()))
                Picasso.with(itemView.getContext()).load(recipe.getImage())
                        .placeholder(R.drawable.food_repeating)
                        .error(R.drawable.food_repeating)
                        .into(simpleTagIv);
        }
    }
}
