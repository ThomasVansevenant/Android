package com.thomasvansevenant.reddit.API;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.thomasvansevenant.daoModels.RedditPost;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ThomasVansevenant on 5/01/2016.
 */
public class RedditDeserializer implements JsonDeserializer<ArrayList<RedditPost>> {

    private final static String LOG_TAG = RedditDeserializer.class.getSimpleName();

    @Override
    public ArrayList<RedditPost> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // The variable 'json' is passed as a parameter to the deserialize() method
        JsonObject jsonMainObject = json.getAsJsonObject();
        JsonObject dataJsonObject = jsonMainObject.get("data").getAsJsonObject();
        JsonArray childrenJsonArray = dataJsonObject.get("children").getAsJsonArray();


        ArrayList<RedditPost> subredditPosts = new ArrayList<>();

        for (int i = 0; i < childrenJsonArray.size(); i++) {
            JsonObject subredditData = childrenJsonArray.get(i).getAsJsonObject().get("data").getAsJsonObject();
            String author = subredditData.get("author").getAsString();
            String title = subredditData.get("title").getAsString();
            String thumbnailUrl = subredditData.get("thumbnail").getAsString();
            String imageUrl = "";
            String idPost = subredditData.get("id").getAsString();
            String after = dataJsonObject.get("after").getAsString();
            int numComments = subredditData.get("num_comments").getAsInt();

            if (subredditData.get("preview") != null) {
                JsonObject previewJsonObject = subredditData.get("preview").getAsJsonObject();
                JsonArray previewChildrenJsonArray = previewJsonObject.getAsJsonArray("images").getAsJsonArray();
                JsonObject sourceJsonObject = previewChildrenJsonArray.get(0).getAsJsonObject().get("source").getAsJsonObject();
                imageUrl = sourceJsonObject.get("url").getAsString();
            }
            RedditPost redditPost = new RedditPost();
            redditPost.setAuthor(author);
            redditPost.setImageUrl(imageUrl);
            redditPost.setTitle(title);
            redditPost.setThumbnailUrl(thumbnailUrl);
            redditPost.setNumComments(numComments);
            redditPost.setAfter(after);

            subredditPosts.add(redditPost);
        }
        Log.i(LOG_TAG, "deserialization succeeded");

        return subredditPosts;
    }

}
