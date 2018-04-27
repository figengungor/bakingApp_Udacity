package com.figengungor.bakingapp_udacity.ui.stepDetail;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Step;
import org.parceler.Parcels;
import java.util.List;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnInteractionListener {

    public static final String EXTRA_STEPS = "steps";
    public static final String EXTRA_STEP_INDEX = "step_index";
    public static final String EXTRA_RECIPE_NAME = "recipe_name";

    List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Bundle bundle = getIntent().getExtras();
        steps = Parcels.unwrap(bundle.getParcelable(EXTRA_STEPS));
        int stepIndex = bundle.getInt(EXTRA_STEP_INDEX);
        String recipeName = bundle.getString(EXTRA_RECIPE_NAME);

        getSupportActionBar().setTitle(getString(R.string.recipeStepsTitle, recipeName, stepIndex));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stepDetailContainerFl, StepDetailFragment.newInstance(steps, stepIndex))
                    .commit();
        }
    }

    @Override
    public void onPreviousStepClicked(int stepIndex) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.stepDetailContainerFl, StepDetailFragment.newInstance(steps, stepIndex - 1))
                .commit();
    }

    @Override
    public void onNextStepClicked(int stepIndex) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.stepDetailContainerFl, StepDetailFragment.newInstance(steps, stepIndex + 1))
                .commit();
    }
}
