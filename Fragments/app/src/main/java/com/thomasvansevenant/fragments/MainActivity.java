package com.thomasvansevenant.fragments;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements OnParadoxSelectedListener {

    private final static String LOG_TAG = Constants.MAIN_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "MainActivity created");


        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ParadoxListFragment
            ParadoxListFragment paradoxListFragment = new ParadoxListFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            paradoxListFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, paradoxListFragment).commit();
        }


    }

    @Override
    public void onParadoxSelected(int position) {
        // The user selected the title of an paradox from the list fragment
        // Capture the descr fragment from the activity layout
        ParadoxDescriptionFragment descrFrag = (ParadoxDescriptionFragment)
                getSupportFragmentManager().findFragmentById(R.id.paradoxDescription);

        if (descrFrag != null) {
            // If descr frag is available, we're in two-pane layout...

            // Call a method in the description fragment to update its content
            descrFrag.loadParadox(position);
            Log.i(LOG_TAG, "two-pane layout");


        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ParadoxDescriptionFragment paradoxDescriptionFragment = new ParadoxDescriptionFragment();
            Bundle args = new Bundle();
            args.putInt(ParadoxDescriptionFragment.ARG_POSITION, position);
            paradoxDescriptionFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


            //fragment transaction animation
            //setCustomAnimations (int enter, int exit, int popEnter, int popExit)
            transaction.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, paradoxDescriptionFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();

            Log.i(LOG_TAG, "one-pane layout");
        }
    }


}
