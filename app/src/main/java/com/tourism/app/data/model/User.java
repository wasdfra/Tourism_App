package com.tourism.app.data.model;

/**
 * Created by estgl3264 on 03/05/2018.
 */

public class User {


    public static final String TAG = User.class.getSimpleName();
    public static final String TABLE = "User";

    // Labels Table Columns names

    public static final String KEY_UserID = "UserID";
    public static final String KEY_Server_UserID = "SUserID";
    public static final String KEY_PassWord = "Password";
    public static final String KEY_UserType = "UserType";
    public static final String KEY_Email = "Email";

    private int ServUserID;
    private String Password;
    private String UserType;
    private String Email;

    public int getServUserID() {
        return this.ServUserID;
    }

    public String getPassword() {
        return this.Password;
    }

    public String getUserType() {
        return this.UserType;
    }

    public String getEmail() {
        return this.Email;
    }


    public void setData(int ServUserID, String password, String UserType, String email) {
        this.ServUserID = ServUserID;
        this.Password = password;
        this.UserType = UserType;
        this.Email = email;
    }

}
