package com.thomasvansevenant.recyclerview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;

/**
 * Created by ThomasVansevenant on 2/01/2016.
 */
public class Character {

    //Must be public
    String name;
    String description;
    int imageId;

    @BindString(R.string.desc_Cersei_Lannister)
    String descCerseiLannister;
    @BindString(R.string.desc_Jaqen_Hghar)
    String desc_JaqenHghar;
    @BindString(R.string.desc_Missandei)
    String descMissandei;
    @BindString(R.string.desc_Petyr_Baelish)
    String desc_Petyr_Baelish;


    private List<Character> characterList;

    public Character() {
        characterList = new ArrayList<>();
        initializeData();

    }

    public Character(String name, String description, int imageId) {
        this.description = description;
        this.imageId = imageId;
        this.name = name;

    }

    //    This method creates an ArrayList that has three characters objects
    private void initializeData() {

        characterList.add(
                new Character(
                        "Cersei Lannister",
                        "Queen Regent of the Seven Kingdoms, Cersei is fiercely protective of her three children. Even before she was married to Robert Baratheon, she was involved in a relationship with her twin brother, Jaime. Like her father, Cersei is interested in maintaining her position of power.",
                        R.drawable.cerseilannister));
        characterList.add(
                new Character(
                        "Jaqen H’ghar",
                        "Jaqen helped Arya escape from Harrenhal and provided her with an iron coin to ensure her safe passage to Braavos.",
                        R.drawable.jaqenhghar));
        characterList.add(
                new Character("Missandei",
                        "Formerly a slave in Astapor, Missandei is fluent in the Common Tongue and High Valyrian.",
                        R.drawable.missandei));
        characterList.add(
                new Character(
                        "Petyr Baelish",
                        "Nakedly ambitious, Littlefinger left the Small Council to marry Lysa Arryn and secure the Vale to the Lannister's side. Beyond his official duties, he is the eyes and ears of King's Landing along with Varys.",
                        R.drawable.petyrbaelish));

    }

    public List<Character> getCharacterList() {
        return characterList;
    }
}
