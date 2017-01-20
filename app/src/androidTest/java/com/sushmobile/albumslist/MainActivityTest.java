package com.sushmobile.albumslist;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import com.sushmobile.albumslist.activities.MainActivity;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() throws Exception{
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
    }

    @Test
    public void testShouldLaunchTheMainActivityAndFindItemsInTheList() throws Exception
    {
        ListView listview = (ListView) mActivityRule.getActivity().findViewById(R.id.album_list);
        Assert.assertThat(listview.getCount(), CoreMatchers.is(100));
    }

    @Test
    public void testDataFlow() throws Exception
    {
        Espresso.onData(CoreMatchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.album_list)).atPosition(0).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.text_album_name_details))
                .check(ViewAssertions.matches(ViewMatchers.withText("quidem molestiae enim")));
    }

}
