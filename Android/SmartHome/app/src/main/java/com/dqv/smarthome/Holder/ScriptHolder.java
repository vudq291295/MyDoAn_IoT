package com.dqv.smarthome.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dqv.smarthome.R;

public class ScriptHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView mTittle;

    public ScriptHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.bg_item_script);
        mTittle = (TextView) itemView.findViewById(R.id.item_tittle_script);

    }
}
