package com.tourism.app.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by estgl3264 on 02/05/2018.
 */
public class DatabaseManager {
    private static int mOpenCounter;
    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private static SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public static boolean isOpen() {
        return mOpenCounter>0;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {

        if(mOpenCounter > 0)
            mOpenCounter--;

        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }
}