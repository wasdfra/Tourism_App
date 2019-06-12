package com.tourism.app.data.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tourism.app.data.DatabaseManager;
import com.tourism.app.data.model.User;


/**
 * Created by estgl3264 on 03/05/2018.
 */

public class UserHandler {

    private static User user;

    public UserHandler() {
        user = new User();
    }

    public static String createTable() {
        return ("CREATE TABLE "
                + User.TABLE + "("
                + User.KEY_UserID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + User.KEY_Server_UserID + "  INTEGER,"
                + User.KEY_PassWord + " TEXT, "
                + User.KEY_UserType + " TEXT, "
                + User.KEY_Email + " TEXT NOT NULL,"
                + " CONSTRAINT " + User.KEY_Email + "  UNIQUE (" + User.KEY_Email + ") )");
    }

    public synchronized int insert(User usr) {
        int UserID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(User.KEY_Server_UserID, usr.getServUserID());
        values.put(User.KEY_PassWord, usr.getPassword());
        values.put(User.KEY_UserType, usr.getUserType());
        values.put(User.KEY_Email, usr.getEmail());

        // Inserting Row
        UserID = (int) db.insert(User.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return UserID;
    }

    public synchronized User getData(String param, String pass) {
        User user = new User();
        Cursor C;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        C = db.rawQuery("Select "
                + User.KEY_Server_UserID + ","
                + User.KEY_PassWord + ","
                + User.KEY_UserType + ","
                + User.KEY_Email + " from " + User.TABLE + " where (" + User.KEY_Email + " = \"" + param + "\" and " + User.KEY_PassWord + " = \"" + pass + "\") or ( " + User.KEY_Server_UserID + " = \"" + param + "\" and " + User.KEY_PassWord + " = \"" + pass + "\")", null);

        Log.println(Log.ERROR, "Controlo query", C.toString());
        if (C != null) {
            C.moveToFirst();
            try {
                Log.println(Log.ERROR, "Controlo query - EMAIL", C.getString(3));
                user.setData(C.getInt(0), C.getString(1), C.getString(2), C.getString(3));
            } catch (IndexOutOfBoundsException e) {
                DatabaseManager.getInstance().closeDatabase();
                return null;
            }


        } else
            user = null;

        return user;
    }


    public synchronized void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }


}
