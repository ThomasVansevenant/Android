package com.thomasvansevenant.diary.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.thomasvansevenant.diary.persistence.Constants.*;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.*;
public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE_NAME + "(" +
                        DIARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DIARY_TITLE + " TEXT NOT NULL, " +
                        DIARY_CONTEXT + " TEXT NOT NULL, " +
                        DIARY_RECORDDATE + " TEXT" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);

    }
}
