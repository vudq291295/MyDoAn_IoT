package com.dqv.smarthome.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dqv.smarthome.Adapter.RoomAdapter;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRoomActivity extends AppCompatActivity {

    public static final String TAG = AddRoomActivity.class.getSimpleName();

    public String urlGetAddRoom = UrlModel.url_insertRoomn;

    public EditText mNameRoom,mChanel;
    public String currentToken ="";
    public Button mAddRoom;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        toolbar = (Toolbar) findViewById(R.id.toolbarAddRoom);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tw = toolbar.findViewById(R.id.app_bar_title) ;
        tw.setText("Thêm mới phòng");
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

        mNameRoom = (EditText) findViewById(R.id.etNamePhong);
        mChanel = (EditText) findViewById(R.id.etKenhPhong);
        mAddRoom = (Button) findViewById(R.id.btnLuuAddRoom);
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        mAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameRoom.getText().toString().trim();
                String chanel = mChanel.getText().toString().trim();
                Log.d(TAG, "onClick name: "+name);
                Log.d(TAG, "onClick chanel: "+chanel);

                if(name == null || name.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Không được để trống tên", Toast.LENGTH_LONG).show();
                }
                else {
                    if(chanel == null || chanel.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Không được để trống kênh", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d(TAG, "onClick: "+name);
                        RoomModel obj = new RoomModel();
                        obj.setName(name);
                        obj.setChanel(Integer.parseInt(chanel));
                        InsertRoomApi(obj);
                    }

                }

            }
        });
    }

    public void InsertRoomApi(final RoomModel model){
        JSONObject postparams = new JSONObject();
        try
        {
            postparams.put("name", model.getName());
            postparams.put("chanel", model.getChanel());
        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, urlGetAddRoom,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(getApplicationContext(),RoomActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                msg+"", Toast.LENGTH_LONG).show();
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
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }



}
