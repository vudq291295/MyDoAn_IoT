package com.dqv.smarthome.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dqv.smarthome.R;

public class EnvrHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView mTittle;
    public TextView mDes;
    public EnvrHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.bg_item_evr);
        mTittle = (TextView) itemView.findViewById(R.id.item_tittle_envr);
        mDes = (TextView) itemView.findViewById(R.id.item_des_envr);

    }
}
