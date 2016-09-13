package com.thomasvansevenant.reddit.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thomasvansevenant.daoModels.RedditPost;
import com.thomasvansevenant.database.DatabaseManager;
import com.thomasvansevenant.reddit.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private final static String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String REDDIT_POST = "redditPost";
    private static final String REDDIT_POST_ID = "redditPostId";

    @Bind(R.id.textview_subreddit_detail_title)
    TextView textViewTitle;

    @Bind(R.id.imageview_subreddit_detail)
    CircleImageView circleImageView;

    @Bind(R.id.textview_subreddit_detail_author)
    TextView textViewAuthor;

    @Bind(R.id.textview_subreddit_detail_comments)
    TextView textViewComments;


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        createLayout();
        return view;
    }

    private void createLayout() {
        Bundle bundle = this.getArguments();
        RedditPost redditPost = bundle.getParcelable(REDDIT_POST);
        String imageUrl = redditPost.getImageUrl();
        String title = redditPost.getTitle();
        String author = redditPost.getAuthor();
        String comments = String.valueOf(redditPost.getNumComments());

        textViewTitle.setText(title);
        textViewAuthor.setText("by " + author);
        textViewComments.setText(comments + " comments");

        if (!imageUrl.isEmpty()) {
            Picasso.with(getContext()).load(imageUrl).into(circleImageView);

        } else {
            Picasso.with(getContext()).load(R.drawable.noimageavailable).into(circleImageView);
            textViewTitle.setText(title);
        }
    }
}
