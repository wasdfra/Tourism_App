package com.tourism.app.data.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tourism.app.data.DatabaseManager;
import com.tourism.app.data.model.RouteData;

import java.util.ArrayList;

/**
 * Created by estgl3264 on 02/05/2018.
 */

public class RouteDataHandler {


    private static RouteData routeData;

    public RouteDataHandler() {

        routeData = new RouteData();

    }


    public static String createTable() {
        return ("CREATE TABLE "
                + RouteData.TABLE + "("
                + RouteData.KEY_RouteDataID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RouteData.KEY_UserID + " INTEGER, "
                + RouteData.KEY_RouteData + " TEXT)");

    }


    public synchronized int insert(RouteData routeDt) {
        int RouteDataID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(RouteData.KEY_UserID, routeData.getUserID());
        values.put(RouteData.KEY_RouteData, routeData.getRouteData());

        // Inserting Row
        RouteDataID = (int) db.insert(RouteData.TABLE, null, values);

        DatabaseManager.getInstance().closeDatabase();

        return RouteDataID;
    }

    public synchronized ArrayList<RouteData> getDataBy(String key, String uid) {
        ArrayList<RouteData> routeDataList = new ArrayList<RouteData>();

        Cursor C;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        C = db.rawQuery("Select "
                + RouteData.KEY_RouteDataID + ", "
                + RouteData.KEY_RouteData + ", "
                + RouteData.KEY_UserID + " from " + RouteData.TABLE + " where " + key + " = " + uid, null);

        if (C != null) {
            C.moveToFirst();
            while (C.moveToNext())
            try {
                RouteData tempRouteData = new RouteData();
                tempRouteData.setRouteData(C.getString(1));
                tempRouteData.setUserID(C.getInt(2));
                routeDataList.add(tempRouteData);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                DatabaseManager.getInstance().closeDatabase();
                return null;
            }

        } else {
            DatabaseManager.getInstance().closeDatabase();
            return null;
        }
        DatabaseManager.getInstance().closeDatabase();

        return routeDataList.size() > 0 ? routeDataList : null;
    }

    public synchronized void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(RouteData.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

}
