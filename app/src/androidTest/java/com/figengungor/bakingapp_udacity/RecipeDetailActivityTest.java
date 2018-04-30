package com.figengungor.bakingapp_udacity;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.figengungor.bakingapp_udacity.data.model.Recipe;
import com.figengungor.bakingapp_udacity.data.model.Step;
import com.figengungor.bakingapp_udacity.ui.recipeDetail.RecipeDetailActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;
import java.util.ArrayList;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by figengungor on 4/30/2018.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RecipeDetailActivity.class);
                    Recipe recipe = new Recipe();
                    recipe.setName("Nutella");
                    ArrayList<Step> steps = new ArrayList<>();
                    Step step = new Step();
                    step.setDescription("Description");
                    step.setShortDescription("Short Description");
                    step.setThumbnailURL("");
                    step.setVideoURL("");
                    steps.add(new Step());
                    recipe.setSteps(steps);
                    result.putExtra(RecipeDetailActivity.EXTRA_RECIPE, Parcels.wrap(recipe));
                    return result;
                }
            };

    @Test
    public void initialSetup_loadAllFragmentsAccordingToDeviceType() {
        boolean isTablet = getInstrumentation().getTargetContext().getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            onView(withId(R.id.ingredientsContainerFl)).check(matches(isDisplayed()));
            onView(withId(R.id.stepsContainerFl)).check(matches(isDisplayed()));
            onView(withId(R.id.stepDetailContainerFl)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.ingredientsContainerFl)).check(matches(isDisplayed()));
            onView(withId(R.id.stepsContainerFl)).check(matches(isDisplayed()));
        }
    }

}
