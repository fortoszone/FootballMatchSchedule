package com.dicoding.fort0.footballmatchschedule.schedule

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.dicoding.fort0.footballmatchschedule.Inject
import com.dicoding.fort0.footballmatchschedule.R
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before

@LargeTest
@RunWith(AndroidJUnit4::class)
class ScheduleActivityTest {

    @Rule
    @JvmField
    var scheduleActivityTest = object : androidx.test.rule.ActivityTestRule<ScheduleActivity>(ScheduleActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            Inject.provideRepoSport(InstrumentationRegistry.getTargetContext()).deleteAllFavorites()
        }
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(scheduleActivityTest.activity.countingIdlingResource)
    }

    private fun <T> first(matcher: Matcher<T>): Matcher<T> {
        return object : BaseMatcher<T>() {
            override fun describeTo(description: Description?) {
            }

            var isFirst = true

            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }

                return false
            }

        }
    }

    @Test
    fun AddLastMatchToFavorites() {
        Thread.sleep(2000)

        // get into one of the last match
        onView(first(withId(R.id.schedule_list))).perform(click())
        onView(first(withContentDescription("Favorite"))).perform(click())

        pressBack()

        // go to favorite menu
        onView(first(withContentDescription(R.string.fav_menu))).perform(click())
        onView(first(withId(R.id.schedule_list))).check(matches(isDisplayed()))

        // delete that matches from favorite
        onView(first(withId(R.id.schedule_list))).perform(click())
        onView(first(withContentDescription("Favorite"))).perform(click())

        pressBack()

        onView(first(withId(R.id.schedule_list))).check(doesNotExist())
    }
}