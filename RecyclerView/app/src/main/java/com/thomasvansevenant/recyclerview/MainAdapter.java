package com.thomasvansevenant.recyclerview;

/**
 * Created by ThomasVansevenant on 2/01/2016.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * This adapter follows the view holder design pattern, which means that it you to define a custom class that extends RecyclerView.ViewHolder.
 * This pattern minimizes the number of calls to the costly findViewById method.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<Character> characterList;

    public MainAdapter(List<Character> characterList) {
        this.characterList = characterList;
    }

    /**
     * Next, override the onCreateViewHolder method.
     * As its name suggests, this method is called when the custom ViewHolder needs to be initialized.
     * We specify the layout that each item of the RecyclerView should use.
     * This is done by inflating the layout using LayoutInflater, passing
     * the output to the constructor of the custom ViewHolder
     * Inflating cardview layout -> card_layout.xml
     */
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        MainViewHolder pvh = new MainViewHolder(v);
        return pvh;
    }


    /**
     * Override the onBindViewHolder to specify the contents of each item of the RecyclerView.
     * This method is very similar to the getView method of a ListView's adapter.
     * In our example, here's where you have to set the values of the name, age, and photo fields of the CardView.     *
     */
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.characterName.setText(characterList.get(position).name);
        holder.characterImage.setImageResource(characterList.get(position).imageId);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView characterName;
        //    TextView characterDescription;
        ImageView characterImage;

        public MainViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            characterName = (TextView) itemView.findViewById(R.id.card_name);
            characterImage = (ImageView) itemView.findViewById(R.id.card_image);
            //            characterDescription = (TextView) itemView.findViewById(R.id.);
        }
    }

    //    Called by RecyclerView when it starts observing this Adapter.
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
