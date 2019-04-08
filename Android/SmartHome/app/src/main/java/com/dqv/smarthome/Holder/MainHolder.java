package com.dqv.smarthome.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dqv.smarthome.R;

public class MainHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView mTittle;
    public TextView mStatus;

    public MainHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imgEquipMain);
        mTittle = (TextView) itemView.findViewById(R.id.twNameEquipMain);
        mStatus = (TextView) itemView.findViewById(R.id.twStatusEquipMain);

    }

}
