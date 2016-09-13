package com.thomasvansevenant.recyclerview;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    @Bind(R.id.textview_character_name)
    TextView characterName;

    @Bind(R.id.textview_character_description)
    TextView characterDescription;

    @Bind(R.id.textview_character_image)
    ImageView characterImage;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);


        Bundle bundle = new Bundle();
        int position = bundle.getInt("POSITION");
        getOneCharacter(position);

        return rootView;
    }

    private void getOneCharacter(int position) {
        List<Character> characterList = new Character().getCharacterList();
        Character character = characterList.get(position);
        characterName.setText(character.name);
        characterDescription.setText(character.description);
        characterImage.setImageResource(character.imageId);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
