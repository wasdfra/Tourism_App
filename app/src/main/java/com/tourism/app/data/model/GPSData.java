package com.tourism.app.data.model;

/**
 * Created by Franisco Azevedo on 02/05/2018.
 */

public class GPSData {

    public static final String TAG = GPSData.class.getSimpleName();
    public static final String TABLE = "GPSData";

    // Labels Table Columns names
    public static final String KEY_GPSDataId = "GPSDataID";
    public static final String KEY_UserID = "UserID";
    public static final String KEY_Latitude = "Latitude";
    public static final String KEY_Longitude = "Longitude";
    public static final String KEY_Altitude = "Altitude";
    public static final String KEY_DateTime = "DateTime";
    public static final String KEY_IsSaved = "Is_Saved";

    private int UserID;
    private double Latitude;
    private double Longitude;
    private double Altitude;
    private long DateTime;
    private boolean IsSaved;


    public double getLongitude() {
        return this.Longitude;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public double getAltitude() {
        return this.Altitude;
    }

    public long getDateTime() {
        return this.DateTime;
    }

    public boolean getIsSaved() {
        return this.IsSaved;
    }

    public int getUserID() {
        return this.UserID;
    }

    public void setIsSaved(boolean isSaved) {this.IsSaved = isSaved;}

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }


    public void setData(double Lat, double Long, double Alt, long Date) {
        this.Latitude = Lat;
        this.Longitude = Long;
        this.Altitude = Alt;
        this.DateTime = Date;
    }

}

