package com.thomasvansevenant.database;

import com.thomasvansevenant.daoModels.RedditPost;

import java.util.ArrayList;
import java.util.Set;

public interface IDatabaseManager {
    void closeDbConnections();

    ArrayList<RedditPost> listRedditPosts();

    RedditPost getRedditPostById(Long redditPostId);

    void bulkInsertRedditPosts(Set<RedditPost> redditPosts);

    void dropDatabase();
}
