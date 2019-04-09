package com.dqv.smarthome.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.dqv.smarthome.Activity.MainActivity;
import com.dqv.smarthome.Activity.RoomActivity;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Dialog.DialogEquipment;
import com.dqv.smarthome.Dialog.ManagerDialog;
import com.dqv.smarthome.Holder.MainHolder;
import com.dqv.smarthome.Holder.RoomHolder;
import com.dqv.smarthome.Model.EquipmentModel;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Mqtt.MqttHelper;
import com.dqv.smarthome.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends RecyclerView.Adapter<MainHolder> {
    public static final String TAG = RoomAdapter.class.getSimpleName();

    Context mContext;
    View v,view;
    MqttHelper mqttHelper;
    private ArrayList<EquipmentModel> mEquipst = new ArrayList<EquipmentModel>();
    FragmentManager fm;
    public String currentToken ="";
    public String urlDeleteEquip = UrlModel.url_deleteEquipment;

    public MainAdapter(ArrayList<EquipmentModel> mRoomsLst, Context mContext, MqttHelper mqttHelper,FragmentManager fm) {
        this.mEquipst = mRoomsLst;
        this.mContext = mContext;
        this.mqttHelper = mqttHelper;
        this.fm = fm;
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();

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
        mainHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEquipment userInfoDialog = DialogEquipment.newInstance(mEquipst.get(i));
                userInfoDialog.show(fm, null);
            }
        });

        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+i);
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
        final Context context = mainHolder.itemView.getContext();
        mainHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle("Xóa phòng");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Bạn có muốn xóa "+mEquipst.get(i).getName() + "tại "+mEquipst.get(i).getNameRoom()+"!!!")
                        .setCancelable(false)
                        .setPositiveButton("ĐỒNG Ý",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                DeleteRoomApi(mEquipst.get(i));
                            }
                        })
                        .setNegativeButton("HỦY",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEquipst.size();
    }

    public void DeleteRoomApi(final EquipmentModel model){
        JSONObject postparams = new JSONObject();
        try
        {
            postparams.put("name", model.getName());
            postparams.put("portOutput", model.getPortOutput());
            postparams.put("roomId", model.getRoomId());
            postparams.put("id",model.getId());
        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, urlDeleteEquip,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                    }
                    Toast.makeText(mContext,
                            msg+"", Toast.LENGTH_LONG).show();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }

}
