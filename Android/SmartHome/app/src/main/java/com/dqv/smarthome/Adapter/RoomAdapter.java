package com.dqv.smarthome.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dqv.smarthome.Activity.RoomActivity;
import com.dqv.smarthome.Holder.RoomHolder;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.MissingResourceException;

public class RoomAdapter extends RecyclerView.Adapter<RoomHolder>{
    public static final String TAG = RoomAdapter.class.getSimpleName();

    Context mContext;
    View v,view;
    private ArrayList<RoomModel> mRoomsLst = new ArrayList<RoomModel>();

    public RoomAdapter(ArrayList<RoomModel> mRoomsLst, Context mContext) {
        this.mRoomsLst = mRoomsLst;
        this.mContext = mContext;
        Log.d(TAG, "RoomAdapter: "+mRoomsLst.size());
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_room, viewGroup, false);
        RoomHolder vh = new RoomHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder() returned: " + mRoomsLst.get(i).getName());

        viewHolder.mTittle.setText(mRoomsLst.get(i).getName());
        viewHolder.mDes.setText("Số thiết bị "+mRoomsLst.get(i).getCountEquipment()+"");
        Picasso.with(mContext)
                .load("https://hips.hearstapps.com/edc.h-cdn.co/assets/cm/15/04/1600x1009/54bff9545f583_-_grandmanner11.jpg?crop=1xw:0.7925117004680188xh;center,top&resize=480:*")
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mRoomsLst.size();
    }
}
