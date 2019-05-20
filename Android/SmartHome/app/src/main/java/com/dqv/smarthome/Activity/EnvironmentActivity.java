package com.dqv.smarthome.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dqv.smarthome.Adapter.EnvrAdapter;
import com.dqv.smarthome.Adapter.ScriptAdapter;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.ConnSqlite.SQLiteHandler;
import com.dqv.smarthome.Model.EnvironmentModel;
import com.dqv.smarthome.Model.ScriptModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Mqtt.MqttHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.dqv.smarthome.R;

public class EnvironmentActivity extends AppCompatActivity {
    public static final String TAG = EnvironmentActivity.class.getSimpleName();

    public String urlGetAllScript = UrlModel.url_getAllEnviroment;
    private Toolbar toolbar;
    public RecyclerView mRecycleview; // bien recycleview
    String currentToken = "";
    EnvrAdapter scriptAdapter;
    ArrayList<EnvironmentModel> listScript = new ArrayList<EnvironmentModel>();
    public MqttHelper mqttHelper;
    public SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        toolbar = (Toolbar) findViewById(R.id.toolbarEnvr);
        db = new SQLiteHandler(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tw = toolbar.findViewById(R.id.app_bar_title) ;
        tw.setText("Kiểm soát môi trường");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ImageView imageView = findViewById(R.id.app_bar_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        mqttHelper = MyApplication.getInstance().getMQTTHelper();
        mRecycleview = (RecyclerView) findViewById(R.id.list_envr);
        mRecycleview.setHasFixedSize(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        scriptAdapter = new EnvrAdapter(listScript,getApplicationContext(),mqttHelper);
        mRecycleview.setAdapter(scriptAdapter);
        getAllEnvr();
    }

    public void getAllEnvr(){
        JSONObject postparams = new JSONObject();
        listScript.clear();
        scriptAdapter.notifyDataSetChanged();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlGetAllScript,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    JSONObject jObj = response;
                    if(!jObj.getBoolean("error")){
                        JSONArray jsonArray = jObj.getJSONArray("data");
                        for(int i =0 ;i<jsonArray.length();i++){
                            JSONObject objTemp = jsonArray.getJSONObject(i);
                            EnvironmentModel model = new EnvironmentModel();
                            model.setValue(objTemp.getInt("value"));
                            model.setRoomId(objTemp.getInt("roomId"));
                            listScript.add(model);
                        }
                        scriptAdapter.notifyDataSetChanged();
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
}