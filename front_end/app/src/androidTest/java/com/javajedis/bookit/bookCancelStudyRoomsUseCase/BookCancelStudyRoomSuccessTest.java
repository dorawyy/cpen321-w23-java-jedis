package com.javajedis.bookit.bookCancelStudyRoomsUseCase;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

import com.javajedis.bookit.MainActivity;
import com.javajedis.bookit.R;
import com.javajedis.bookit.model.TimeSlotsModel;
import com.javajedis.bookit.recyclerview.adapter.TimeSlotsRecyclerViewAdapter;
import com.javajedis.bookit.util.ToastMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.javajedis.bookit.model.BookingsModel;
import com.javajedis.bookit.recyclerview.adapter.BookingsRecyclerViewAdapter;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookCancelStudyRoomSuccessTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void bookCancelStudyRoomSuccessTest() {
        ViewInteraction ic = onView(
                allOf(withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.sign_in_button),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        ic.perform(click());

        // TODO: need to be tested
        // ChatGPT usage: Yes --> from here
        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation());
        // Wait for the Google Sign-In screen to appear
        uiDevice.waitForIdle();

        // Click on the first Google account in the account picker
        UiObject2 googleAccount = uiDevice.findObject(By.textContains("gmail")); // Modify the selector as needed

        // Click on the Google account
        if (googleAccount != null) {
            googleAccount.click();
        }

        // open search activity
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.search_button), withText("search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());
        // choose study rooms in search activity
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.study_rooms_button), withText("study rooms"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());
        // choose ESC building
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.building_recyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                2)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.room_names_recyclerview),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(3, click()));
        // click book now
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.book_now_button), withText("book now"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton3.perform(click());
        // choose November 30th
        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.calendarRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)));
        recyclerView3.perform(actionOnItemAtPosition(32, click()));
        // check time slots are displayed on screen
        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.timeslots_recycler_view),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        recyclerView4.check(matches(isDisplayed()));

        onView(withId(R.id.timeslots_recycler_view)).check(new recyclerViewItemCountAssertion(47));

        // click the last time slots 23:30 - 24:00
        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.timeslots_recycler_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView5.perform(actionOnItemAtPosition(47, click()));

//        ViewInteraction textView2 = onView(
//                allOf(withId(R.id.action_bookings_textView), withText("click to cancel"),
//                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
//                        isDisplayed()));
//        textView2.check(matches(withText("click to cancel")));
//
//        ViewInteraction textView3 = onView(
//                allOf(withId(R.id.timeslot_bookings_textView), withText("2330-2400"),
//                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
//                        isDisplayed()));
//        textView3.check(matches(withText("2330-2400")));
//
//        ViewInteraction textView4 = onView(
//                allOf(withId(R.id.date_bookings_textView), withText("30-11-2023"),
//                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
//                        isDisplayed()));
//        textView4.check(matches(withText("30-11-2023")));


        // assert if current time is before 2330-2400
        // ChatGPT usage: Yes --> from here
        // get current date
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = LocalDate.of(2023, 11, 30);

        if (currentDate.isBefore(targetDate)) {
            // Assert that the current date is before 30-11-2023
            assertTrue(currentDate.isBefore(targetDate));
        } else if (currentDate.isEqual(targetDate)){
            // Same dateAssert that the current time is before 23:30
            String currentTime = getCurrentTime();
            int currentHour = Integer.parseInt(currentTime.substring(0, 2));
            int currentMinute = Integer.parseInt(currentTime.substring(2));

            int targetHour = 23;
            int targetMinute = 30;

            // Convert both current time and target time to minutes for easy comparison
            int currentTimeInMinutes = currentHour * 60 + currentMinute;
            int targetTimeInMinutes = targetHour * 60 + targetMinute;

            assertTrue(currentTimeInMinutes < targetTimeInMinutes);
        } else {
            // Test fails
            fail("current time is after booking starting time for newly booked session");
        }

        // ChatGPT usage: Yes --> from here

        onView(withId(R.id.bookings_recyclerView)).check(new bookingsRecyclerViewItemCountAssertion(0));

        // TODO: need to be tested
        uiDevice.waitForIdle();

        // Click on the first bookings in the bookings page
        UiObject2 firstBooking = uiDevice.findObject(By.textContains("click to cancel")); // Modify the selector as needed

        // Click on the firstBooking
        if (firstBooking != null) {
            firstBooking.click();
        }

//        // click cancel booking
//        ViewInteraction recyclerView7 = onView(
//                allOf(withId(R.id.bookings_recyclerView),
//                        childAtPosition(
//                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
//                                0)));
//        recyclerView7.perform(actionOnItemAtPosition(0, click()));

        // From: https://www.qaautomated.com/2016/01/how-to-test-toast-message-using-espresso.html
        // check if the message appears with the text: “Your booking has been canceled!”
        onView(withText("Your booking has been canceled!")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    // ChatGPT usage: Yes
    public static class recyclerViewItemCountAssertion implements ViewAssertion {
        private final int position;

        public recyclerViewItemCountAssertion(int position) {
            this.position = position;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                // Assuming your adapter is YourAdapter
                assert adapter != null;
                TimeSlotsModel item = ((TimeSlotsRecyclerViewAdapter) adapter).getItemAtPosition(position);

                // Now you can perform assertions or actions based on the data in the item
                // For example, you can check a specific property of the item
                assertThat(item.getStatus(), is("book now"));
            } else {
                throw noViewFoundException;
            }
        }
    }

    // ChatGPT usage: Yes
    public static class bookingsRecyclerViewItemCountAssertion implements ViewAssertion {
        private final int position;

        public bookingsRecyclerViewItemCountAssertion(int position) {
            this.position = position;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                // Assuming your adapter is YourAdapter
                assert adapter != null;
                BookingsModel item = ((BookingsRecyclerViewAdapter) adapter).getItemAtPosition(position);

                // Now you can perform assertions or actions based on the data in the item
                // For example, you can check a specific property of the item
                assertThat(item.getAction(), is("click to cancel"));
            } else {
                throw noViewFoundException;
            }
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return currentTime.format(formatter);
    }
}
