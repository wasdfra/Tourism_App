package com.tourism.app.data.handler;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.tourism.app.data.DatabaseManager;
import com.tourism.app.data.model.Session;

/**
 * Created by Franisco Azevedo on 02/05/2018.
 */

public class SessionHandler {

    private static Session session;

    public SessionHandler() {
        session = new Session();
    }

    public static String createTable() {
        return ("CREATE TABLE "
                + Session.TABLE + "("
                + Session.KEY_SessionID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Session.KEY_FSessionID + "  INTEGER ,"
                + Session.KEY_UserID + " INTEGER, "
                + Session.KEY_SessStrDt + " INTEGER, "
                + Session.KEY_SessStpDt + " INTEGER)");
    }

    public synchronized int insert(Session sess) {
        int SessionID;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(Session.KEY_FSessionID, sess.getFSessionID());
        values.put(Session.KEY_UserID, sess.getUserID());
        values.put(Session.KEY_SessStrDt, sess.getSessStrDt());
        values.put(Session.KEY_SessStpDt, sess.getSessStpDt());


        if (DatabaseManager.isOpen())
            SessionID = (int) db.insert(Session.TABLE, null, values);
        else
            throw new IllegalStateException("Base de dados fechada");

        DatabaseManager.getInstance().closeDatabase();
        return SessionID;
    }

    public synchronized void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Session.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }


}