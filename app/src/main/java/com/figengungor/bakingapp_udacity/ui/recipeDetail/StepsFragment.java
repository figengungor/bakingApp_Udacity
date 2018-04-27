package com.figengungor.bakingapp_udacity.ui.recipeDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Step;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by figengungor on 4/19/2018.
 */

public class StepsFragment extends Fragment implements StepAdapter.ItemListener {

    @BindView(R.id.stepsRv)
    RecyclerView stepsRv;

    private static final String ARG_STEPS = "steps";
    private static final String KEY_SELECTED_STEP_INDEX = "selected_step_index";
    List<Step> steps;

    OnInteractionListener callback;
    StepAdapter adapter;
    int selectedStepIndex = 0;

    interface OnInteractionListener {
        void onStepClicked(int stepIndex);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        steps = Parcels.unwrap(getArguments().getParcelable(ARG_STEPS));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, view);
        adapter = new StepAdapter(steps, this);
        stepsRv.setAdapter(adapter);
        stepsRv.setNestedScrollingEnabled(false);
        if (savedInstanceState != null) {
            selectedStepIndex = savedInstanceState.getInt(KEY_SELECTED_STEP_INDEX);
            adapter.setSelectedStep(selectedStepIndex);
        }
        return view;
    }

    public static StepsFragment newInstance(List<Step> steps) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEPS, Parcels.wrap(steps));
        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnInteractionListener");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_SELECTED_STEP_INDEX, selectedStepIndex);
    }

    @Override
    public void onItemClick(int stepIndex) {
        selectedStepIndex = stepIndex;
        adapter.setSelectedStep(stepIndex);
        callback.onStepClicked(stepIndex);
    }

    public void resetStep() {
        adapter.setSelectedStep(-1);
    }
}
