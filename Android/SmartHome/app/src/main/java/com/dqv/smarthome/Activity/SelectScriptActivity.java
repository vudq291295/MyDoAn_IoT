package com.dqv.smarthome.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class SelectScriptActivity extends AppCompatActivity {
    public static String TAG = SelectScriptActivity.class.getSimpleName();

    ArrayList<EquipmentModel> listTG = new ArrayList<EquipmentModel>();
    public String urlGetAllEquipment = UrlModel.url_getALlScript;
    String currentToken = "";
    public SQLiteHandler db;
    CustomListView customAdapter;
    String currentTbName ="";
    int currentTbID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_script);
        db = new SQLiteHandler(getApplicationContext());
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();

        ListView lw = (ListView)findViewById(R.id.list_SelectKB);
        customAdapter = new CustomListView(this, R.layout.item_selecttb, listTG);
        lw.setAdapter(customAdapter);
        getAllEquipment();

    }

    @Override
    public void onBackPressed() {
        Intent data = getIntent();
//        Intent data = new Intent(this,AddScheduleTBActivity.class);
        Bundle b = new Bundle();
        b.putString("scriptName",currentTbName);
        b.putInt("scriptID",currentTbID);
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_selectkb, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.ten_kb);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final EquipmentModel obj = getItem(position);
            viewHolder.tvName.setText(obj.getName());
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
            TextView tvName;

        }

    }
}

