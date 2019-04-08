package com.dqv.smarthome.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dqv.smarthome.Holder.MainHolder;
import com.dqv.smarthome.Holder.RoomHolder;
import com.dqv.smarthome.Model.EquipmentModel;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Mqtt.MqttHelper;
import com.dqv.smarthome.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainHolder> {
    public static final String TAG = RoomAdapter.class.getSimpleName();

    Context mContext;
    View v,view;
    MqttHelper mqttHelper;
    private ArrayList<EquipmentModel> mEquipst = new ArrayList<EquipmentModel>();

    public MainAdapter(ArrayList<EquipmentModel> mRoomsLst, Context mContext, MqttHelper mqttHelper) {
        this.mEquipst = mRoomsLst;
        this.mContext = mContext;
        this.mqttHelper = mqttHelper;

        Log.d(TAG, "RoomAdapter: "+mRoomsLst.size());
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, viewGroup, false);
        MainHolder vh = new MainHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, final int i) {
        mainHolder.mTittle.setText(mEquipst.get(i).getName());
        if(mEquipst.get(i).getStatus() == 1){
            Picasso.with(mContext)
                    .load(R.drawable.on)
                    .fit()
                    .centerCrop()
                    .noPlaceholder()
                    .into(mainHolder.imageView);
            mainHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            mainHolder.mStatus.setText("BẬT");
        }
        else {
            Picasso.with(mContext)
                    .load(R.drawable.off)
                    .fit()
                    .centerCrop()
                    .noPlaceholder()
                    .into(mainHolder.imageView);
            mainHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.colorAccentDark));

            mainHolder.mStatus.setText("TẮT");
        }
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataSend = "0";
                Log.d(TAG, "onClick: DV1/"+mEquipst.get(i).getChanel()+"/"+mEquipst.get(i).getPortOutput() + " - " + dataSend);
                if(mEquipst.get(i).getStatus() == 1) {
                    dataSend = "0";
                }
                else {
                    dataSend = "1";
                }
                mqttHelper.publish("DV1/"+mEquipst.get(i).getChanel()+"/"+mEquipst.get(i).getPortOutput(),dataSend);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEquipst.size();
    }
}
