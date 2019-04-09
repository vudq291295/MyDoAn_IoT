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
import com.dqv.smarthome.Activity.RoomActivity;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Dialog.ManagerDialog;
import com.dqv.smarthome.Holder.RoomHolder;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class RoomAdapter extends RecyclerView.Adapter<RoomHolder>{
    public static final String TAG = RoomAdapter.class.getSimpleName();
    public String urlDeleteRoom = UrlModel.url_deleteRoomn;
    public String currentToken ="";

    Context mContext;
    View v,view;
    private ArrayList<RoomModel> mRoomsLst = new ArrayList<RoomModel>();
    FragmentManager fm;
    public RoomAdapter(ArrayList<RoomModel> mRoomsLst, Context mContext,FragmentManager fm) {
        this.mRoomsLst = mRoomsLst;
        this.mContext = mContext;
        this.fm = fm;
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();

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
    public void onBindViewHolder(@NonNull RoomHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder() returned: " + mRoomsLst.get(i).getName());

        viewHolder.mTittle.setText(mRoomsLst.get(i).getName());
        viewHolder.mDes.setText("Số thiết bị "+mRoomsLst.get(i).getCountEquipment()+"");
        Picasso.with(mContext)
                .load("https://hips.hearstapps.com/edc.h-cdn.co/assets/cm/15/04/1600x1009/54bff9545f583_-_grandmanner11.jpg?crop=1xw:0.7925117004680188xh;center,top&resize=480:*")
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(viewHolder.imageView);

        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerDialog userInfoDialog = ManagerDialog.newInstance(mRoomsLst.get(i));
                userInfoDialog.show(fm, null);
            }
        });
        final Context context = viewHolder.itemView.getContext();
        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle("Xóa phòng");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Bạn có muốn xóa "+mRoomsLst.get(i).getName() + "!!!")
                        .setCancelable(false)
                        .setPositiveButton("ĐỒNG Ý",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                DeleteRoomApi(mRoomsLst.get(i));
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
        return mRoomsLst.size();
    }

    public void DeleteRoomApi(final RoomModel model){
        JSONObject postparams = new JSONObject();
        try
        {
            postparams.put("name", model.getName());
            postparams.put("chanel", model.getChanel());
            postparams.put("id",model.getId());
        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, urlDeleteRoom,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(mContext,RoomActivity.class);
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
