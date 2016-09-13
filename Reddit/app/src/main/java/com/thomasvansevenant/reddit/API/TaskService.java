package com.thomasvansevenant.reddit.API;

import com.thomasvansevenant.daoModels.RedditPost;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by ThomasVansevenant on 25/01/2016.
 */
public interface TaskService {
    @GET("/r/{category}.json")
    void getRedditPosts(@Path("category") String category, @Query("limit") int limit, Callback<ArrayList<RedditPost>> callback);

    @GET("/r/{category}.json")
    void getMoreRedditPosts(@Path("category") String category, @Query("limit") int limit, @Query("after") String id, Callback<ArrayList<RedditPost>> callback);
}

