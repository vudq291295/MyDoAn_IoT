package com.dqv.smarthome.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dqv.smarthome.Adapter.AutoFitGridLayoutManager;
import com.dqv.smarthome.Adapter.MainAdapter;
import com.dqv.smarthome.Adapter.RoomAdapter;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.ConnSqlite.SQLiteHandler;
import com.dqv.smarthome.Dialog.DialogEquipment;
import com.dqv.smarthome.Dialog.ManagerDialog;
import com.dqv.smarthome.Model.EquipmentModel;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Model.UserModel;
import com.dqv.smarthome.Mqtt.MqttHelper;
import com.dqv.smarthome.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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
    public String urlGetAllEquipmentByRoom = UrlModel.url_getALlEquipmentByRoom;

    public String urlGetAllRoom = UrlModel.url_getALlRoomn;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtWebsite, twHome;
    public FloatingActionsMenu fam;
    public FloatingActionButton fammm;


    public static int navItemIndex = 0;
    public SQLiteHandler db;
    public MqttHelper mqttHelper;
    public RecyclerView mRecyclerView;
    ArrayList<EquipmentModel> listTG = new ArrayList<EquipmentModel>();
    MainAdapter mainAdapter;
    String currentToken = "";
    int currentRoomID = 0;
    FragmentManager fm;

    View.OnClickListener btnClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_tonghop:
                    getAllEquipment();
                    currentRoomID = -1;
                    fam.collapse();
                    break;
                default:
                    currentRoomID = v.getId();
                    getAllEquipmentByRoomId(currentRoomID);
                    fam.collapse();
                    break;
            }
            Log.d(TAG, "onClick currentRoomID : " + currentRoomID);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteHandler(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fm = getSupportFragmentManager();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.main_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        fam = (FloatingActionsMenu) findViewById(R.id.fab_filter_new);
        fammm = (FloatingActionButton) findViewById(R.id.fab_fab_main);
        final ImageView dimmedBackground= (ImageView) findViewById(R.id.dimmedBackground);
        dimmedBackground.setVisibility(View.GONE);
        final FloatingActionButton fab_tonghop = (FloatingActionButton) findViewById(R.id.fab_tonghop);
        fab_tonghop.setOnClickListener(btnClickListner);

        fammm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isExpanded()) {
                    fam.collapse();
//                    fammm.setIcon(R.drawable.filter);
                } else {
                    fam.expand();
//                    fammm.setIcon(R.drawable.cancel_filter);
                }
            }
        });

        fam.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                dimmedBackground.setVisibility(View.VISIBLE);
                fammm.setIcon(R.drawable.cancel_filter);

            }

            @Override
            public void onMenuCollapsed() {
                dimmedBackground.setVisibility(View.GONE);
                fammm.setIcon(R.drawable.filter);
            }

        });
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        //set Up MQTT


        mRecyclerView = (RecyclerView) findViewById(R.id.list_main);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainAdapter = new MainAdapter(listTG,getApplicationContext(),MyApplication.getInstance().getMQTTHelper(),fm);
        mRecyclerView.setAdapter(mainAdapter);
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
//        getAllEquipment();
        getAllRoom();
        MyApplication.getInstance().getMQTTHelper().setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

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
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                    case R.id.nav_script:
                        startActivity(new Intent(MainActivity.this, ScriptActivity.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addEquip:
                if(currentRoomID >0){
                    EquipmentModel model = new EquipmentModel();
                    model.setRoomId(currentRoomID);
                    DialogEquipment userInfoDialog = DialogEquipment.newInstance(model);
                    userInfoDialog.show(fm, null);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Vui lòng chọn phòng muốn thêm mới", Toast.LENGTH_LONG).show();

                }

                return true;
            default:
                return false;

        }
    }

    private void logout() {
        MyApplication.getInstance().logout(db);
    }

    public void getAllEquipment(){
        JSONObject postparams = new JSONObject();
        listTG.clear();
        mainAdapter.notifyDataSetChanged();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlGetAllEquipment,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    JSONObject jObj = response;
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
                            model.setNameRoom(objTemp.getString("nameRoom"));

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
                Log.d(TAG, "onErrorResponse() returned: " + error);

                if(error.networkResponse.statusCode == 401){
                    MyApplication.getInstance().logout(db);
                    Toast.makeText(getApplicationContext(),
                            "Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại!!!", Toast.LENGTH_LONG).show();

                }
                Toast.makeText(getApplicationContext(),
                        error.networkResponse.statusCode+"", Toast.LENGTH_LONG).show();

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
    int count;

    public void getAllRoom(){
        JSONObject postparams = new JSONObject();
        final ArrayList<RoomModel> listTG = new ArrayList<RoomModel>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlGetAllRoom,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    JSONObject jObj = response;
                    if(!jObj.getBoolean("error")){
                        JSONArray jsonArray = jObj.getJSONArray("data");
                        for(int i =0 ;i<jsonArray.length();i++){
                            JSONObject objTemp = jsonArray.getJSONObject(i);
                            RoomModel roomModel = new RoomModel();
                            roomModel.setId(objTemp.getInt("id"));
                            roomModel.setName(objTemp.getString("name"));
                            roomModel.setChanel(objTemp.getInt("chanel"));
                            roomModel.setCountEquipment(objTemp.getInt("chanel"));

                            listTG.add(roomModel);
                        }
                        if(listTG.size() > 0){
                            for (int i =0 ;i < listTG.size();i++){
                                count = i;
                                FloatingActionButton fab = new FloatingActionButton(getApplicationContext());
                                fab.setId(listTG.get(count).getId());
                                fab.setTitle(listTG.get(i).getName());
                                fab.setColorNormal(getResources().getColor(R.color.colorPrimary));
                                fab.setIcon(R.drawable.house);
                                fab.setOnClickListener(btnClickListner);

                                fam.addButton(fab);
                            }
                        }
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
                Toast.makeText(getApplicationContext(),
                        error+"", Toast.LENGTH_LONG).show();
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

    public void getAllEquipmentByRoomId(int roomId){
        listTG.clear();
        mainAdapter.notifyDataSetChanged();
        StringRequest request = new StringRequest(Request.Method.GET, urlGetAllEquipmentByRoom+"/"+roomId, new Response.Listener<String>() {
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
                            model.setNameRoom(objTemp.getString("nameRoom"));
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
