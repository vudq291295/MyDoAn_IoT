package com.dqv.smarthome.ConnSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dqv.smarthome.Model.UserModel;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TOKEN = "token";

    private static final String KEY_STATUS = "status";
    //status user
    public static final String STATUS_NOLOGIN = "0"; // uset ko daang nhaop
    public static final String STATUS_LOGIN = "1"; // user dang dang nhap
    public static final String STATUS_LOGOUT = "2"; // user cuoi cung khi logout

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create sqlite db
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_USER);
        String CREATE_LOGIN_TABLE = "CREATE TABLE "+TABLE_USER+"("
                +KEY_ID+" INTEGER PRIMARY KEY,"
                +KEY_NAME+" TEXT,"
                +KEY_PASSWORD+" TEXT,"
                +KEY_TOKEN+" TEXT,"
                +KEY_STATUS+" TEXT"+")";
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database tables created");
        Log.d(TAG, "onCreate() called with: " + CREATE_LOGIN_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    //add user
    public void addUser(UserModel obj) {
        SQLiteDatabase db = this.getWritableDatabase();
//        onCreate(db);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, obj.getName()); // Name
        values.put(KEY_ID, obj.getId()); // user_id
        values.put(KEY_PASSWORD,obj.getPasword());
        values.put(KEY_TOKEN,obj.getToken());
        values.put(KEY_STATUS, STATUS_LOGIN); // user_id

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: " + id);
    }




    //get current user store by sqlite
    public UserModel getCurrentUser() {
        UserModel result=  new UserModel();
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row

        cursor.moveToFirst();
        Log.d(TAG, "getUserDetailsLogout() returned: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            user.put("username", cursor.getString(1));
            user.put("password",cursor.getString(2));
            user.put("token",cursor.getString(3));
            user.put(KEY_STATUS,cursor.getString(4));
            result.setName( cursor.getString(1));
            result.setPasword( cursor.getString(2));
            result.setToken( cursor.getString(3));
        }

        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return result;
    }

    //get current user store by sqlite
    public UserModel getUserByUserName(String userName) {
        Log.d(TAG, "getUserByUserName: "+userName);
        UserModel result=  new UserModel();
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "+KEY_NAME+" = '"+userName+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        Log.d(TAG, "getUserByUserName() returned: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            user.put("username", cursor.getString(1));
            user.put("password",cursor.getString(2));
            user.put(KEY_STATUS,cursor.getString(3));
            result.setName(cursor.getString(1));
            result.setPasword(cursor.getString(2));
            cursor.close();
            db.close();
            return result;
        }
        else{
            cursor.close();
            db.close();
            return null;
        }
    }


    //delete all user
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);

        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
