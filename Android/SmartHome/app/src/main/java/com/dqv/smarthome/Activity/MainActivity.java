package com.dqv.smarthome.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dqv.smarthome.Adapter.AutoFitGridLayoutManager;
import com.dqv.smarthome.Adapter.MainAdapter;
import com.dqv.smarthome.Adapter.RoomAdapter;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.ConnSqlite.SQLiteHandler;
import com.dqv.smarthome.Model.EquipmentModel;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Model.UserModel;
import com.dqv.smarthome.Mqtt.MqttHelper;
import com.dqv.smarthome.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    public String urlGetAllEquipment = UrlModel.url_getALlEquipment;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtWebsite, twHome;

    public static int navItemIndex = 0;
    public SQLiteHandler db;
    public MqttHelper mqttHelper;
    public RecyclerView mRecyclerView;
    ArrayList<EquipmentModel> listTG = new ArrayList<EquipmentModel>();
    MainAdapter mainAdapter;
    String currentToken = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteHandler(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.main_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);

        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        //set Up MQTT

        startMqtt();

        mRecyclerView = (RecyclerView) findViewById(R.id.list_main);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainAdapter = new MainAdapter(listTG,getApplicationContext(),mqttHelper);
        mRecyclerView.setAdapter(mainAdapter);
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
//        getAllEquipment();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mqttHelper.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startMqtt();
        getAllEquipment();
    }

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
                Log.d(TAG, "messageArrived: TOPIC: "+ topic + " - MSG : "+mqttMessage);
                String[] lstToPic =  topic.split("/");
                Log.d(TAG, "messageArrived: TOPIC 1: "+ lstToPic[0]);
                Log.d(TAG, "messageArrived: TOPIC 2: "+ lstToPic[1]);
                Log.d(TAG, "messageArrived: TOPIC 3: "+ lstToPic[2]);

                for(int i =0;i<listTG.size();i++){
                    if(listTG.get(i).getChanel() == Integer.parseInt(lstToPic[1])){
                        if(listTG.get(i).getPortOutput() == Integer.parseInt(lstToPic[2])){
                            listTG.get(i).setStatus(Integer.parseInt(mqttMessage.toString()));
                        }
                    }
                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    private void loadNavHeader() {
        // name, website
        if (MyApplication.getInstance().getPrefManager().getUser() != null)
            txtName.setText(MyApplication.getInstance().getPrefManager().getUser().getName().toString().trim());
    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_logout:
                        logout();
                        return true;
                    case R.id.nav_room:
                        startActivity(new Intent(MainActivity.this, RoomActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
    private void logout() {
        MyApplication.getInstance().logout(db);
    }

    public void getAllEquipment(){
        listTG.clear();
        mainAdapter.notifyDataSetChanged();
        StringRequest request = new StringRequest(Request.Method.GET, urlGetAllEquipment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    if(!jObj.getBoolean("error")){
                        JSONArray jsonArray = jObj.getJSONArray("data");
                        for(int i =0 ;i<jsonArray.length();i++){
                            JSONObject objTemp = jsonArray.getJSONObject(i);
                            EquipmentModel model = new EquipmentModel();
                            model.setName(objTemp.getString("name"));
                            model.setId(objTemp.getInt("id"));
                            model.setPortOutput(objTemp.getInt("portOutput"));
                            model.setRoomId(objTemp.getInt("roomId"));
                            model.setStatus(objTemp.getInt("status"));
                            model.setChanel(objTemp.getInt("chanel"));

                            listTG.add(model);
                        }
                        mainAdapter.notifyDataSetChanged();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),
                            ex+"", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse() returned: " + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error.networkResponse.statusCode == 401){
                    MyApplication.getInstance().logout(db);
                    Toast.makeText(getApplicationContext(),
                            "Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại!!!", Toast.LENGTH_LONG).show();

                }
                Toast.makeText(getApplicationContext(),
                        error.networkResponse.statusCode+"", Toast.LENGTH_LONG).show();

                Log.d(TAG, "onErrorResponse() returned: " + error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", "Bearer "+currentToken);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }
}
