package com.dqv.smarthome.Application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dqv.smarthome.Authentication.SessionManager;
import com.dqv.smarthome.ConnSqlite.SQLiteHandler;
import com.dqv.smarthome.FirstActivity;
import com.dqv.smarthome.Model.UserModel;
import com.dqv.smarthome.Mqtt.MqttHelper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

public class MyApplication extends Application {
    private static Context context;
    public static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;
    private SQLiteHandler db;
    private int userId;
    private SessionManager pref;
    public MqttHelper mqttHelper;

//    private Gson mGSon;

    //@Override
    //protected void attachBaseContext(Context base) {
    //    super.attachBaseContext(LanguageUtils.onAttach(base));
    //}



    @Override
    public void onCreate() {
        super.onCreate();
        db = new SQLiteHandler(getApplicationContext());
        mInstance = this;
        //startMqtt();
//        mGSon = new Gson();

    }

    @Override
    public void onTerminate() {
        mqttHelper.disconnect();
        super.onTerminate();
    }

    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    /**
     *VUDQ
     *09/03/2018
     *get current instance
     * **/
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    /**
     *VUDQ
     *09/03/2018
     *get queue by request
     * **/
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    /**
     *VUDQ
     *09/03/2018
     *get current PrefManager
     * **/
    public SessionManager getPrefManager() {
        if (pref == null) {
            pref = new SessionManager(this);
        }

        return pref;
    }

    public MqttHelper getMQTTHelper() {
        return mqttHelper;
    }

    public void setMqttHelper(MqttHelper mqttHelper) {
        this.mqttHelper = mqttHelper;
    }

    /**
     *VUDQ
     *09/03/2018
     *add queue to request call api
     * **/
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    /**
     *VUDQ
     *09/03/2018
     *camcle queue to request call api
     * **/
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    //function login
    public void logout(SQLiteHandler conn) {
        //Log.d(TAG, "logout: "+pref.getUser().getName());
        UserModel obj = new UserModel();
        conn.deleteUsers();
        conn.close();
        pref.clear();
        Intent intent = new Intent(this, FirstActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //function login
//    public void logoutNoActivity(SQLiteHandler conn) {
//        if(pref.getUser()!=null) {
//            UserModel obj = new UserModel();
//            obj = conn.getUserByUserName(pref.getUser().getName());
//
//            Log.d(TAG, "logout: " + obj.getName());
//            conn.setDefaulAllUser();
//            conn.setLoginUser(obj, db.STATUS_LOGOUT);
//            conn.close();
//            pref.clear();
//        }
//    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());

        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}
