package com.dqv.smarthome.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dqv.smarthome.R;

public class RoomHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView mTittle;
    public TextView mDes;
    public ImageView imageViewEdit;
    public ImageView imageViewDelete;

    public RoomHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.bg_item_news);
        mTittle = (TextView) itemView.findViewById(R.id.item_tittle_news);
        mDes = (TextView) itemView.findViewById(R.id.item_des_news);
        imageViewEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
        imageViewDelete = (ImageView) itemView.findViewById(R.id.imgDelete);


    }
}
