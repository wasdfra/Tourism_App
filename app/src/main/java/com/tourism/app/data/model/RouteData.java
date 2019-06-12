package com.tourism.app.data.model;

/**
 * Created by estgl3264 on 02/05/2018.
 */

public class RouteData {

    public static final String TAG = RouteData.class.getSimpleName();
    public static final String TABLE = "GPSData";

    // Labels Table Columns names
    public static final String KEY_RouteDataID = "RouteDataID";
    public static final String KEY_UserID = "UserID";
    public static final String KEY_RouteData = "Data";

    private int UserID;
    private int RouteID;
    private String RouteData;


    //-----Get-----//
    public int getRouteID() {
        return this.RouteID;
    }

    public String getRouteData() { return this.RouteData; }

    public int getUserID() {
        return this.UserID;
    }


    //-----Set----//
    public void setRouteData(String RouteData) {this.RouteData = RouteData;}

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }


}

