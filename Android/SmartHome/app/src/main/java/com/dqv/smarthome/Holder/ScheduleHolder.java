package com.dqv.smarthome.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.dqv.smarthome.R;

public class ScheduleHolder extends RecyclerView.ViewHolder{
    public TextView mTime;
    public TextView mDay2;
    public TextView mDay3;
    public TextView mDay4;
    public TextView mDay5;
    public TextView mDay6;
    public TextView mDay7;
    public TextView mDayC;
    public TextView mEquip;
    public Switch mStatus;

    public ScheduleHolder(@NonNull View itemView) {
        super(itemView);
        mTime = (TextView) itemView.findViewById(R.id.twTimeSche);
        mDay2 = (TextView) itemView.findViewById(R.id.twDaySche2);
        mDay3 = (TextView) itemView.findViewById(R.id.twDaySche3);
        mDay4 = (TextView) itemView.findViewById(R.id.twDaySche4);
        mDay5 = (TextView) itemView.findViewById(R.id.twDaySche5);
        mDay6 = (TextView) itemView.findViewById(R.id.twDaySche6);
        mDay7 = (TextView) itemView.findViewById(R.id.twDaySche7);
        mDayC = (TextView) itemView.findViewById(R.id.twDayScheC);

        mEquip = (TextView) itemView.findViewById(R.id.twEquipSche);
        mStatus = (Switch) itemView.findViewById(R.id.swStatusSche);

    }
}
