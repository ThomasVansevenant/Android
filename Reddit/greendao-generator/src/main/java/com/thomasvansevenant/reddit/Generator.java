package com.thomasvansevenant.reddit;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.thomasvansevenant.daoModels");
        Entity redditPost = schema.addEntity("RedditPost");
        redditPost.addIdProperty();
        redditPost.addStringProperty("author");
        redditPost.addStringProperty("thumbnailUrl");
        redditPost.addStringProperty("title");
        redditPost.addStringProperty("imageUrl");
        redditPost.addStringProperty("idPost");
        redditPost.addStringProperty("after");
        redditPost.addIntProperty("numComments");

        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
