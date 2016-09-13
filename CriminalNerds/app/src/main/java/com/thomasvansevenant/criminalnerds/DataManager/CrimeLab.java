package com.thomasvansevenant.criminalnerds.DataManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.thomasvansevenant.daoModels.Crime;
import com.thomasvansevenant.daoModels.CrimeDao;
import com.thomasvansevenant.daoModels.DaoMaster;
import com.thomasvansevenant.daoModels.DaoSession;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static final String LOG_TAG = CrimeLab.class.getSimpleName();
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DaoSession daoSession;
    private CrimeDao mCrimeDao;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "crimeBase-db", null);
        mDatabase = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(mDatabase);
        daoSession = daoMaster.newSession();
        mCrimeDao = daoSession.getCrimeDao();
//        DaoSession daoSession = ((CriminalNerdsApplication) getApplicationContext()).getDaoSession();
    }


    public void addCrime(Crime c) {
//        ContentValues values = getContentValues(c);
//
//        mDatabase.insert(CrimeTable.NAME, null, values);

        mCrimeDao.insert(replaceWithDefault(c));

    }

    public List<Crime> getCrimes() {
//        List<Crime> crimes = new ArrayList<Crime>();
//
//        CrimeCursorWrapper cursor = queryCrimes(null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            crimes.add(cursor.getCrime());
//            cursor.moveToNext();
//        }
//        cursor.close();
//
        Log.i(LOG_TAG, "All crimes loaded");
        return mCrimeDao.queryBuilder().list();
    }

    public Crime getCrime(UUID id) {
//        CrimeCursorWrapper cursor = queryCrimes(
//                CrimeTable.Cols.UUID + " = ?",
//                new String[]{id.toString()}
//        );
//
//        try {
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//
//            cursor.moveToFirst();
//            return cursor.getCrime();
//        } finally {
//            cursor.close();
//        }
        return mCrimeDao
                .queryBuilder()
                .where(CrimeDao
                        .Properties
                        .Id
                        .eq(id))
                .unique();
    }

    public void deleteCrime(Crime c) {
        mCrimeDao.delete(c);
    }


    public File getPhotoFile(Crime crime) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, crime.getPhotoFilename());
    }

    public void updateCrime(Crime crime) {
//        String uuidString = crime.getId().toString();
//        ContentValues values = getContentValues(crime);
//
//        mDatabase.update(CrimeTable.NAME, values,
//                CrimeTable.Cols.UUID + " = ?",
//                new String[]{uuidString});
        mCrimeDao.update(crime);
    }


    private Crime replaceWithDefault(Crime c) {
        if (c.getTitle() == null)
            c.setTitle("");
        if (c.getDate() == null)
            c.setDate(new Date());
        if (c.getPhotopath() == null)
            c.setPhotopath("");
        if (c.getSolved() == null)
            c.setSolved(false);
        if (c.getSuspect() == null)
            c.setSuspect("");
        return c;
    }

//    private static ContentValues getContentValues(Crime crime) {
//        ContentValues values = new ContentValues();
//        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
//        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
//        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
//        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
//        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
//
//        return values;
//    }

//    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
//        Cursor cursor = mDatabase.query(
//                CrimeTable.NAME,
//                null, // Columns - null selects all columns
//                whereClause,
//                whereArgs,
//                null, // groupBy
//                null, // having
//                null  // orderBy
//        );
//
//        return new CrimeCursorWrapper(cursor);
//    }


}
