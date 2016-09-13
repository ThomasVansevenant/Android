package com.thomasvansevenant.reddit.recycler;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thomasvansevenant.daoModels.RedditPost;
import com.thomasvansevenant.reddit.R;

import java.util.ArrayList;

public class RedditAdapter extends RecyclerView.Adapter<RedditAdapter.ViewHolder> {
    private static final String LOG_TAG = RedditAdapter.class.getSimpleName();
    private ArrayList<RedditPost> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView textViewTitle;
        public ImageView imageView;
        public TextView textViewAuthor;
        public Context context;
        public TextView textViewComments;

        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card);
            textViewTitle = (TextView) v.findViewById(R.id.card_subreddit_title);
            imageView = (ImageView) v.findViewById(R.id.card_subbreddit_image);
            textViewAuthor = (TextView) v.findViewById(R.id.card_subreddit_author);
            textViewComments = (TextView) v.findViewById(R.id.card_subreddit_comment);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RedditAdapter(ArrayList<RedditPost> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RedditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RedditPost redditPost = mDataset.get(position);
        String title = redditPost.getTitle();
        String thumbnailUrl = redditPost.getThumbnailUrl();
        String imageUrl = redditPost.getImageUrl();
        String numberComments = String.valueOf(redditPost.getNumComments());
        String author = redditPost.getAuthor();
        holder.textViewTitle.setText(title);
        holder.textViewAuthor.setText(author);
        holder.textViewComments.setText(numberComments + " comments");
        Log.i(LOG_TAG, imageUrl);

        if (!thumbnailUrl.toLowerCase().contains("http")) {
            Picasso.with(context)
                    .load(R.drawable.noimage)
                    .into(holder.imageView);
        } else {
            Picasso.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.photo_placeholder_loading)
                    .into(holder.imageView);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
