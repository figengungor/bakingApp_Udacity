package com.figengungor.bakingapp_udacity.ui.recipeDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Ingredient;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by figengungor on 4/18/2018.
 */

public class IngredientsFragment extends Fragment {

    @BindView(R.id.ingredientRv)
    RecyclerView ingredientRv;

    private static final String ARG_INGREDIENTS = "ingredients";
    List<Ingredient> ingredients;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredients = Parcels.unwrap(getArguments().getParcelable(ARG_INGREDIENTS));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);
        IngredientAdapter adapter = new IngredientAdapter(ingredients);
        ingredientRv.setAdapter(adapter);
        ingredientRv.setNestedScrollingEnabled(false);
        return view;
    }

    public static IngredientsFragment newInstance(List<Ingredient> ingredients) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_INGREDIENTS, Parcels.wrap(ingredients));
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
