package com.thomasvansevenant.reddit.views;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thomasvansevenant.daoModels.RedditPost;
import com.thomasvansevenant.database.DatabaseManager;
import com.thomasvansevenant.database.IDatabaseManager;

import com.thomasvansevenant.reddit.API.ServiceGenerator;
import com.thomasvansevenant.reddit.API.TaskService;
import com.thomasvansevenant.reddit.R;
import com.thomasvansevenant.reddit.data.Constants;
import com.thomasvansevenant.reddit.recycler.RecyclerItemClickListener;
import com.thomasvansevenant.reddit.recycler.RedditAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.thomasvansevenant.reddit.data.Constants.LOG_TAG_SUBREDDITFRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubRedditFragment extends Fragment {
    public static final String ARG_SUBREDDIT_NUMBER = "subreddit_number";

    private static final String REDDIT_POST = "redditPost";
    private static final String REDDIT_POST_ID = "redditPostId";
    private static final String REDDIT_POSTS = "redditPosts";
    private static final String REDDIT_CATEGORY = "category";


    private IDatabaseManager databaseManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // init database manager
        databaseManager = new DatabaseManager(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                updateReddits();
                Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), Settings.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private static final String LIMIT = "6";
    private int limit;

    private SubRedditFragment subRedditFragment = this;


    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<RedditPost> subreddits;


    public SubRedditFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_reddit, container, false);
        ButterKnife.bind(this, view);
        updateReddits();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().finish();
    }


    private void updateReddits() {

        int i = getArguments().getInt(ARG_SUBREDDIT_NUMBER);
        final String category = getResources()
                .getStringArray(R.array.subreddits_array)[i]
                .toLowerCase();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        limit = Integer.parseInt(prefs.getString(getString(R.string.pref_postcount_key), getString(R.string.pref_postcount_default)));
        subreddits = new ArrayList<>();
        getRedditPosts(category, limit);

        Bundle bundle = new Bundle();
        bundle.putString(REDDIT_CATEGORY, category);


        // specify an adapter (see also next example)
        mAdapter = new RedditAdapter(subreddits, getContext());


//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                DetailFragment detailFragment = new DetailFragment();
                                Bundle args = new Bundle();
                                args.putInt(SubRedditFragment.ARG_SUBREDDIT_NUMBER, position);
                                RedditPost redditPost = subreddits.get(position);
                                args.putParcelable(REDDIT_POST, redditPost);
                                detailFragment.setArguments(args);

                                // Replace whatever is in the fragment_container view with this fragment,
                                // and add the transaction to the back stack so the user can navigate back
                                ft.replace(R.id.content_frame, detailFragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                ft.addToBackStack(null);
                                // Commit the transaction
                                ft.commit();

                            }
                        }));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();


                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            String after = subreddits.get(totalItemCount - 1).getAfter();//
//
                            loading = false;
                            getMoreRedditPosts(category, limit, after);
                            Log.i("...", "Last Item Wow !");
                            Log.i(LOG_TAG_SUBREDDITFRAGMENT, String.valueOf(visibleItemCount)
                            );
//
                        }
                    }
                }
            }
        });
    }

    public interface OnNewsItemSelectedListener {
        public void onNewsItemPicked(String category);
    }


    @Override
    public void onStop() {
        super.onStop();
        mAdapter = null;
    }


    private void getRedditPosts(String category, int limit) {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getRedditPosts(category, limit, new Callback<ArrayList<RedditPost>>() {

            @Override
            public void success(ArrayList<RedditPost> redditPosts, Response response) {
                insertDatabase(redditPosts);
                subreddits.addAll(redditPosts);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Failed to load redditpost", Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG_SUBREDDITFRAGMENT, "TaskService failed: " + error);
            }
        });
    }

    private void getMoreRedditPosts(String category, int limit, String after) {
//        RedditTask task = new RedditTask();
//        task.setOnTaskCompleted(this);
//        task.execute(category, limit, after);

        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getMoreRedditPosts(category, limit, after, new Callback<ArrayList<RedditPost>>() {
            @Override
            public void success(ArrayList<RedditPost> redditPosts, Response response) {
                insertDatabase(redditPosts);
                subreddits.addAll(redditPosts);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "load more...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Failed to load redditpost", Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG_SUBREDDITFRAGMENT, "Load more failed: " + error);
            }
        });
    }

    private void insertDatabase(ArrayList<RedditPost> redditPosts) {
        Set setRedditPosts = new HashSet(redditPosts);
        DatabaseManager.getInstance(getContext()).dropDatabase();
        DatabaseManager.getInstance(getContext()).bulkInsertRedditPosts(setRedditPosts);
    }
}

