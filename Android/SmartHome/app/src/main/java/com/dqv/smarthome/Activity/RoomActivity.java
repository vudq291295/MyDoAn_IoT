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
import com.android.volley.toolbox.StringRequest;
import com.dqv.smarthome.Adapter.RoomAdapter;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Model.UserModel;
import com.dqv.smarthome.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomActivity extends AppCompatActivity {
    public static final String TAG = RoomActivity.class.getSimpleName();

    public String urlGetAllRoom = UrlModel.url_getALlRoomn;

    private Toolbar toolbar;
    public RecyclerView mRecycleview; // bien recycleview
    public FloatingActionButton fammm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        toolbar = (Toolbar) findViewById(R.id.toolbarRoom);
        mRecycleview = (RecyclerView) findViewById(R.id.list_room);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tw = toolbar.findViewById(R.id.app_bar_title) ;
        tw.setText("Phòng");
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

        mRecycleview.setHasFixedSize(false);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        String currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        SynchData(mRecycleview,currentToken);
        final Intent intent = new Intent(this,AddRoomActivity.class);
        fammm = (FloatingActionButton) findViewById(R.id.fab_fab_room);

        fammm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }

    public void SynchData(final RecyclerView recyclerView, final String token){
        final ArrayList<RoomModel> listTG = new ArrayList<RoomModel>();
        StringRequest request = new StringRequest(Request.Method.GET, urlGetAllRoom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    if(!jObj.getBoolean("error")){
                        JSONArray jsonArray = jObj.getJSONArray("data");
                        for(int i =0 ;i<jsonArray.length();i++){
                            JSONObject objTemp = jsonArray.getJSONObject(i);
                            RoomModel roomModel = new RoomModel();
                            roomModel.setName(objTemp.getString("name"));
                            roomModel.setChanel(objTemp.getInt("chanel"));
                            roomModel.setCountEquipment(objTemp.getInt("chanel"));
                            listTG.add(roomModel);
                        }
                        recyclerView.setAdapter(new RoomAdapter(listTG,getApplicationContext()));
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
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }
}
