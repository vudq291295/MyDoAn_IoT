package com.dqv.smarthome.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Holder.MainHolder;
import com.dqv.smarthome.Holder.ScriptHolder;
import com.dqv.smarthome.Model.EquipmentModel;
import com.dqv.smarthome.Model.ScriptDetailsModel;
import com.dqv.smarthome.Model.ScriptModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Mqtt.MqttHelper;
import com.dqv.smarthome.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScriptAdapter extends RecyclerView.Adapter<ScriptHolder> {
    public static final String TAG = ScriptAdapter.class.getSimpleName();

    Context mContext;
    View v,view;
    MqttHelper mqttHelper;
    private ArrayList<ScriptModel> mEquipst = new ArrayList<ScriptModel>();
    public String currentToken ="";
    public String urlGetDetailScript = UrlModel.url_getDetailsScript;

    public ScriptAdapter(ArrayList<ScriptModel> mRoomsLst, Context mContext, MqttHelper mqttHelper) {
        this.mEquipst = mRoomsLst;
        this.mContext = mContext;
        this.mqttHelper = mqttHelper;
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        this.mqttHelper.publish("DV1/20/12","0");

        Log.d(TAG, "RoomAdapter: "+mRoomsLst.size());
    }

    @NonNull
    @Override
    public ScriptHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_script, viewGroup, false);
        ScriptHolder vh = new ScriptHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScriptHolder scriptHolder,final int i) {
        scriptHolder.mTittle.setText(mEquipst.get(i).getName());
        Picasso.with(mContext)
                .load("https://vuongquocnoithat.vn/images/2018/01/04/Bo-tri-den-trong-phong-ngu-dep-am-ap-thu-gian-nhat-6.jpg")
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(scriptHolder.imageView);
        scriptHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailsScript(mEquipst.get(i).getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mEquipst.size();
    }

    public void getDetailsScript(int idScript){
        StringRequest request = new StringRequest(Request.Method.GET, urlGetDetailScript+"/"+idScript, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    ScriptModel model = new ScriptModel();
                    JSONObject jObj = new JSONObject(response);
                    if(!jObj.getBoolean("error")){
                        JSONObject jObjData = jObj.getJSONObject("data");
                        model.setId(jObjData.getInt("id"));
                        model.setName(jObjData.getString("name"));
                        JSONArray jsonArray = jObjData.getJSONArray("details");
                        for(int i =0 ;i<jsonArray.length();i++){
                            JSONObject objTemp = jsonArray.getJSONObject(i);
                            ScriptDetailsModel detailsScript = new ScriptDetailsModel();
                            detailsScript.setEquipmentID(objTemp.getInt("equipmentID"));
                            detailsScript.setEquipmentChanel(objTemp.getInt("equipmentChanel"));
                            detailsScript.setEquipmentPort(objTemp.getInt("equipmentPort"));
                            detailsScript.setEquipmentName(objTemp.getString("equipmentName"));
                            detailsScript.setRoomID(objTemp.getInt("roomID"));
                            detailsScript.setRoomName(objTemp.getString("roomName"));
                            detailsScript.setStatus(objTemp.getInt("status"));
                            detailsScript.setScripID(objTemp.getInt("scripID"));
                            model.details.add(detailsScript);
                        }
                        if(model.getDetails().size()>0){
                            for(int i =0;i<model.getDetails().size();i++ ){
                                Log.d(TAG, "onClick: "+i);
                                String dataSend = model.getDetails().get(i).getStatus()+"";
                                String topic = "DV1/"+model.getDetails().get(i).getEquipmentChanel()+"/"+model.getDetails().get(i).getEquipmentPort() ;
                                Log.d(TAG, "onClick: "+topic + " - " + dataSend);
                                mqttHelper.publish(topic,dataSend);
                            }
                        }

                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(mContext,
                            ex+"", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse() returned: " + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error.networkResponse.statusCode == 401){
                    Toast.makeText(mContext,
                            "Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại!!!", Toast.LENGTH_LONG).show();

                }
                Toast.makeText(mContext,
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
