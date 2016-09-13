package com.thomasvansevenant.criminalnerds;

import android.content.Intent;
import android.os.Build;

import com.thomasvansevenant.criminalnerds.Activities.CrimeListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

/**
 * Created by ThomasVansevenant on 9/01/2016.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class CrimeListActivityTest {
    // ActivityController is a Robolectric class that drives the Activity lifecycle
    private ActivityController<CrimeListActivity> controller;
    private CrimeListActivity activity;

    @Before
    public void setup() {
        // Call the "buildActivity" method so we get an ActivityController which we can use
        // to have more control over the activity lifecycle
        controller = Robolectric.buildActivity(CrimeListActivity.class);

    }

    // Test that simulates the full lifecycle of an activity
    @Test
    public void createsAndDestroysActivity() {
        createWithIntent("my extra_value");


        // ... add assertions ...
    }

    // Activity creation that allows intent extras to be passed in
    private void createWithIntent(String extra) {
        Intent intent = new Intent(RuntimeEnvironment.application, CrimeListActivity.class);
        intent.putExtra("activity_extra", extra);

        activity = controller
                .withIntent(intent)//
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }

}
