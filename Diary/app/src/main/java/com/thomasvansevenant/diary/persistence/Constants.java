package com.thomasvansevenant.diary.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Constants {
    public static final String LOG_TAG = "MyDBHelper";
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "DiaryBase.db";


    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.

    public static final String CONTENT_AUTHORITY = "com.thomasvansevenant.diary";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.thomasvansevenant.diary/diary/ is a valid path for
    // looking at weather data. content://com.thomasvansevenant.diary/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_DIARY = "diary";

    /* Inner class that defines the table contents of the location table */
    public static final class DiaryEntry implements BaseColumns {
        static final int DIARY = 100;
        static final int DIARIES = 101;

        public static Uri CONTENT_URI =
                BASE_CONTENT_URI
                        .buildUpon()
                        .appendPath(PATH_DIARY)
                        .build();

        //ContentResolver*
        public static final String CONTENT_TYPE =
                ContentResolver
                        .CURSOR_DIR_BASE_TYPE
                        + "/" + CONTENT_AUTHORITY
                        + "/" + PATH_DIARY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIARY;

        public static final String DB_TABLE_NAME = "diaries";
        public static final String DIARY_ID = "_id";
        public static final String DIARY_TITLE = "diary_title";
        public static final String DIARY_CONTEXT = "diary_context";
        public static final String DIARY_RECORDDATE = "diary_date";

        public static Uri buildDiaryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}

/**
 * ContentResolver*
 * The Content Resolver is the single, global instance in your application that provides access
 * to your (and other applications')
 * content providers. The Content Resolver behaves exactly as its name implies: it accepts requests from clients,
 * and resolves these
 * requests by directing them to the content provider with a distinct authority.
 * To do this, the Content Resolver stores a mapping from authorities to Content Providers.
 * This design is important, as it allows a simple and secure means of accessing other applications'
 * Content Providers.
 * The Content Resolver includes the CRUD (create, read, update, delete) methods corresponding to the
 * abstract methods (insert, query, update, delete) in the Content Provider class. The Content Resolver
 * does not know the implementation of the Content Providers it is interacting with (nor does it need to know);
 * each method is passed an URI that specifies the Content Provider to interact with.
 */
