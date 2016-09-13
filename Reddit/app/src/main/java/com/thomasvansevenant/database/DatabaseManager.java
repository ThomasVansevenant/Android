package com.thomasvansevenant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.thomasvansevenant.daoModels.DaoMaster;
import com.thomasvansevenant.daoModels.DaoSession;
import com.thomasvansevenant.daoModels.RedditPost;
import com.thomasvansevenant.daoModels.RedditPostDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by ThomasVansevenant on 16/01/2016.
 */
public class DatabaseManager implements IDatabaseManager, AsyncOperationListener {

    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DatabaseManager.class.getCanonicalName();
    /**
     * Instance of DatabaseManager
     */
    private static DatabaseManager instance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;

    public DatabaseManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, "sample-database", null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
    }

    public static DatabaseManager getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }
    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }


    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public synchronized ArrayList<RedditPost> listRedditPosts() {
        List<RedditPost> redditPosts = null;
        try {
            openReadableDb();
            RedditPostDao redditPostDao = daoSession.getRedditPostDao();
            redditPosts = redditPostDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (redditPosts != null) {
            return new ArrayList<>(redditPosts);
        }
        return null;
    }

    @Override
    public synchronized RedditPost getRedditPostById(Long redditPostId) {
        RedditPost redditPost = null;
        try {
            openReadableDb();
            RedditPostDao redditPostDao = daoSession.getRedditPostDao();
            redditPost = redditPostDao.load(redditPostId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redditPost;
    }

    @Override
    public synchronized void bulkInsertRedditPosts(Set<RedditPost> redditPosts) {
        try {
            if (redditPosts != null && redditPosts.size() > 0) {
                openWritableDb();
                asyncSession.insertOrReplaceInTx(RedditPost.class, redditPosts);
                assertWaitForCompletion1Sec();
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(RedditPost.class);    // clear all elements from a table
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }
}
