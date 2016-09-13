package com.thomasvansevenant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParadoxDescriptionFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    @Bind(R.id.title)
    public TextView title;

    @Bind(R.id.description)
    public TextView description;


    public ParadoxDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paradox_description, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt(ARG_POSITION);
            // Set paradox based on argument passed in
            loadParadox(position);
        } else if (mCurrentPosition != -1) {
            // Set paradox based on saved instance state defined during onCreateView
            loadParadox(mCurrentPosition);
        }
    }

    public void loadParadox(int position) {
        title.setText(Paradoxes.ParadoxNames[position]);
        description.setText(Paradoxes.ParadoxDescription[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current paradox selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }


}
