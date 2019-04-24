package com.dqv.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dqv.smarthome.Activity.AddScheduleTBActivity;
import com.dqv.smarthome.Activity.ScheduleTBActivity;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Holder.ScheduleHolder;
import com.dqv.smarthome.Model.ScheduleModel;
import com.dqv.smarthome.Model.ScriptModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Mqtt.MqttHelper;
import com.dqv.smarthome.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleTBAdapter extends RecyclerView.Adapter<ScheduleHolder> {
    public static final String TAG = ScheduleTBAdapter.class.getSimpleName();
    public String url_updateSchedule = UrlModel.url_updateSchedule;

    Context mContext;
    View v,view;
    private ArrayList<ScheduleModel> mEquipst = new ArrayList<ScheduleModel>();
    public String currentToken ="";
    public String urlGetDetailScript = UrlModel.url_getDetailsScript;

    public ScheduleTBAdapter(ArrayList<ScheduleModel> mRoomsLst, Context mContext) {
        this.mEquipst = mRoomsLst;
        this.mContext = mContext;
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
    }


    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_tb, viewGroup, false);
        ScheduleHolder vh = new ScheduleHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder scheduleHolder, final int i) {
        scheduleHolder.mTime.setText(mEquipst.get(i).getTimeStart().toString());
        if(mEquipst.get(i).getDay()!=null){
            if(mEquipst.get(i).getDay().indexOf(scheduleHolder.mDay2.getText().toString().substring(0,1)) >= 0){
                scheduleHolder.mDay2.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            if(mEquipst.get(i).getDay().indexOf(scheduleHolder.mDay3.getText().toString().substring(0,1)) >= 0){
                scheduleHolder.mDay3.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            if(mEquipst.get(i).getDay().indexOf(scheduleHolder.mDay4.getText().toString().substring(0,1)) >= 0){
                scheduleHolder.mDay4.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            if(mEquipst.get(i).getDay().indexOf(scheduleHolder.mDay5.getText().toString().substring(0,1)) >= 0){
                scheduleHolder.mDay5.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            if(mEquipst.get(i).getDay().indexOf(scheduleHolder.mDay6.getText().toString().substring(0,1)) >= 0){
                scheduleHolder.mDay6.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            if(mEquipst.get(i).getDay().indexOf(scheduleHolder.mDay7.getText().toString().substring(0,1)) >= 0){
                scheduleHolder.mDay7.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            if(mEquipst.get(i).getDay().indexOf(scheduleHolder.mDayC.getText().toString().substring(0,1)) >= 0){
                scheduleHolder.mDayC.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
        }
        String status = "";
        if(mEquipst.get(i).getStatusEquipment() == 1){
            status = "BẬT";
        }
        else if(mEquipst.get(i).getStatusEquipment() == 0){
            status = "TẮT";
        }
        scheduleHolder.mEquip.setText(status+" "+mEquipst.get(i).getEquipmentName()+"");
        Log.d(TAG, "onBindViewHolder mEquipst.get(i).getStatusEquipment(): "+ mEquipst.get(i).getStatusEquipment());
        scheduleHolder.mStatus.setChecked(mEquipst.get(i).getStatus()==1);
        scheduleHolder.mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEquipst.get(i).setStatus(1);
                }
                else {
                    mEquipst.get(i).setStatus(0);
                }
                UpdateEquipApi(mEquipst.get(i));
            }
        });
        scheduleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddScheduleTBActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",mEquipst.get(i).getId());
                bundle.putString("name",mEquipst.get(i).getName());
                bundle.putString("timeStart",mEquipst.get(i).getTimeStart());
                bundle.putInt("equipmentID",mEquipst.get(i).getEquipmentID());
                bundle.putString("day",mEquipst.get(i).getDay());
                bundle.putInt("status",mEquipst.get(i).getStatus());
                bundle.putInt("statusEquipment",mEquipst.get(i).getStatusEquipment());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEquipst.size();
    }

    public void UpdateEquipApi(final ScheduleModel model){
        JSONObject postparams = new JSONObject();
        String url  = "";
        try
        {
            postparams.put("id", model.getId());
            postparams.put("name", model.getName());
            postparams.put("timeStart", model.getTimeStart()+":00");
            postparams.put("equipmentID", model.getEquipmentID()+"");
            postparams.put("day", model.getDay());
            postparams.put("status", model.getStatus()+"");
            postparams.put("statusEquipment", model.getStatusEquipment()+"");
            Log.d(TAG, "UpdateEquipApi: "+postparams);

        }
        catch (Exception ex){

        }
        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, url_updateSchedule,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
//                        Intent intent = new Intent(mContext, ScheduleTBActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                    else {
//                        Toast.makeText(getApplicationContext(),
//                                msg+"", Toast.LENGTH_LONG).show();
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
                Toast.makeText(mContext,
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
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }
}
