package com.itoo.ohue.journalapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.itoo.ohue.journalapp.view.CreateJournalActivity;
import com.itoo.ohue.journalapp.view.JournalActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class SubmitButtonTest {

    @Rule
    public ActivityTestRule<CreateJournalActivity> createJournalActivityActivityTestRule =
            new ActivityTestRule<>(CreateJournalActivity.class);

    @Test
    public void checkClick() {
        onView(withId(R.id.save_button)).perform(click());
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }
}
