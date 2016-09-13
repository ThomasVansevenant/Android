package com.thomasvansevenant.criminalnerds.Utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;


import com.thomasvansevenant.criminalnerds.Fragments.CrimeListFragment;

/**
 * Created by ThomasVansevenant on 8/01/2016.
 */
public class CrimeTouchHelper extends ItemTouchHelper.SimpleCallback {
    private CrimeListFragment.CrimeAdapter crimeAdapter;

    public CrimeTouchHelper(CrimeListFragment.CrimeAdapter crimeAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.crimeAdapter = crimeAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i(CrimeTouchHelper.class.getSimpleName(), String.valueOf(viewHolder.getAdapterPosition()));
        crimeAdapter.deleteCrimes(viewHolder.getAdapterPosition());
    }
}
