package com.dqv.smarthome.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dqv.smarthome.Model.UserModel;

public class SessionManager {
    private String TAG = SessionManager.class.getSimpleName();

    //shared preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AFCVN";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_NOTIFICATIONS = "notifications";
    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_LAST_USER_ID = "last_user_id";
    private static final String KEY_IS_QA = "is_qa";

    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_PWD = "password";
    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_USER_LAST_TOKEN = "last_token";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }


    //set current User after Login Success
    public void setLogin(boolean isLoggedIn)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    //set current User after Login Success
    public void reNewSession(String token)
    {
        editor.putString(KEY_USER_TOKEN, token);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    // save current User after Login Success
    public void storeUser(UserModel user) {
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_PWD,user.getPasword());
        editor.putString(KEY_USER_TOKEN, user.getToken());
        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getName());
    }

    // get current User after Login Success
    public UserModel getUser() {
        if (pref.getString(KEY_USER_ID, null) != null) {
            String id, name, password,token,lang;
            id = pref.getString(KEY_USER_ID, null);
            name = pref.getString(KEY_USER_NAME, null);
            password = pref.getString(KEY_USER_PWD, null);
            token = pref.getString(KEY_USER_TOKEN, null);
            UserModel user = new UserModel(id, name, password);
            user.setToken(token);
            return user;
        }
        return null;
    }




    public void clear() {
        String lastUID = "";
        String lastTkn = "";
        String lastLng = "";
        String lastIsQA = "";

        lastUID = pref.getString(KEY_USER_ID,null);
        lastTkn = pref.getString(KEY_USER_TOKEN, null);
        lastIsQA = pref.getString(KEY_IS_QA,"F");

        Log.d(TAG, "clear: "+lastTkn);
        editor.clear().apply();
        editor.commit();
        editor.putString(KEY_LAST_USER_ID, lastUID);
        editor.putString(KEY_USER_LAST_TOKEN, lastTkn);

        editor.commit();
        Log.d(TAG, "clear pref login: done");
    }

}
