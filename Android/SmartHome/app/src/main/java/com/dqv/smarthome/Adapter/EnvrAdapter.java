package com.dqv.smarthome.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.dqv.smarthome.Holder.EnvrHolder;
import com.dqv.smarthome.Holder.ScriptHolder;
import com.dqv.smarthome.Model.EnvironmentModel;
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

public class EnvrAdapter extends RecyclerView.Adapter<EnvrHolder> {
    public static final String TAG = EnvrAdapter.class.getSimpleName();

    Context mContext;
    View v,view;
    MqttHelper mqttHelper;
    private ArrayList<EnvironmentModel> mEquipst = new ArrayList<EnvironmentModel>();
    public String currentToken ="";

    public EnvrAdapter(ArrayList<EnvironmentModel> mRoomsLst, Context mContext, MqttHelper mqttHelper) {
        this.mEquipst = mRoomsLst;
        this.mContext = mContext;
        this.mqttHelper = mqttHelper;
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        this.mqttHelper.publish("DV1/20/12","0");

        Log.d(TAG, "RoomAdapter: "+mRoomsLst.size());
    }

    @NonNull
    @Override
    public EnvrHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_evr, viewGroup, false);
        EnvrHolder vh = new EnvrHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EnvrHolder scriptHolder,final int i) {
        scriptHolder.mTittle.setText("Phòng: "+mEquipst.get(i).getRoomId());
        scriptHolder.mDes.setText("Nhiệt độ: "+mEquipst.get(i).getValue()+" °C");
        Picasso.with(mContext)
                .load("https://wi-images.condecdn.net/image/doEYpG6Xd87/crop/2040/f/weather.jpg")
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(scriptHolder.imageView);
    }


    @Override
    public int getItemCount() {
        return mEquipst.size();
    }
}
