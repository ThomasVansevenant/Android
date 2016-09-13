package com.thomasvansevenant.criminalnerds;

import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;


import com.thomasvansevenant.criminalnerds.Activities.CrimeListActivity;
import com.thomasvansevenant.criminalnerds.DataManager.CrimeLab;
import com.thomasvansevenant.criminalnerds.Fragments.CrimeFragment;
import com.thomasvansevenant.daoModels.Crime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Ismail on 31/12/2015.
 */

@RunWith(CustomRobolectricRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class InstanceStateTests {
    private static final String TEST_TEXT = "TEST VOOR EDITTEXT";

    private FragmentActivity activity;
    private CrimeFragment crimeFragment;


    @Before
    public void Setup(){ // Activity met (lege) dummy crime aanmaken
        activity = Robolectric.buildActivity(CrimeListActivity.class).create().get();
        Crime crime = new Crime();
        crimeFragment = CrimeFragment.newInstance(crime.getId());
    }

    @Test
    public void fragmentsNotNull(){ // Testen dat activity werkelijk bestaat
        assertNotNull(crimeFragment);
    }

    @Test
    public void screenOrientation_shouldSaveInputtedText() {
        SupportFragmentTestUtil.startFragment(crimeFragment, CrimeListActivity.class); // Koppelt de fragment werkelijk aan een activity

        EditText edit = (EditText) crimeFragment.getView().findViewById(R.id.crime_title);
        edit.setText(TEST_TEXT);

        activity = crimeFragment.getActivity();

        activity.recreate(); // Recreate simuleert een configuration change

        Fragment recreatedFragment = activity.getSupportFragmentManager().getFragments().get(1); // De hergecreerde fragment ophalen

        edit = (EditText) recreatedFragment.getView().findViewById(R.id.crime_title);

        assertNotNull(recreatedFragment);
        assertNotSame(crimeFragment, recreatedFragment);

        assertEquals(edit.getText().toString(), TEST_TEXT); // Testen of de tekst die voor de config change werd ingevoerd, terug in het inputfield staat
    }

    @After
    public void after() throws Exception {
        Field instance = CrimeLab.class.getDeclaredField("sCrimeLab");
        instance.setAccessible(true);
        instance.set(null, null);
    }
}