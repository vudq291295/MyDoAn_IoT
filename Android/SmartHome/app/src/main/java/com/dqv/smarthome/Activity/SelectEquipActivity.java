package com.dqv.smarthome.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.ConnSqlite.SQLiteHandler;
import com.dqv.smarthome.Model.EquipmentModel;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectEquipActivity extends AppCompatActivity {
    public static String TAG = SelectEquipActivity.class.getSimpleName();

    ArrayList<EquipmentModel> listTG = new ArrayList<EquipmentModel>();
    public String urlGetAllEquipment = UrlModel.url_getALlEquipment;
    public String urlGetAllEquipmentByRoom = UrlModel.url_getALlEquipmentByRoom;

    public String urlGetAllRoom = UrlModel.url_getALlRoomn;
    String currentToken = "";
    public SQLiteHandler db;
    public FloatingActionsMenu fam;
    public FloatingActionButton fammm;
    int currentRoomID = 0;
    CustomListView customAdapter;
    String currentTbName ="";
    int currentTbID = 0;
    View.OnClickListener btnClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_tonghop_celect_tb:
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
        setContentView(R.layout.activity_select_equip);
        db = new SQLiteHandler(getApplicationContext());
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();

        fam = (FloatingActionsMenu) findViewById(R.id.fab_filter_select_tb);
        fammm = (FloatingActionButton) findViewById(R.id.fab_fab_select_tb);
        final ImageView dimmedBackground= (ImageView) findViewById(R.id.dimmedBackground_selectTB);
        dimmedBackground.setVisibility(View.GONE);
        FloatingActionButton fab_tonghop = (FloatingActionButton) findViewById(R.id.fab_tonghop_celect_tb);
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
        ListView lw = (ListView)findViewById(R.id.list_SelectTB);
        customAdapter = new CustomListView(this, R.layout.item_selecttb, listTG);
        lw.setAdapter(customAdapter);
        getAllRoom();
        getAllEquipment();

    }

    @Override
    public void onBackPressed() {
        Intent data = getIntent();
//        Intent data = new Intent(this,AddScheduleTBActivity.class);
        Bundle b = new Bundle();
        b.putString("equipmentName",currentTbName);
        b.putInt("equipmentID",currentTbID);
        data.putExtras(b);
        data.putExtra("a","a");
        setResult(Activity.RESULT_OK,data);
        super.onBackPressed();
    }

    public void getAllEquipment(){
        JSONObject postparams = new JSONObject();
        listTG.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlGetAllEquipment,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() getAllEquipment returned: " + response);
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
                        customAdapter.notifyDataSetChanged();
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
        StringRequest request = new StringRequest(Request.Method.GET, urlGetAllEquipmentByRoom+"/"+roomId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse() getAllEquipmentByRoomId returned: " + response);
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
                        customAdapter.notifyDataSetChanged();
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


    class CustomListView extends ArrayAdapter<EquipmentModel>{

        private int resourceLayout;
        private Context mContext;

        public CustomListView(Context context, int resource,ArrayList<EquipmentModel> items) {
            super(context, resource,items);
            this.mContext = context;
            this.resourceLayout = resource;
        }

        @Override
        public View getView(int position, View convertView,ViewGroup parent) {
            View v = convertView;
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_selecttb, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.ten_tb);
                viewHolder.tvNumberPhone = (TextView) convertView.findViewById(R.id.ten_phong);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final EquipmentModel obj = getItem(position);
            viewHolder.tvName.setText(obj.getName());
            viewHolder.tvNumberPhone.setText(obj.getNameRoom());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentTbName = obj.getName();
                    currentTbID = obj.getId();
                    onBackPressed();
                }
            });
            return convertView;
        }

        public class ViewHolder {
            TextView tvName, tvNumberPhone, tvAvatar;

        }

    }
}

