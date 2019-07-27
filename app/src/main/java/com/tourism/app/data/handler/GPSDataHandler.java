package com.tourism.app.data.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tourism.app.data.DatabaseManager;
import com.tourism.app.data.model.GPSData;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by estgl3264 on 02/05/2018.
 */

public class GPSDataHandler {


    public GPSDataHandler() {}

    public static String createTable() {
        return ("CREATE TABLE "
                + GPSData.TABLE + "("
                + GPSData.KEY_GPSDataId + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GPSData.KEY_UserID + " INTEGER, "
                + GPSData.KEY_Latitude + " FLOAT, "
                + GPSData.KEY_Longitude + " FLOAT, "
                + GPSData.KEY_Altitude + " FLOAT, "
                + GPSData.KEY_DateTime + " TEXT, "
                + GPSData.KEY_IsSaved + " BOOLEAN)");
    }

    public synchronized int insert(GPSData gpsDt) {
        int GPSDataID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(GPSData.KEY_UserID, gpsDt.getUserID());
        values.put(GPSData.KEY_Longitude, gpsDt.getLongitude());
        values.put(GPSData.KEY_Latitude, gpsDt.getLatitude());
        values.put(GPSData.KEY_Altitude, gpsDt.getAltitude());
        values.put(GPSData.KEY_DateTime, gpsDt.getDateTime());
        values.put(GPSData.KEY_IsSaved, gpsDt.getIsSaved());

        // Inserting Row
        GPSDataID = (int) db.insert(GPSData.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return GPSDataID;
    }



    public synchronized List<Integer> getNotSavedId(){
        List<Integer> notSavedId = new ArrayList<>();

        Cursor C;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        notSavedId.clear();

        C = db.rawQuery("Select " + GPSData.KEY_GPSDataId + " from " + GPSData.TABLE + " where (" + GPSData.KEY_IsSaved + " <> 1)", null);


        if (C != null || C.getCount() >0) {

            C.moveToFirst();
            do {

                try {
                    notSavedId.add(C.getInt(0));
                } catch (IndexOutOfBoundsException e) {
                         DatabaseManager.getInstance().closeDatabase();
                }

            } while (C.moveToNext());

        } else
            notSavedId = null;

        C.close();
        DatabaseManager.getInstance().closeDatabase();
        return notSavedId;
    }

    public synchronized GPSData getDataByID(int gpsid) {
        GPSData gpsData = new GPSData();

        Cursor C;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        C = db.rawQuery("Select "
                + GPSData.KEY_Latitude + ", "
                + GPSData.KEY_Longitude + ", "
                + GPSData.KEY_Altitude + ", "
                + GPSData.KEY_DateTime + ", "
                + GPSData.KEY_UserID + ", "
                + GPSData.KEY_IsSaved + " from " + GPSData.TABLE + " where " + GPSData.KEY_GPSDataId + " = " + gpsid, null);

        if (C != null) {
            C.moveToFirst();
            try {
                gpsData.setData(C.getDouble(0), C.getDouble(1), C.getDouble(2), C.getLong(3));
                gpsData.setUserID(C.getInt(4));
                gpsData.setIsSaved(C.getInt(5) > 0);
            } catch (IndexOutOfBoundsException e) {
                DatabaseManager.getInstance().closeDatabase();
                return null;
            }
        } else
            return null;

        C.close();
        DatabaseManager.getInstance().closeDatabase();
        return gpsData;
    }


    public synchronized void changeSavedStatus(int id, boolean status) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        int statusInt;

        if (status){
            statusInt = 1;
        }else{
            statusInt = 0;
        }

        db.execSQL("Update " + GPSData.TABLE + " SET " + GPSData.KEY_IsSaved + " =  " + statusInt + " where " + GPSData.KEY_GPSDataId + " = " + id);
        DatabaseManager.getInstance().closeDatabase();
    }

    public synchronized void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(GPSData.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

}