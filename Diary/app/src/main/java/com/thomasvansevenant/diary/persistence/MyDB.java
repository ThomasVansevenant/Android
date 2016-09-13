package com.thomasvansevenant.diary.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.thomasvansevenant.diary.models.Diary;

import java.util.GregorianCalendar;

import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.CONTENT_URI;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DIARY_CONTEXT;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DIARY_RECORDDATE;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DIARY_TITLE;

public class MyDB {

    private MyDBHelper dbHelper;
    private SQLiteDatabase db;

    public MyDB(Context context) {
        dbHelper = new MyDBHelper(context);
    }


    public SQLiteDatabase open() {
        if (db != null) {
            if (db.isOpen()) {
                db.close();
            }
        }
        db = dbHelper.getWritableDatabase();

        if (!db.isOpen()) {
            db = dbHelper.getReadableDatabase();
        }
        return db;
    }

    public void close() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public void insertDiary(String title, String context, Context cont) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DIARY_TITLE, title);
        contentValues.put(DIARY_CONTEXT, context);
        contentValues.put(DIARY_RECORDDATE, new GregorianCalendar().getTime().toString());

        cont.getContentResolver().insert(CONTENT_URI, contentValues);

    }

    public Diary getDiary(Context cont, long id) {
        Uri uri = Constants.DiaryEntry.buildDiaryUri(id);
        String[] projection = {DIARY_TITLE, DIARY_CONTEXT, DIARY_RECORDDATE};
        String title = "",
                context = "",
                date = "";

        Cursor cursor = cont.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            title = cursor.getString(cursor.getColumnIndexOrThrow(DIARY_TITLE));
            context = cursor.getString(cursor.getColumnIndexOrThrow(DIARY_CONTEXT));
            date = cursor.getString(cursor.getColumnIndexOrThrow(DIARY_RECORDDATE));


            cursor.close();
        }
        Diary diary = new Diary(title, context, date);
        return diary;
    }

    public void deleteAllRecords() {
        open();
        db.execSQL("delete from " + Constants.DiaryEntry.DB_TABLE_NAME);
        close();
    }
}
