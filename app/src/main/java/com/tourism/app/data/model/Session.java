package com.tourism.app.data.model;

/**
 * Created by estgl3264 on 02/05/2018.
 */

public class Session {


    public static final String TAG = Session.class.getSimpleName();
    public static final String TABLE = "Session";

    // Labels Table Columns names
    public static final String KEY_SessionID = "SessionID";
    public static final String KEY_FSessionID = "FSessionID";
    public static final String KEY_SessStrDt = "SessStrDt";
    public static final String KEY_SessStpDt = "SessStpDt";
    public static final String KEY_UserID = "UserID";


    private int FSessionID;
    private int UserID;
    private long SessStrDt;
    private long SessStpDt;


    public double getUserID() {
        return this.UserID;
    }

    public long getSessStrDt() {
        return this.SessStrDt;
    }

    public long getSessStpDt() {
        return this.SessStpDt;
    }

    public int getFSessionID() {
        return this.FSessionID;
    }

    public void setData(int FSessionID, int UserID, long SessStrDt, long SessStpDt) {
        this.UserID = UserID;
        this.SessStrDt = SessStrDt;
        this.SessStpDt = SessStpDt;
        this.FSessionID = FSessionID;
    }

}
