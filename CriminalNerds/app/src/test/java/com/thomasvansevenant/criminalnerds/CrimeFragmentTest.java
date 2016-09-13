//package com.thomasvansevenant.criminalnerds;
//
//import android.app.Activity;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.EditText;
//
//import com.thomasvansevenant.criminalnerds.Activities.CrimeListActivity;
//import com.thomasvansevenant.criminalnerds.Fragments.CrimeFragment;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricGradleTestRunner;
//import org.robolectric.RuntimeEnvironment;
//import org.robolectric.annotation.Config;
//import org.robolectric.shadows.ShadowLog;
//import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;
//import org.robolectric.util.ActivityController;
//import org.robolectric.util.FragmentController;
//
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertNotNull;
//import static junit.framework.Assert.assertNotSame;
//
///**
// * Created by ThomasVansevenant on 9/01/2016.
// */
//@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
//@RunWith(RobolectricGradleTestRunner.class)
//public class CrimeFragmentTest {
////   private static final int EDIT_TEXT_ID = R.id.edit_text;
//private static final String EDIT_TEXT_VALUE = "this text value should be restored";
//
//    private static final int EDIT_TEXT_ID = R.id.crime_title;
//    private static final String EDIT_TEXT_VALUE = "this text value should be restored";
//    private ActivityController<CrimeListActivity> controller;
//
//    @Before
//    public void setupFreshFixture() {
//        controller = Robolectric.buildActivity(CrimeListActivity.class).setup();
//    }
//
//    @Test
//    public void test() {
//        setEditText(controller.get(), EDIT_TEXT_ID, EDIT_TEXT_VALUE);
//        final Bundle state = restartActivity();
//        assertEquals(EDIT_TEXT_VALUE, state.getString(CrimeFragment.EDIT_TEXT_KEY));
//        assertEquals(EDIT_TEXT_VALUE, getEditText(controller.get(), EDIT_TEXT_ID));
//    }
//
//    private Bundle restartActivity() {
//        final Bundle state = new Bundle();
//        controller.saveInstanceState(state).stop().destroy();
//        controller = Robolectric.buildActivity(NotificationActivity.class).setup(state);
//        return state;
//    }
//
//    //TODO move to utilities
//    public static String getEditText(final Activity activity, final int id) {
//        return ((EditText) activity.findViewById(id)).getText().toString();
//    }
//
//    public static void setEditText(final Activity activity, final int id, final String value) {
//        ((EditText) activity.findViewById(id)).setText(value);
//    }
//}
