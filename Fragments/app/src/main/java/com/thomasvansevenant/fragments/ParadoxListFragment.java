package com.thomasvansevenant.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParadoxListFragment extends Fragment {

    private ArrayAdapter<String> mParadoxAdapter;
    private OnParadoxSelectedListener mCallback;// callback to communicate with Activity

    public ParadoxListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paradox_list, container, false);
        ButterKnife.bind(view);

        mParadoxAdapter =
                new ArrayAdapter<>(getActivity(),
                        R.layout.list_item_paradox,
                        R.id.list_item_paradox_textview,
                        Paradoxes.ParadoxNames);

        ListView listView = (ListView) view.findViewById(R.id.listview_paradox);
        listView.setAdapter(mParadoxAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onParadoxSelected(position);
            }
        });

        return view;
    }

    //Communicates back to MainActivity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity) {
            activity = (Activity) context;

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (OnParadoxSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
        Log.i(Constants.LOG_TAG_PARADOXLIST_FRAGMENT, "onAttach() called");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        Log.i(Constants.LOG_TAG_PARADOXLIST_FRAGMENT, "onDetach() called");

    }
}
