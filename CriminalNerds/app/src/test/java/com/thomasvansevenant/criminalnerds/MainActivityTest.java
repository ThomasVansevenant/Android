package com.thomasvansevenant.criminalnerds;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.thomasvansevenant.criminalnerds.Activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest extends FragmentActivity{
    // ActivityController is a Robolectric class that drives the Activity lifecycle
    private ActivityController<MainActivity> controller;
    private MainActivity activity;


    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(MainActivity.class);

        // Call the "buildActivity" method so we get an ActivityController which we can use
        // to have more control over the activity lifecycle
        controller = Robolectric.buildActivity(MainActivity.class);
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void validateTextViewContent() {
        TextView tvHelloWorld = (TextView) activity.findViewById(R.id.tvHelloWorld);
        assertNotNull("TextView could not be found", tvHelloWorld);
        assertTrue("TextView contains incorrect text",
                "Hello world!".equals(tvHelloWorld.getText().toString()));
    }


    // Activity creation that allows intent extras to be passed in
    private void createWithIntent(String extra) {
        Intent intent = new Intent(RuntimeEnvironment.application, MainActivity.class);
        intent.putExtra("activity_extra", extra);
        activity = controller
                .withIntent(intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }

    // Test that simulates the full lifecycle of an activity
    @Test
    public void createsAndDestroysActivity() {
        createWithIntent("my extra_value");

        // ... add assertions ...
    }

    // @After => JUnit 4 annotation that specifies this method should be run after each test
    @After
    public void tearDown() {
        // Destroy activity after every test
        controller
                .pause()
                .stop()
                .destroy();
    }


//    @Test
//    public void recreatesActivity() {
//        Bundle bundle = new Bundle();
//
//        // Destroy the original activity
//        controller
//                .saveInstanceState(bundle)
//                .pause()
//                .stop()
//                .destroy();
//
//        // Bring up a new activity
//        controller = Robolectric.buildActivity(MainActivity.class)
//                .create(bundle)
//                .start()
//                .restoreInstanceState(bundle)
//                .resume()
//                .visible();
//        activity = controller.get();
//
//        // ... add assertions ...
//    }
}