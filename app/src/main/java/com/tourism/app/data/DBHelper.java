package com.tourism.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tourism.app.data.handler.*;


/**
 * Created by estgl3264 on 02/05/2018.
 */


public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 9;
    // Database Name
    private static final String DATABASE_NAME = "routes.db";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        try {
            db.execSQL(UserHandler.createTable());
            db.execSQL(GPSDataHandler.createTable());
            db.execSQL(SessionHandler.createTable());
            db.execSQL(RouteDataHandler.createTable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}