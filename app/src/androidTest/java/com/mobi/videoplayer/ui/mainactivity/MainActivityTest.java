package com.mobi.videoplayer.ui.mainactivity;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.mobi.videoplayer.R;

import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkViewsAvailability() {
        assertNotNull(onView(withId(R.id.edit_abbreviation)));
        assertNotNull(onView(withId(R.id.btn_show_result)));
        assertNotNull(onView(withId(R.id.progress_bar)));
        assertNotNull(onView(withId(R.id.tv_status)));
        assertNotNull(onView(withId(R.id.rv_acronym)));
    }

    @Test
    public void validateEditText() {
        onView(withId(R.id.edit_abbreviation)).perform(typeText("TV")).check(matches(withText("TV")));
    }

    @Test
    public void validateButton() {
        onView(withId(R.id.edit_abbreviation)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText("TV"), closeSoftKeyboard());
        onView(withId(R.id.btn_show_result)).perform(click());
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    @Test
    public void onViewClickedWhenInternetAvailable() {
        onView(withId(R.id.btn_show_result)).perform(click());
        onView(withId(R.id.tv_status)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void onViewClickedWhenWithEmptyAcronym() {
        onView(withId(R.id.edit_abbreviation)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btn_show_result)).perform(click());
        onView(withId(R.id.tv_status)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }


    @Test
    public void showRecyclerView() {
        onView(withId(R.id.btn_show_result)).perform(click());
        onView(withId(R.id.rv_acronym)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void loadDataInRecyclerView() throws InterruptedException {
        onView(withId(R.id.edit_abbreviation)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText("TV"), closeSoftKeyboard());
        onView(withId(R.id.btn_show_result)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.tv_status)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void showRecyclerViewWithEmptyList() throws InterruptedException {
        onView(withId(R.id.edit_abbreviation)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText("dfkdjkfjfd"), closeSoftKeyboard());
        onView(withId(R.id.btn_show_result)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.tv_status)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void showStatusTest() throws InterruptedException {
        onView(withId(R.id.edit_abbreviation)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText("dfkdjkfjfdui"), closeSoftKeyboard());
        onView(withId(R.id.btn_show_result)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.tv_status)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void showProgressBar() {
        onView(withId(R.id.edit_abbreviation)).perform(ViewActions.clearText())
                .perform(ViewActions.typeText("AB"), closeSoftKeyboard());
        onView(withId(R.id.btn_show_result)).perform(click());
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}