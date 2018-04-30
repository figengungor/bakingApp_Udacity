package com.figengungor.bakingapp_udacity;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.figengungor.bakingapp_udacity.data.model.Step;
import com.figengungor.bakingapp_udacity.ui.stepDetail.StepDetailActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;
import java.util.ArrayList;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by figengungor on 5/1/2018.
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {

    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityTestRule =
            new ActivityTestRule<StepDetailActivity>(StepDetailActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, StepDetailActivity.class);
                    ArrayList<Step> steps = new ArrayList<>();
                    Step step = new Step();
                    step.setDescription("Description");
                    step.setShortDescription("Short Description");
                    step.setThumbnailURL("");
                    step.setVideoURL("");
                    steps.add(new Step());
                    steps.add(new Step());
                    steps.add(new Step());
                    result.putExtra(StepDetailActivity.EXTRA_STEPS, Parcels.wrap(steps));
                    result.putExtra(StepDetailActivity.EXTRA_STEP_INDEX, 0);
                    result.putExtra(StepDetailActivity.EXTRA_RECIPE_NAME, "Nutella");
                    return result;
                }
            };

    @Test
    public void setupLayout_loadFragment(){
        onView(withId(R.id.stepDetailContainerFl)).check(matches(isDisplayed()));
    }

    @Test
    public void stepIndexZero_onlyNextBtnIsVisible(){
        onView(withId(R.id.nextStepBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.previousStepBtn)).check(matches(not((isDisplayed()))));
    }

    @Test
    public void clickNextBtnWhenIndexIsZero_nextAndPreviousButtonsAreVisible(){
        onView(withId(R.id.nextStepBtn)).perform(click());
        onView(withId(R.id.nextStepBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.previousStepBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void clickNextBtnUntilLastStep_onlyPreviousBtnIsVisible(){
        onView(withId(R.id.nextStepBtn)).perform(click());
        onView(withId(R.id.nextStepBtn)).perform(click());
        onView(withId(R.id.nextStepBtn)).check(matches(not((isDisplayed()))));
        onView(withId(R.id.previousStepBtn)).check(matches(isDisplayed()));
    }

}
