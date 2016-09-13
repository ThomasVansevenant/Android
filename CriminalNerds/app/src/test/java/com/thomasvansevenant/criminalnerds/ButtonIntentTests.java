package com.thomasvansevenant.criminalnerds;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;


import com.thomasvansevenant.criminalnerds.Activities.CrimeListActivity;
import com.thomasvansevenant.criminalnerds.DataManager.CrimeLab;
import com.thomasvansevenant.criminalnerds.Fragments.CrimeFragment;
import com.thomasvansevenant.daoModels.Crime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.io.File;
import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Ismail on 31/12/2015.
 */
@RunWith(CustomRobolectricRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ButtonIntentTests {
    private static final String INTENT_CONTAINER_KEY = "android.intent.extra.INTENT";

    private Fragment crimeFragment;

    @Before
    public void Setup(){
        Crime crime = new Crime();
        crimeFragment = CrimeFragment.newInstance(crime.getId());
    }

    @Test
    public void fragmentsNotNull(){
        assertNotNull(crimeFragment);
    }

    @Test
    public void clickingCamera_shouldStartCameraActivity() {
        SupportFragmentTestUtil.startFragment(crimeFragment, CrimeListActivity.class);

        crimeFragment.getView().findViewById(R.id.crime_camera).performClick();

        Intent expectedIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent actualIntent = (shadowOf(crimeFragment.getActivity()).getNextStartedActivity()); // Camera is gestart naast de activity?

        assertThat(actualIntent.getAction()).isEqualTo(expectedIntent.getAction()); // Controleer dat de juiste intent is doorgegeven

        Uri dummy = Uri.fromFile(new File(""));
        assertEquals(actualIntent.getExtras().get(MediaStore.EXTRA_OUTPUT).getClass(), dummy.getClass()); // Controleer of de File object ook is meegegeven
    }

    @Test
    public void clickingSuspect_shouldStartContactsActivity() {
        SupportFragmentTestUtil.startFragment(crimeFragment, CrimeListActivity.class);

        crimeFragment.getView().findViewById(R.id.crime_suspect).performClick();

        Intent expectedIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        Intent actualIntent = (shadowOf(crimeFragment.getActivity()).getNextStartedActivity()); // Contacts is gestart naast de activity?

        assertThat(actualIntent.getAction()).isEqualTo(expectedIntent.getAction()); // Controleer
    }

    @Test
    public void clickingSend_shouldStartShare() {
        SupportFragmentTestUtil.startFragment(crimeFragment, CrimeListActivity.class);

        crimeFragment.getView().findViewById(R.id.crime_report).performClick();

        Intent expectedIntentContainer = new Intent(Intent.ACTION_CHOOSER);
        Intent expectedIntent = new Intent(Intent.ACTION_SEND);

        Intent actualIntentContainer = (shadowOf(crimeFragment.getActivity()).getNextStartedActivity());
        Intent actualIntent = (Intent) actualIntentContainer.getExtras().get(INTENT_CONTAINER_KEY);

        //check if corrent intent container actions and intent actions have been fired
        assertThat(actualIntentContainer.getAction()).isEqualTo(expectedIntentContainer.getAction()); // Is de intent container gestart?
        assertThat(actualIntent.getAction()).isEqualTo(expectedIntent.getAction()); // Is de intent send gestart?

        String dummy = "";
        assertEquals(actualIntent.getExtras().get(Intent.EXTRA_TEXT).getClass(), dummy.getClass()); // Controleer of er tekst is meegegeven
    }

    @After
    public void after() throws Exception { // sCrimeLab terug null maken voor de volgende tests (lost een vreemde bug op)
        Field instance = CrimeLab.class.getDeclaredField("sCrimeLab");
        instance.setAccessible(true);
        instance.set(null, null);
    }
}