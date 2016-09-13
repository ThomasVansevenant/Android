package com.thomasvansevenant.criminalnerds;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.thomasvansevenant.daoModels.DaoMaster;
import com.thomasvansevenant.daoModels.DaoSession;

public class CriminalNerdsApplication extends Application {
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "crime-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();   }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
