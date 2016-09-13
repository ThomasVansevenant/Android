package com.thomasvansevenant.recyclerview;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerFragment extends Fragment {

    private final static String LOG_TAG = RecyclerFragment.class.getSimpleName();

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    LayoutManager mLayoutManager;



    public RecyclerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        ButterKnife.bind(this, rootView);

        List<Character> characterList = new Character().getCharacterList();
//        Instantiate and find the variables in the onCreateView method of your Fragment
//        Connect both objects
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        MainAdapter mainAdapter = new MainAdapter(characterList);
        mRecyclerView.setAdapter(mainAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        loadDetailFragment(position);
                    }
                })
        );

        ButterKnife.bind(this, rootView);
        // Inflate the layout for this fragment
        return rootView;
    }


    private void loadDetailFragment(int position) {
        Log.i(LOG_TAG, "Tapped postition " + position);
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        detailFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        //fragment transaction animation
        //setCustomAnimations (int enter, int exit, int popEnter, int popExit)
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_in_left,
                android.R.anim.slide_in_left,
                android.R.anim.slide_in_left);

        //Onclick replace current fragment with detailFragment
        fragmentTransaction.replace(R.id.container_main, detailFragment);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

}
