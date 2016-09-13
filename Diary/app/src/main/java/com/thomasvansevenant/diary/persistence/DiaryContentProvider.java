package com.thomasvansevenant.diary.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.thomasvansevenant.diary.persistence.Constants.DiaryEntry;

import static com.thomasvansevenant.diary.persistence.Constants.*;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.*;

public class DiaryContentProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MyDBHelper mMyDBHelper;
    private MyDB myDB;


    static UriMatcher buildUriMatcher() {

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, PATH_DIARY + "/#", DIARY);
        matcher.addURI(authority, PATH_DIARY, DIARIES);

        return matcher;
    }

    //Create instance of MyDBHelper
    //returns true -> tells that MyDBHelper has been created
    @Override
    public boolean onCreate() {
        mMyDBHelper = new MyDBHelper(getContext());
        myDB = new MyDB(getContext());
        return true;
    }


    //    which returns the MIME* type of data in the content provider
    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DIARY:
                return CONTENT_ITEM_TYPE;
            case DIARIES:
                return CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknow uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Set table
        queryBuilder.setTables(DB_TABLE_NAME);

        // check URI's
        int uriType = sUriMatcher.match(uri);

        //Type defined in matcher
        switch (uriType) {
            case DIARIES: {
                break;
            }
            case DIARY: {
                // WHERE clause queryBuilder
                queryBuilder.appendWhere(DIARY_ID + "=" + uri.getLastPathSegment());
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        SQLiteDatabase db = myDB.open();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder); // execute query

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        long id = 0;

        SQLiteDatabase sqlDB = myDB.open();

        switch (uriType) {
            case DIARIES: {
                id = sqlDB.insert(DB_TABLE_NAME, null, values);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(PATH_DIARY + "/" + id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMyDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case DIARY:
                rowsDeleted = db.delete(
                        DB_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMyDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case DIARY:
//                normalizeDate(values);
                rowsUpdated = db.update(DB_TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * A MIME type is a label for a given type of data so software can know how
     * to handle the data. It serves the same purpose on the Internet that file
     * extensions do on Microsoft Windows.
     */
}
